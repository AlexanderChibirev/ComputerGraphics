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
		this.color[0] = (float)Math.random() * 0.5f + 0.5f;
		this.color[1] = (float)Math.random() * 0.5f + 0.5f;
		this.color[2] = (float)Math.random() * 0.5f + 0.5f;
		this.color[3] = 1.0f;
	}
	public void render(GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				gl.glColor4fv(this.color, 0);
				gl.glBegin(GL2.GL_POLYGON);
				for (Vector2 v : p.getVertices()) {
					gl.glVertex3d(v.x, v.y, 0.0);
				}
				gl.glEnd();
				gl.glColor4f(this.color[0] * 0.8f, this.color[1] * 0.8f, this.color[2] * 0.8f, 1.0f);
				gl.glBegin(GL.GL_LINE_LOOP);
				for (Vector2 v : p.getVertices()) {
					gl.glVertex3d(v.x, v.y, 0.0);
				}
				gl.glEnd();
			}
		}
		gl.glPopMatrix();
	}	
}
