import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;
import com.jogamp.opengl.GL2;

public class Light {
	private float[] WHITE_LIGHT = {1, 1, 1, 1};
	private float[] PINK_LIGHT = {0.3f, 0.3f, 0.3f, 0.3f};
	private final Vector3f SUNLIGHT_DIRECTION = new Vector3f( -1.f, 0.2f, 0.7f);
	private DirectedLightSource m_sunlight = new DirectedLightSource(GL2.GL_LIGHT0);
	
	private FloatBuffer colorToFloatBuffer(float[] buffer) {
		FloatBuffer floatBuffer;
		ByteBuffer buf= ByteBuffer.allocateDirect(4 * 4);
		buf.order(ByteOrder.nativeOrder());
		floatBuffer = buf.asFloatBuffer();
		floatBuffer.put(buffer);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	public void setLight(GL2 gl) {
		m_sunlight.setDirection(SUNLIGHT_DIRECTION);
		m_sunlight.setDiffuse(colorToFloatBuffer(WHITE_LIGHT));
		m_sunlight.setAmbient(colorToFloatBuffer(PINK_LIGHT));
		m_sunlight.setSpecular(colorToFloatBuffer(WHITE_LIGHT));
		m_sunlight.setup(gl);
	}
}
