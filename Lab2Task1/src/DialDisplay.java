
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class DialDisplay implements GLEventListener  {
	private Camera m_camera = new Camera();
	private Light m_light = new Light();
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		final GLU glu = GLU.createGLU(gl);
		m_camera.update(glu);
		drawRhombicuboctahedron(gl);
		m_light.setLight(gl);
	}

	private void drawRhombicuboctahedron(GL2 gl){
		RhombicuboctahedronView pentakis = new RhombicuboctahedronView();
		gl.glOrtho(-4, 4, -4, 4, -4, 4);
		pentakis.drawRhombicuboctahedron(gl);
	}

	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glFrontFace(GL2.GL_CCW);
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	}
	
	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}

}
