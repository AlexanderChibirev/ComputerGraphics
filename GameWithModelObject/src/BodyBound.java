import java.awt.Rectangle;

public class BodyBound {
	 protected float x;
	 protected float y;
	 protected int width;
	 protected int height;
	 protected int typeSprite;
	 
	 public BodyBound(float x, float y, int width, int height, int typeSprite) {
	        this.x = x;
	        this.y = y;
	        this.width = width;
	        this.height = height;
	        this.typeSprite = typeSprite;
	 }
	 
	 public Rectangle getBounds() {
	        return new Rectangle((int)x, (int)y, width, height);
	}	 
	 
}
