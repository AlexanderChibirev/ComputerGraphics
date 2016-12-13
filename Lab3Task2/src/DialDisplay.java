import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.vecmath.Vector2f;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	
	
	private final Vector2f QUAD_TOPLEFT = new Vector2f(-300, -200);
	private final Vector2f QUAD_SIZE = new Vector2f( 600, 400);
	ChinaFlag mQuadObj = new ChinaFlag(QUAD_TOPLEFT, QUAD_SIZE);
	private ShaderProgram mShaderProgram;
	@Override
	
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (1, 1, 1, 1);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		int mRZ = 512;
		gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
		
		mShaderProgram.use(gl);
		mQuadObj.draw(drawable);
	
	}

	private void setupOpenGLState(GL2 gl)
	{
	    //включаем механизмы трёхмерного мира.
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glFrontFace(GL2.GL_CCW);
		gl.glCullFace(GL2.GL_BACK);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		setupOpenGLState(gl);
        initShaderProgram(gl);
	}
	
	private void initShaderProgram(GL2 gl) {
        String[] vertexShader = loadShaderFileFromSrc("USSR.vs");
        String[] fragmentShader = loadShaderFileFromSrc("USSR.fs");

        mShaderProgram = new ShaderProgram(gl);
        mShaderProgram.compileShader(gl, vertexShader, ShaderType.Vertex);
        mShaderProgram.compileShader(gl, fragmentShader, ShaderType.Fragment);
      
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
