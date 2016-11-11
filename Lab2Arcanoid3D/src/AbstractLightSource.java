import com.jogamp.opengl.GL2;

/*
 * const method does not exist in java. 
 * final and const have different semantics,
 * except when applied to a variable of a primitive type.
 * The java solution typically involves creating immutable classes -
 * where objects are initialized in construction and provide
 * no accessors allowing change.
*/
public abstract class AbstractLightSource implements ILightSource {
	AbstractLightSource(int index) {
		this.m_index = index;
	}
	
	private float[] m_ambient;
	private float[] m_diffuse;
	private float[] m_specular;
	private int m_index;
	
	protected void setupImpl(GL2 gl) {
		gl.glEnable(m_index);
		gl.glLightfv(m_index, GL2.GL_AMBIENT, m_ambient, 0);
		gl.glLightfv(m_index, GL2.GL_DIFFUSE, m_diffuse, 0);
		gl.glLightfv(m_index, GL2.GL_SPECULAR, m_specular, 0);
	}
	protected int getIndex() {
		return m_index;	
	}
	 
	public final void setAmbient(final float[] color) {
		m_ambient = color;
	}
	
	public final void setDiffuse(final float[] color) {
		m_diffuse = color;
	}
	
	public final void setSpecular(final float[] color) {
		m_specular = color;
	}
	 
	public final float[] getAmbient() {
		return m_ambient;
	}
	
	public final float[] getDiffuse() {
		return m_diffuse;
	}
	
	public final float[] getSpecular() {
		return m_specular;
	}
}
