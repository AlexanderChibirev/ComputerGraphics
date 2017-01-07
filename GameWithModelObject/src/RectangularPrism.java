import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class RectangularPrism {
	private Vector3f mSize;
	RectangularPrism(Vector3f size) {
		this.mSize = size;
	}
	
	public void setSize(Vector3f size) {
		mSize = size;
	}
	
	public void draw(GL2 gl, int textureID) {
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textureID);
		gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, -1.0f * mSize.z); // Top Right Of The Quad (Top)
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, -1.0f * mSize.z); // Top Left Of The Quad (Top)
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Bottom Left Of The Quad (Top)
			gl.glTexCoord2f(0.0f,1.0f);	gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Bottom Right Of The Quad (Top)
			
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Top Right Of The Quad
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Top Left Of The Quad
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Left Of The Quad
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Right Of The Quad 
			
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Top Right Of The Quad (Front)
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Top Left Of The Quad (Front)
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Bottom Left Of The Quad
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Bottom Right Of The Quad 
			
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Left Of The Quad
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Right Of The Quad
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, -1.0f * mSize.z); // Top Right Of The Quad (Back)
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, -1.0f  * mSize.z); // Top Left Of The Quad (Back)
			
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Top Right Of The Quad (Left)
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, 1.0f * mSize.y, -1.0f * mSize.z); // Top Left Of The Quad (Left)
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Left Of The Quad
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Bottom Right Of The Quad 
			

			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, -1.0f * mSize.z); // Top Right Of The Quad (Right)
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f * mSize.x, 1.0f * mSize.y, 1.0f * mSize.z); // Top Left Of The Quad
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, 1.0f * mSize.z); // Bottom Left Of The Quad
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f * mSize.x, -1.0f * mSize.y, -1.0f * mSize.z); // Bottom Right Of The Quad
		gl.glEnd();
		gl.glFlush();		
	}
}
