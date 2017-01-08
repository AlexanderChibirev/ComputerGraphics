import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

import Utils.TextureUtils;

public class RectangularPrism {
	private Texture starsTex;
	private Vector3f mSize;
	RectangularPrism(Vector3f size, GL2 gl) {
		this.mSize = size;
		loadTextures(gl);
	}
	
	public void setSize(Vector3f size) {
		mSize = size;
	}
	
	private void loadTextures(GL2 gl)
	{
	    starsTex = TextureUtils.loadTexture("stars.jpg", gl);
	    starsTex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	    starsTex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	}  // end of loadTextures()
	
	public void drawFloor(GL2 gl, int textureID) {
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
	
	public void drawStars(GL2 gl, int size)
	{
		gl.glDisable(GL2.GL_LIGHTING);
		// enable texturing and choose the 'stars' texture
		gl.glEnable(GL2.GL_TEXTURE_2D);
		starsTex.bind(gl);
		TextureCoords tc = starsTex.getImageTexCoords();
		float lf = tc.left();
		float r = tc.right();
		float b = tc.bottom();
		float t = tc.top();
		// replace the quad colors with the texture
		gl.glTexEnvf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE,
		GL2.GL_REPLACE);
		gl.glBegin(GL2.GL_QUADS);
		// back wall
		int edge = size/2;
		gl.glTexCoord2f(lf, b); gl.glVertex3i(-edge, 0, -edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(edge, 0, -edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(edge, edge, -edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(-edge, edge, -edge);
		// right wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, 0, -edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(edge, 0, edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(edge, edge, edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(edge, edge, -edge);
		// front wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, 0, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, 0, edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(-edge, edge, edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(edge, edge, edge);
		// left wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(-edge, 0, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, 0, -edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(-edge, edge, -edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(-edge, edge, edge);
		// ceiling
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, edge, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, edge, edge);
		gl.glTexCoord2f(2*r, 2*t); gl.glVertex3i(-edge, edge, -edge);
		gl.glTexCoord2f(lf, 2*t); gl.glVertex3i(edge, edge, -edge);
		gl.glEnd();
		// switch back to modulation of quad colors and texture
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE,
		GL2.GL_MODULATE);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_LIGHTING);
	} // end of drawStars()
	
	
	public void drawBackground (GL2 gl, Vector<Integer> texturesID) {
		gl.glDisable(GL.GL_DEPTH_TEST); 
		gl.glDisable(GL.GL_CULL_FACE); 
		gl.glEnable(GL2.GL_TEXTURE_2D);	
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0, 1, 0, 1, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glDepthMask(false);
		gl.glEnable(GL2.GL_BLEND);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE); //importante
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); //importante
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texturesID.get(PossitionID.BACKGROUND.getValue()));	
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(0f,0f); gl.glVertex2f(0,0);
			gl.glTexCoord2f(0f,1f); gl.glVertex2f(0,1f);
			gl.glTexCoord2f(1f,1f); gl.glVertex2f(1,1);
			gl.glTexCoord2f(1f,0f); gl.glVertex2f(1,0);
		gl.glEnd();		
		gl.glDepthMask(true);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPopMatrix();
	}
}
