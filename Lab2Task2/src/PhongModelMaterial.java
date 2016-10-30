import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;

public class PhongModelMaterial {
	private FloatBuffer m_ambient;
	private FloatBuffer m_diffuse;
	private FloatBuffer m_specular;
	private float m_shininess = 10.f;
	
	public void setup(GL2 gl) {
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, m_ambient);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, m_diffuse);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, m_specular);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, m_shininess);
	}
	
	public void setAmbient(final FloatBuffer ambient) {
		m_ambient = ambient;
	}
	
	public void setDiffuse(final FloatBuffer diffuse) {
		m_diffuse = diffuse;
	}
	
	public void setSpecular(final FloatBuffer specular) {
		m_specular = specular;
	}
	
	public void setShininess(float shininess) {
		m_shininess = shininess;
	}
	
	public FloatBuffer getAmbient() {
		return m_ambient;
	}
	
	public FloatBuffer getDiffuse() {
		return m_diffuse;
	}
	
	public FloatBuffer getSpecular() {
		return m_specular;
	}
	
	public float getShininess() {
		return m_shininess;
	}
}
