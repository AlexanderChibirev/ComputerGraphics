import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class DirectedLightSource extends AbstractLightSource {

	DirectedLightSource(int index) {
		super(index);
	}
	private float[] m_direction;
	
	public Vector3f getDirection() {
		return new Vector3f(m_direction[0], m_direction[1], m_direction[2]);
	}
	public void setDirection(final float[] value) {
		m_direction = value;
	}

	@Override
	public void setup(GL2 gl) {
		setupImpl(gl);
		gl.glLightfv(getIndex(), GL2.GL_POSITION, m_direction, 0);
	}
}
