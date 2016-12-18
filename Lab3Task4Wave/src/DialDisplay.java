import javax.vecmath.Vector2f;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class DialDisplay implements GLEventListener  {
	
	private final Vector2f SURFACE_TOPLEFT = new Vector2f(-1, -1);
	private final Vector2f SURFACE_SIZE = new Vector2f(2, 2);
	protected long mLast;
	private Surface mSurface;
	
	@Override	
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (1, 1, 1, 1);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		System.out.println(CustomListener.getX());
		update();
		/*mSurface.setWaveCenter(new Vector2f(0 / 800,
			0 / 800));*/
		
		
		try {
			mSurface.draw(drawable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void update() {
        long time = System.nanoTime();
        long diff = time - this.mLast;
        this.mLast = time;
    	double elapsedTime = diff / 1.0e9;
    	mSurface.update(elapsedTime);
	}

	private void setupOpenGLState(GL2 gl) {
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
		mSurface = new Surface(SURFACE_TOPLEFT, SURFACE_SIZE, gl);			
		setupOpenGLState(gl);
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}
}
