import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class DialDisplay implements GLEventListener  {
	private float m_rquad = 0.0f;
	@Override
	public void display(GLAutoDrawable gLDrawable) {		
		final GL2 gl = gLDrawable.getGL().getGL2();
		// включаем механизмы трёхмерного мира.
		includeMechanisms3DWorld(gl);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    gl.glRotatef(m_rquad, 1.0f, 1.0f, 1.0f ); // Rotate The Cube On X, Y & Z
	    m_rquad -=0.55f;
		drawRhombicuboctahedron(gl);
	}
	private void drawRhombicuboctahedron(GL2 gl){
		RhombicuboctahedronView pentakis = new RhombicuboctahedronView();
		gl.glOrtho(-4, 4, -4, 4, -4, 4);
		enableBlending(gl);
	    pentakis.drawRhombicuboctahedron(gl);
	    disableBlending(gl);
	}

	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glFrontFace(GL2.GL_CCW);
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		
	}
	
	private void disableBlending(GL2 gl) {
		gl.glDepthMask(true);
		gl.glDisable(GL2.GL_BLEND);
	}
	
	private void enableBlending(GL2 gl) {
		gl.glDepthMask(false);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
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
