import java.awt.Rectangle;

public class BodyBound {
	 protected float x;
	 protected float y;
	 protected int width;
	 protected int height;
	 protected int textureId;
	 
	 public BodyBound(float x, float y, int width, int height, int textureId) {
	        this.x = x;
	        this.y = y;
	        this.width = width;
	        this.height = height;
	        this.textureId = textureId;
	 }
	 
	 public Rectangle getBounds() {		 	
	        return new Rectangle((int)x, (int)y, width, height);
	 }
	 
}
