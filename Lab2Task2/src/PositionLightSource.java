import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class PositionLightSource extends AbstractLightSource {

	PositionLightSource(int index) {
		super(index);
	}

private FloatBuffer m_direction;
	
	public Vector3f getDirection() {
		return new Vector3f(m_direction.get(0), m_direction.get(1), m_direction.get(2));
	}
	public void setDirection(final Vector3f value) {
		ByteBuffer buf= ByteBuffer.allocateDirect(4 * 4);
		buf.order(ByteOrder.nativeOrder());
		m_direction = buf.asFloatBuffer();
		float[] buffer = {value.x, value.y, value.z, 0};
		m_direction.put(buffer);
		m_direction.position(0);
	}

	@Override
	public void setup(GL2 gl) {
		setupImpl(gl);
		gl.glLightfv(getIndex(), GL2.GL_POSITION, m_direction);
	}
}
