import java.awt.MouseInfo;
import java.awt.Point;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class DialDisplay implements GLEventListener  {
	private Camera m_camera = new Camera();
	private Light m_light = new Light();
	private Material m_material = new Material();
	private FooFunctional pointOfMobiusStripFromXY = new FooFunctional() {
		@Override
		public Vector3f invoke(float u, float v) {
			float x = (float) ((1.f + (v / 2.f * Math.cos(u / 2.f))) * Math.cos(u));
			float y = (float) ((1.f + (v / 2.f * Math.cos(u / 2.f))) * Math.sin(u));
			float z = (float) (v / 2.f * Math.sin(u / 2.f));
			return new Vector3f(x, y, z);
		}
	};
	private Function3D m_funtion = new Function3D(pointOfMobiusStripFromXY);
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		final GLU glu = GLU.createGLU(gl);
		m_camera.update(glu);
		m_material.setMaterial(gl);
		m_light.setLight(gl);
		drawFunction3D(gl);
	}

	private void drawFunction3D(GL2 gl) {
		gl.glOrtho(-5, 5, -5, 5, -5, 5);
		m_funtion.draw(gl);
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
