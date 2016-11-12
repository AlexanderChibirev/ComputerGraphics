import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Cube {
	private final float mSize = 0.3f;
	private void setMatrixModeForCube(GL2 gl, GLU glu)
	{
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(55.0f,1.3,1.0,20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef( 0f, 0f, -5.0f ); 
	}
	
	private void setMatrixModeForGame(GL2 gl)
	{
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-400, 400, -300, 300, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public void draw(GL2 gl, GLU glu) {
		setMatrixModeForCube(gl, glu);
		// Rotate The Cube On X, Y & Z
		gl.glRotatef(50, 1.0f, 1.0f, 1.0f); 
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
			gl.glColor3f(1f ,0f,0f); //red color
			gl.glVertex3f(1.0f * mSize, 1.0f * mSize, -1.0f * mSize); // Top Right Of The Quad (Top)
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, -1.0f * mSize); // Top Left Of The Quad (Top)
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, 1.0f * mSize); // Bottom Left Of The Quad (Top)
			gl.glVertex3f( 1.0f * mSize, 1.0f * mSize, 1.0f * mSize); // Bottom Right Of The Quad (Top)
			
			gl.glColor3f( 0f,1f,0f ); //green color
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Top Right Of The Quad
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Top Left Of The Quad
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, -1.0f * mSize); // Bottom Left Of The Quad
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, -1.0f * mSize); // Bottom Right Of The Quad 
			
			gl.glColor3f( 0f,0f,1f ); //blue color
			gl.glVertex3f( 1.0f * mSize, 1.0f * mSize, 1.0f * mSize ); // Top Right Of The Quad (Front)
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, 1.0f * mSize ); // Top Left Of The Quad (Front)
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Bottom Left Of The Quad
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Bottom Right Of The Quad 
			
			gl.glColor3f( 1f,1f,0f ); //yellow (red + green)
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, -1.0f * mSize ); // Bottom Left Of The Quad
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, -1.0f * mSize ); // Bottom Right Of The Quad
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, -1.0f * mSize ); // Top Right Of The Quad (Back)
			gl.glVertex3f( 1.0f * mSize, 1.0f * mSize, -1.0f  * mSize); // Top Left Of The Quad (Back)
			
			gl.glColor3f( 1f,0f,1f ); //purple (red + green)
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, 1.0f * mSize ); // Top Right Of The Quad (Left)
			gl.glVertex3f( -1.0f * mSize, 1.0f * mSize, -1.0f * mSize ); // Top Left Of The Quad (Left)
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, -1.0f * mSize); // Bottom Left Of The Quad
			gl.glVertex3f( -1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Bottom Right Of The Quad 
			
			gl.glColor3f( 0f,1f, 1f ); //sky blue (blue +green)
			gl.glVertex3f( 1.0f * mSize, 1.0f * mSize, -1.0f * mSize ); // Top Right Of The Quad (Right)
			gl.glVertex3f( 1.0f * mSize, 1.0f * mSize, 1.0f * mSize ); // Top Left Of The Quad
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, 1.0f * mSize); // Bottom Left Of The Quad
			gl.glVertex3f( 1.0f * mSize, -1.0f * mSize, -1.0f * mSize); // Bottom Right Of The Quad
		gl.glEnd(); // Done Drawing The Quad
		gl.glFlush();	
		setMatrixModeForGame(gl);
	}
}
