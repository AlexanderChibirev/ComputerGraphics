import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFrame;

import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.event.WindowListener;
import com.jogamp.newt.event.WindowUpdateEvent;
import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class Shader extends JFrame implements GLEventListener, WindowListener {

   private static final long serialVersionUID = 1L;
   boolean first = true;
   int list;
   int no;
   double[] x, y, z, a;

   public Shader() {
      super("Test Shader");

      setLayout(new BorderLayout());
      //addWindowListener(this);

      setSize(850, 850);
      setVisible(true);

      setupJOGL();
      String s = "0.00";
      noFormat = new DecimalFormat(s);
   }

   private void setupJOGL() {
      GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
      caps.setDoubleBuffered(true);
      caps.setHardwareAccelerated(true);
      GLCanvas canvas = new GLCanvas(caps);
      canvas.addGLEventListener(this);

      add(canvas, BorderLayout.CENTER);

      Animator anim = new Animator(canvas);
      anim.start();
   }

   /**
    * @param args
    */
   public static void main(String[] args) {
      new Shader().setVisible(true);
   }

   @Override
   public void display(GLAutoDrawable drawable) {
	   final GL2 gl = drawable.getGL().getGL2();
	         gl.glLoadIdentity();
      gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

      gl.glColor3f(1, 0, 0);
      gl.glBegin(GL2.GL_LINES);
      gl.glVertex2d(0, -d);
      gl.glVertex2d(0, d);
      gl.glEnd();
      updateFPS();
   }

   public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
      // TODO Auto-generated method stub

   }
   @Override
   public void init(GLAutoDrawable drawable) {
	  drawable.setGL(new DebugGL2((GL2) drawable.getGL()));
	  final GL2 gl = drawable.getGL().getGL2();
	      
      gl.glClearColor(0, 0, 0, 0);
      gl.glMatrixMode(GL2.GL_PROJECTION);
      gl.glLoadIdentity();
      gl.glOrtho(0, 1, 0, 1, -1, 1);

      gl.glMatrixMode(GL2.GL_MODELVIEW);
      gl.glLoadIdentity();

      setupShaders(gl);
   }

   int p;
   int v;
   int f;
   int g;

   private void setupShaders(GL2 gl) {

      v = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
      f = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
      g = gl.glCreateShader(GL3.GL_GEOMETRY_SHADER);

      String[] vs = new String[1];
      vs[0] = readShaderFile("vertex.vs");
      String[] gs = new String[1];
      gs[0] = readShaderFile("geometry.gs");
      String[] fs = new String[1];
      fs[0] = readShaderFile("fragment.fs");

      int[] vb = new int[] { vs[0].length() };
      gl.glShaderSource(v, 1, vs, vb, 0);

      int[] fb = new int[] { fs[0].length() };
      gl.glShaderSource(f, 1, fs, fb, 0);

      int[] gb = new int[] { gs[0].length() };
      gl.glShaderSource(g, 1, gs, gb, 0);

      gl.glCompileShader(v);
      checkCompileError(gl, v);

      gl.glCompileShader(f);
      checkCompileError(gl, f);

      gl.glCompileShader(g);
      checkCompileError(gl, g);

      p = gl.glCreateProgram();

      gl.glAttachShader(p, f);
      gl.glAttachShader(p, v);
      gl.glAttachShader(p, g);

      gl.glProgramParameteri(p, GL2.GL_GEOMETRY_INPUT_TYPE_EXT,
                  GL2.GL_LINES);
      gl.glProgramParameteri(p, GL2.GL_GEOMETRY_OUTPUT_TYPE_EXT,
            GL2.GL_LINE_STRIP);

      int[] temp = new int[2];
      gl.glGetIntegerv(GL2.GL_GEOMETRY_PROGRAM_PARAMETER_BUFFER_NV, temp, 0);
      gl.glProgramParameteri(p, GL2.GL_GEOMETRY_VERTICES_OUT_EXT, temp[0]);

      gl.glLinkProgram(p);
      checkLinkAndValidationErrors(gl, p);
      gl.glUseProgram(p);
   }

   private void checkCompileError(GL2 gl, int id) {
      IntBuffer status = BufferUtil.newIntBuffer(1);
      gl.glGetShaderiv(id, GL2.GL_COMPILE_STATUS, status);

      if (status.get() == GL2.GL_FALSE) {
         getInfoLog(gl, id);
      } else {
         System.out.println("Successfully compiled shader " + id);
      }
   }

   private void getInfoLog(GL2 gl, int id) {
      IntBuffer infoLogLength = BufferUtil.newIntBuffer(1);
      gl.glGetShaderiv(id, GL2.GL_INFO_LOG_LENGTH, infoLogLength);

      ByteBuffer infoLog = BufferUtil.newByteBuffer(infoLogLength.get(0));
      gl.glGetShaderInfoLog(id, infoLogLength.get(0), null, infoLog);

      String infoLogString = Charset.forName("US-ASCII").decode(infoLog)
            .toString();
      throw new Error("Shader compile error\n" + infoLogString);
   }

   private void checkLinkAndValidationErrors(GL2 gl, int id) {
      IntBuffer status = BufferUtil.newIntBuffer(1);
      gl.glGetProgramiv(id, GL2.GL_LINK_STATUS, status);

      if (status.get() == GL2.GL_FALSE) {
         getInfoLog(gl, id);
      } else {
         status.rewind();
         gl.glValidateProgram(id);
         gl.glGetProgramiv(id, GL2.GL_VALIDATE_STATUS, status);
         if (status.get() == GL2.GL_FALSE) {
            getInfoLog(gl, id);
         } else {
            System.out.println("Successfully linked program " + id);
         }
      }
   }

   private String readShaderFile(String file) {
      String s = "";
      try {
         BufferedReader r = new BufferedReader(new FileReader(file));
         String t = r.readLine();
         while (t != null) {
            s += t;
            s += "\n";
            t = r.readLine();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return s;
   }

   int w;
   int h;
   int d;
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width,
         int height) {
	   final GL2 gl = drawable.getGL().getGL2();
	      
      gl.glViewport(0, 0, width, height);
      gl.glMatrixMode(GL2.GL_PROJECTION);
      gl.glLoadIdentity();

      w = width / 2;
      h = height / 2;
      gl.glOrtho(-1 * w, w, -1 * h, h, -600, 600);
      gl.glMatrixMode(GL2.GL_MODELVIEW);
      gl.glLoadIdentity();
      d = Math.min(w, h);
   }
   
   private double stTime;
   private double enTime;
   private double frameCt;
   private NumberFormat noFormat;

   private void updateFPS() {
      enTime = System.currentTimeMillis();
      double diff = enTime - stTime;
      frameCt++;
      if (diff > 1000) {
         double fps = frameCt * 1000 / diff;
         stTime = enTime;
         String s = noFormat.format(fps);
         this.setTitle("Name [FPS: " + s + "]");
         frameCt = 0;
      }
   }

	@Override
	public void windowDestroyNotify(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDestroyed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowMoved(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowRepaint(WindowUpdateEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowResized(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

}