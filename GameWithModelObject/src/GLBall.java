import org.dyn4j.dynamics.Body;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBall extends Body {
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX() , this.transform.getTranslationY(), 0.0);
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		gl.glColor3f(1f,1f,0f);//yellow
		GLUquadric quad = glu.gluNewQuadric();
		double radius = 0.3; 
		int slices = 30;
		int stacks = 30;
		glu.gluSphere(quad, radius, slices, stacks);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();
	}
}
	
