import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class GLBlock extends Body {
	private final float mBlockSize = 0.00001f;
	protected float[] color = new float[4];
	public GLBlock() {
		this.color[0] = (float)Math.random() * 0.5f + 0.5f;
		this.color[1] = (float)Math.random() * 0.5f + 0.5f;
		this.color[2] = (float)Math.random() * 0.5f + 0.5f;
		this.color[3] = 1.0f;
	}
	final Vector3f DARK_GREEN = new Vector3f(0.05f, 0.45f, 0.1f);
	final Vector3f LIGHT_GREEN = new Vector3f(0.1f, 0.8f, 0.15f);
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
			
		//gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);		
		for (BodyFixture fixture : this.fixtures) {
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				float mBlockSize = (float) Math.abs(p.getVertices()[0].x);
				gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
					gl.glColor3f(1f ,0f,0f); //red color
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f * mBlockSize); // Top Right Of The Quad (Top)
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f * mBlockSize); // Top Left Of The Quad (Top)
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Left Of The Quad (Top)
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Right Of The Quad (Top)
					
					gl.glColor3f( 0f,1f,0f ); //green color
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Top Right Of The Quad
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Top Left Of The Quad
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f( 0f,0f,1f ); //blue color
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Top Right Of The Quad (Front)
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Top Left Of The Quad (Front)
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f(1f,1f,0f ); //yellow (red + green)
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Left Of The Quad
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Right Of The Quad
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f * mBlockSize); // Top Right Of The Quad (Back)
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f  * mBlockSize); // Top Left Of The Quad (Back)
					
					gl.glColor3f(1f,0f,1f ); //purple (red + green)
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Top Right Of The Quad (Left)
					gl.glVertex3f(-1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f * mBlockSize); // Top Left Of The Quad (Left)
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Left Of The Quad
					gl.glVertex3f(-1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Right Of The Quad 
					
					gl.glColor3f(0f,1f, 1f ); //sky blue (blue +green)
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, -1.0f * mBlockSize); // Top Right Of The Quad (Right)
					gl.glVertex3f(1.0f * mBlockSize, 1.0f * mBlockSize, 1.0f * mBlockSize); // Top Left Of The Quad
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, 1.0f * mBlockSize); // Bottom Left Of The Quad
					gl.glVertex3f(1.0f * mBlockSize, -1.0f * mBlockSize, -1.0f * mBlockSize); // Bottom Right Of The Quad
				gl.glEnd(); // Done Drawing The Quad
				gl.glFlush();
			}
		}
		gl.glPopMatrix();
	}	
	
	public void updateBlocks(GL2 gl, GLU glu) {
		for (double i = RangesConst.RANGE_BEGIN_FOR_BLOCKS.getValue();
				i < DialDisplay.sWorld.getBodyCount();
				++i) {
			GLBlock glBlocks = (GLBlock) DialDisplay.sWorld.getBody((int) i);
			glBlocks.render(gl,glu);
		}
	}
}
