import javax.vecmath.Vector3f;

public class VertexP3N {
	private Vector3f position;
	private Vector3f normal = new Vector3f(0, 0, 0);
	
	public VertexP3N(final Vector3f position) {
		this.setPosition(position);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
}
