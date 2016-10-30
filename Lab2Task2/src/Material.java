import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class Material {
	private final float[] YELLOW_RGBA = {1, 1, 0, 1};
	private final float[] FADED_WHITE_RGBA = { 0.3f, 0.3f, 0.3f, 1.f};
	private final float MATERIAL_SHININESS = 30.f;
	private PhongModelMaterial m_material = new PhongModelMaterial();
	
	private FloatBuffer colorToFloatBuffer(float[] buffer) {
		FloatBuffer floatBuffer;
		ByteBuffer buf= ByteBuffer.allocateDirect(4 * 4);
		buf.order(ByteOrder.nativeOrder());
		floatBuffer = buf.asFloatBuffer();
		floatBuffer.put(buffer);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	public void setMaterial(GL2 gl) {
		
		m_material.setAmbient(colorToFloatBuffer(YELLOW_RGBA));
		m_material.setDiffuse(colorToFloatBuffer(YELLOW_RGBA));
		m_material.setSpecular(colorToFloatBuffer(FADED_WHITE_RGBA));
		m_material.setShininess(MATERIAL_SHININESS);
		m_material.setup(gl);
	}
}
