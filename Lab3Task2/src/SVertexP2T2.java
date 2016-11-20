import javax.vecmath.Vector2f;

public class SVertexP2T2 {
	private Vector2f mPosition;
	private Vector2f mTexCoord;
	
	public SVertexP2T2(Vector2f position, Vector2f texCoord) {
		// TODO Auto-generated constructor stub
		setTexCoord(texCoord);
		setPosition(position);
	}
	public Vector2f getTexCoord() {
		return mTexCoord;
	}
	public void setTexCoord(Vector2f texCoord) {
		this.mTexCoord = texCoord;
	}
	
	public Vector2f getPosition() {
		return mPosition;
	}
	public void setPosition(Vector2f position) {
		this.mPosition = position;
	}
	
}
