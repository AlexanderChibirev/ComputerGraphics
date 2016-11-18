import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBall extends Body {
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX() , this.transform.getTranslationY(), 0.0);
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		GLUquadric quad = glu.gluNewQuadric();
		glu.gluSphere(quad, 0.3, 30, 30);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();
	}
}
	
