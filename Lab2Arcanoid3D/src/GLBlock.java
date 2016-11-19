import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class GLBlock extends Body {
	private float[] mColor = {0,1,1};
	private RectangularPrism mBlock = new  RectangularPrism(new Vector3f(), mColor);
	private Vector3f mBlockSize = new Vector3f(1,1,1);
	public void render(GL2 gl,  int textureID) {
		gl.glPushMatrix();
		float shiftZ = 1.2f;
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), shiftZ);	
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				Vector2 v = p.getVertices()[0];
				mBlockSize.x =  Math.abs((float) v.x);
				mBlockSize.y =  Math.abs((float) v.y);
				mBlock.setSize(mBlockSize);
				mBlock.setColor(mColor);
				mBlock.draw(gl, textureID);
			}
		}
		gl.glPopMatrix();
	}	
}
