import javax.vecmath.Vector3f;

public class VertexP3N {
	private Vector3f m_position;
	private Vector3f m_normal = new Vector3f(0, 0, 0);
	
	public VertexP3N(final Vector3f position) {
		this.setPosition(position);
	}

	public Vector3f getPosition() {
		return m_position;
	}

	public void setPosition(Vector3f position) {
		this.m_position = position;
	}

	public Vector3f getNormal() {
		return m_normal;
	}

	public void setNormal(Vector3f normal) {
		this.m_normal = normal;
	}
}
