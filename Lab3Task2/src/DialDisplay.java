import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private ShaderManager mShaderManager = new ShaderManager();
	
	private final Vector2f QUAD_TOPLEFT = new Vector2f(-200, -200);
	private final Vector2f QUAD_SIZE = new Vector2f( 400, 400);
	Quadrangle m_quadObj = new Quadrangle(QUAD_TOPLEFT, QUAD_SIZE);
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (1, 1, 1, 1);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glColor3f( 0.0f, 1.0f, 0.0f );
		setupOpenGLState(gl);
		//mShaderManager.start(gl);
		m_quadObj.draw(gl);
		//mShaderManager.stop(gl);
	}

	private void setupOpenGLState(GL2 gl)
	{
	    // включаем механизмы трёхмерного мира.
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
        // Enable VSync
        gl.setSwapInterval(1);
		gl.glShadeModel(GL2.GL_FLAT);
		try {
			mShaderManager.attachShaders(gl);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
