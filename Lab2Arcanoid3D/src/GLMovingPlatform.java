import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class GLMovingPlatform  extends Body {
	private float[] mColorMovingPlatform = {1,0,0};
	private RectangularPrism mMovingPlatform = new  RectangularPrism(new Vector3f(), mColorMovingPlatform);
	private Vector3f mBlockSize = new Vector3f(1,1,1);
	public void render(GL2 gl, int textureID) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				Vector2 v = p.getVertices()[0];
				mBlockSize.x =  Math.abs((float) v.x);
				mBlockSize.y =  Math.abs((float) v.y);
				mMovingPlatform.setSize(mBlockSize);
				mMovingPlatform.setColor(mColorMovingPlatform);
				mMovingPlatform.draw(gl, textureID);
			}
		}
		gl.glPopMatrix();
	}	
	
}
