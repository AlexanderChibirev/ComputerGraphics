import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class GLMovingPlatform  extends Body {
	private float mElongationCoefficient = 7.0f;
	public void render(GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				float blockSize = (float) Math.abs(p.getVertices()[0].y);
				gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
					gl.glColor3f(1f ,0f,0f); //red color
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f * blockSize); // Top Right Of The Quad (Top)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f * blockSize); // Top Left Of The Quad (Top)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, 1.0f * blockSize); // Bottom Left Of The Quad (Top)
					gl.glVertex3f(1.0f * mElongationCoefficient *blockSize, 1.0f * blockSize, 1.0f * blockSize); // Bottom Right Of The Quad (Top)
					
					gl.glColor3f( 0f,1f,0f ); //green color
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Top Right Of The Quad
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Top Left Of The Quad
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f( 0f,0f,1f ); //blue color
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, 1.0f * blockSize); // Top Right Of The Quad (Front)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, 1.0f * blockSize); // Top Left Of The Quad (Front)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f(1f,1f,0f ); //yellow (red + green)
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Left Of The Quad
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Right Of The Quad
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f * blockSize); // Top Right Of The Quad (Back)
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f  * blockSize); // Top Left Of The Quad (Back)
					
					gl.glColor3f(1f,0f,1f ); //purple (red + green)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, 1.0f * blockSize); // Top Right Of The Quad (Left)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f * blockSize); // Top Left Of The Quad (Left)
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Left Of The Quad
					gl.glVertex3f(-1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f(0f,1f, 1f ); //sky blue (blue +green)
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, -1.0f * blockSize); // Top Right Of The Quad (Right)
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, 1.0f * blockSize, 1.0f * blockSize); // Top Left Of The Quad
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, 1.0f * blockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mElongationCoefficient * blockSize, -1.0f * blockSize, -1.0f * blockSize); // Bottom Right Of The Quad
				gl.glEnd(); // Done Drawing The Quad
				gl.glFlush();
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
