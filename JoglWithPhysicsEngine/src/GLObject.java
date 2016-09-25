import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLObject extends Body {
		protected float[] color = new float[4];
		public GLObject() {
			// randomly generate the color
			this.color[0] = (float)Math.random() * 0.5f + 0.5f;
			this.color[1] = (float)Math.random() * 0.5f + 0.5f;
			this.color[2] = (float)Math.random() * 0.5f + 0.5f;
			this.color[3] = 1.0f;
		}
		public void render(GL2 gl) {
			// save the original transform
			gl.glPushMatrix();
			// transform the coordinate system from world coordinates to local coordinates	
			gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
			// rotate about the z-axis
			gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
			
			// loop over all the body fixtures for this body
			for (BodyFixture fixture : this.fixtures) {
				// get the shape on the fixture
				Convex convex = fixture.getShape();
				// check the shape type
				if (convex instanceof Polygon) {
					// since Triangle, Rectangle, and Polygon are all of
					// type Polygon in addition to their main type
					Polygon p = (Polygon) convex;
					
					// set the color
					gl.glColor4fv(this.color, 0);
					// fill the shape
					gl.glBegin(GL2.GL_POLYGON);
					for (Vector2 v : p.getVertices()) {
						gl.glVertex3d(v.x, v.y, 0.0);
					}
					gl.glEnd();
					
					// set the color
					gl.glColor4f(this.color[0] * 0.8f, this.color[1] * 0.8f, this.color[2] * 0.8f, 1.0f);
					
					// draw the shape
					gl.glBegin(GL.GL_LINE_LOOP);
					for (Vector2 v : p.getVertices()) {
						gl.glVertex3d(v.x, v.y, 0.0);
					}
					gl.glEnd();
				}
				// circles and other curved shapes require a little more work, so to keep
				// this example short we only include polygon shapes; see the RenderUtilities
				// in the Sandbox application
			}
			
			// set the original transform
			gl.glPopMatrix();
		}
	}
	
