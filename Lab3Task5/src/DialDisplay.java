import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private TwistValueController mTwistController;
	private ShaderProgram mShaderProgram;
	
	public DialDisplay(TwistValueController inputHandler) {
		mTwistController = inputHandler;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0, 0, 1);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();        
		
		
		mShaderProgram.use(gl);
		
		float points[] = {
			    -0.45f,  0.45f,
			     0.45f,  0.45f,
			     0.45f, -0.45f,
			    -0.45f, -0.45f,
		};
		
		FloatBuffer mIndicies;
		mIndicies = BufferUtil.newFloatBuffer(8);
		mIndicies.put(points);
		mIndicies.rewind();
		
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, 4);
		gl.glBufferData(GL2.GL_ARRAY_BUFFER, mIndicies.limit(), mIndicies, GL2.GL_STATIC_DRAW);
		
		IntBuffer vao;
		vao = BufferUtil.newIntBuffer(1);		
		gl.glGenVertexArrays(1, vao);
		gl.glBindVertexArray(1);
		gl.glDrawArrays(GL2.GL_POINTS, 0, 4);
	}
	
	private void initShaderProgram(GL2 gl) {
        String[] vertexShader = loadShaderFileFromSrc("vertex.vs");
        String[] fragmentShader = loadShaderFileFromSrc("fragment.fs");
        String[] geometryShader = loadShaderFileFromSrc("geometry.gs");

        
        
        mShaderProgram = new ShaderProgram(gl);
        mShaderProgram.compileShader(gl, vertexShader, ShaderType.Vertex);
        mShaderProgram.compileShader(gl, fragmentShader, ShaderType.Fragment);
        mShaderProgram.compileShader(gl, geometryShader, ShaderType.Geometry);
        
        mShaderProgram.link(gl);
    }
	
	private String[] loadShaderFileFromSrc( String fileName) {
		StringBuilder sb = new StringBuilder();
		try{
			InputStream is = getClass().getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine())!=null){
				sb.append(line);
				sb.append('\n');
			}
			is.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return new String[]{sb.toString()};
	}


	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1);
        initShaderProgram(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) { 
            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

}
