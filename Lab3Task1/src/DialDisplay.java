import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private ShaderManager mShaderManager = new ShaderManager();
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (1, 1, 1, 1);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glBegin(GL2.GL_LINE_LOOP);		
		gl.glColor3f( 0.0f, 1.0f, 0.0f );
		float size = 0.1f;
		for (float a = 0; a < 2 * Math.PI; a += (Math.PI/1000) ) {
			 double r = size * 
					 (1 + Math.sin(a)) *
					 (1 + 0.9 *  Math.cos(8 * a)) * 
					 (1 + 0.1 * Math.cos(24 * a));
			 gl.glVertex2d(r * Math.cos(a), r * Math.sin(a));
		}
		gl.glEnd();
		gl.glFlush();
		
		//mShaderManager.start(gl);
		
		//mShaderManager.stop(gl);
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
