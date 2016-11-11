import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class PositionLightSource extends AbstractLightSource {

	PositionLightSource(int index) {
		super(index);
	}

	private float[] m_direction;
	
	public Vector3f getDirection() {
		return new Vector3f(m_direction[0], m_direction[1], m_direction[2]);
	}
	
	@Override
	public void setup(GL2 gl) {
		setupImpl(gl);
		gl.glLightfv(getIndex(), GL2.GL_POSITION, m_direction, 0);
	}
}
