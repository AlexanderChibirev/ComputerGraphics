import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class GLMovingPlatform  extends Body {
	private void render(GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				gl.glColor3f(1, 0, 0);
				gl.glBegin(GL2.GL_POLYGON);
				for (Vector2 v : p.getVertices()) {
					gl.glVertex3d(v.x, v.y, 0.0);
				}
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
	}
	public void updateMovingPlatform(GL2 gl) {
		for (double i = 0; i < RangesConst.RANGE_END_FOR_MOVING_PLATFORM.getValue(); ++i) {
			GLMovingPlatform glObjects = (GLMovingPlatform) DialDisplay.sWorld.getBody((int) i);
			glObjects.render(gl);
		}
	}
}
