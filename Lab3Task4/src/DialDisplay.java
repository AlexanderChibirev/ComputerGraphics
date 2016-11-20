import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private ShaderManager mShaderManager = new ShaderManager();
	private Camera mCamera = new Camera();
	private  GLU mGlu = new GLU();
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		mShaderManager.start(gl);
		mShaderManager.updateUniformVars(gl);
		mCamera.update(mShaderManager);
		gl.glLoadIdentity();
		
        gl.glBegin(GL2.GL_QUADS);
		{
			gl.glTexCoord2f(0.0f, 0.0f);
			gl.glVertex3f(0.0f, 1.0f, 1.0f);
			gl.glTexCoord2f(1.0f, 0.0f);
			gl.glVertex3f(1.0f, 1.0f, 1.0f);
			gl.glTexCoord2f(1.0f, 1.0f);
			gl.glVertex3f(1.0f, 0.0f, 1.0f);
			gl.glTexCoord2f(0.0f, 1.0f);
			gl.glVertex3f(0.0f, 0.0f, 1.0f);
		}
        gl.glEnd();

        gl.glFlush();
		mShaderManager.stop(gl);
	}

	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
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
        if (height <= 0) { 

            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        mGlu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

}
