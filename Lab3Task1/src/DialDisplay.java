import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private ShaderManager mProgramTwist = new ShaderManager();
	private Line3D mLineObj = new Line3D(0, 2.f * (float)Math.PI,  (float)Math.PI / 1000.f);
	private TwistValueController mTwistController;
	public DialDisplay(TwistValueController inputHandler) {
		mTwistController = inputHandler;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0, 0, 0);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		mProgramTwist.start(gl);
			int mRZ = 2;
			float x = -0.15f;
			gl.glTranslated(x, 0, 0);
			gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
			mProgramTwist.updateUniformVars(gl, mTwistController.getCurrentValue());
	        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
	        mLineObj.draw(drawable);
	        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        mProgramTwist.stop(gl);

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
			mProgramTwist.attachShaders(gl);
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
