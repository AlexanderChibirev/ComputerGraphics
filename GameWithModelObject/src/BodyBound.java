import java.awt.Rectangle;

public class BodyBound {
	 protected float x;
	 protected float y;
	 protected int width;
	 protected int height;
	 
	 public BodyBound(float x, float y, int width, int height) {
	        this.x = x;
	        this.y = y;
	        this.width = width;
	        this.height = height;
	 }
	 
	 public Rectangle getBounds() {
	        return new Rectangle((int)x, (int)y, width, height);
	}	 
	 
}
