import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Cube {
	private final float mSize = 8.5f;
	private final float mSizes = 0.1f;
	
	public void draw(GL2 gl) {
		gl.glTranslated(0, 0, -1f);
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
			gl.glColor3f(1f ,1f,1f); //red color
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, -1.0f * mSizes); // Top Right Of The Quad (Top)
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, -1.0f * mSizes); // Top Left Of The Quad (Top)
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Bottom Left Of The Quad (Top)
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Bottom Right Of The Quad (Top)
			
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Top Right Of The Quad
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Top Left Of The Quad
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Left Of The Quad
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Right Of The Quad 
			
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Top Right Of The Quad (Front)
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Top Left Of The Quad (Front)
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Bottom Left Of The Quad
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Bottom Right Of The Quad 
			
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Left Of The Quad
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Right Of The Quad
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, -1.0f * mSizes); // Top Right Of The Quad (Back)
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, -1.0f  * mSizes); // Top Left Of The Quad (Back)
			
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Top Right Of The Quad (Left)
			gl.glVertex3f(-1.0f * mSize, 1.0f * mSize, -1.0f * mSizes); // Top Left Of The Quad (Left)
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Left Of The Quad
			gl.glVertex3f(-1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Bottom Right Of The Quad 
			

			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, -1.0f * mSizes); // Top Right Of The Quad (Right)
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, 1.0f * mSizes); // Top Left Of The Quad
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, 1.0f * mSizes); // Bottom Left Of The Quad
			gl.glVertex3f(1.0f * mSize, -1.0f * mSize, -1.0f * mSizes); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad
		gl.glTranslated(0, 0, 1f);
		gl.glFlush();
	}
}
