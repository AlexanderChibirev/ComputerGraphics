import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import java.io.*;
import java.nio.FloatBuffer;

import javax.vecmath.Vector2f;


@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private static final Vector2f QUAD_TOPLEFT  = new Vector2f(-300, -150);
    private static final Vector2f QUAD_SIZE   = new Vector2f(600, 300);

    private ShaderProgram shaderProgram;
    private GLU glu;

    private Quad function;

    Canvas() {
        this.addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
        checkOpenGLVersion(gl);

        // ----- Your OpenGL initialization code here -----
		function = new Quad(QUAD_TOPLEFT, QUAD_SIZE);
        initShaderProgram(gl);
        initGLContext(gl);
    }
    
   
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {


    	   GL2 gl = drawable.getGL().getGL2();

           if (height == 0){
               height = 1;
           }

           final float fieldOfView = 60.f;
           final float aspect = (float)width / height;
           final float zNear = 0.1f;
           final float zFar = 100.f;

           gl.glViewport(0, 0, width, height);

           gl.glMatrixMode(GL2.GL_PROJECTION);
           gl.glLoadIdentity();
           glu.gluPerspective(fieldOfView, aspect, zNear, zFar);

           gl.glMatrixMode(GL2.GL_MODELVIEW);
           gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		// ----- OpenGL rendering code -----
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
    	int mRZ = 40;
		gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
        // ----- Drawing shape -----
        shaderProgram.use(gl);
        function.draw(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

        shaderProgram.dispose(drawable.getGL().getGL2());
    }

    private void initGLContext(GL2 gl){

		gl.glClearColor(0f, 0f, 0f, 0f);
		gl.glClearDepth(1f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

    }

    private void initShaderProgram(GL2 gl){


        String vSource = loadStringFileFromCurrentPackage("source/glslFunction.vert");
        String fSource = loadStringFileFromCurrentPackage("source/glslFunction.frag");


        shaderProgram = new ShaderProgram(gl);
        shaderProgram.compileShader(gl, vSource, ShaderType.Vertex);
        shaderProgram.compileShader(gl, fSource, ShaderType.Fragment);

		//IntBuffer resultVertex = BufferUtil.newIntBuffer(1);
		//resultVertex.put(1);
		//gl.glGetObjectParameterivARB(shaderProgram.getProgramId(), GL2.GL_OBJECT_COMPILE_STATUS_ARB, resultVertex);

        shaderProgram.link(gl);
    }

    private void checkOpenGLVersion(GL2 gl)
    {
        if (!gl.isExtensionAvailable("GL_VERSION_3_2"))
        {
            throw new RuntimeException("Sorry, but OpenGL 3.2 is not available");
        }
    }

	private String loadStringFileFromCurrentPackage( String fileName){
		InputStream stream = this.getClass().getResourceAsStream(fileName);

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		// allocate a string builder to add line per line
		StringBuilder strBuilder = new StringBuilder();

		try {
			String line = reader.readLine();
			// get text from file, line per line

			while(line != null){
				strBuilder.append(line + "\n");
				line = reader.readLine();
			}
			// close resources
			reader.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return strBuilder.toString();
	}
}
