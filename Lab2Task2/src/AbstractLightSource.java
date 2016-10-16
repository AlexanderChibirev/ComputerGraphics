import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
public abstract class AbstractLightSource implements ILightSource {
	AbstractLightSource(int index) {
		this.m_index = index;
	}
	
	private FloatBuffer m_ambient;
	private FloatBuffer m_diffuse;
	private FloatBuffer m_specular;
	private int m_index;
	
	protected void setupImpl(GL2 gl) {
		gl.glEnable(m_index);
		gl.glLightfv(m_index, GL2.GL_AMBIENT, m_ambient);
		gl.glLightfv(m_index, GL2.GL_DIFFUSE, m_diffuse);
		gl.glLightfv(m_index, GL2.GL_SPECULAR, m_specular);
	}
	protected int getIndex() {
		return m_index;	
	}
	 
	public final void setAmbient(final FloatBuffer color) {
		m_ambient = color;
	}
	
	public final void setDiffuse(final FloatBuffer color) {
		m_diffuse = color;
	}
	
	public final void setSpecular(final FloatBuffer color) {
		m_specular = color;
	}
	 
	public final FloatBuffer getAmbient() {
		return m_ambient;
	}
	
	public final FloatBuffer getDiffuse() {
		return m_diffuse;
	}
	
	public final FloatBuffer getSpecular() {
		return m_specular;
	}
}
