import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class GLBlock extends Body {
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
			
				gl.glBegin(GL2.GL_QUADS);
				float sizeBlock = (float) Math.abs(p.getVertices()[0].x);
				//1
				gl.glColor4f(1,0,0,0);
				gl.glVertex3f( -sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, sizeBlock );
				gl.glVertex3f( sizeBlock, sizeBlock, sizeBlock ); 
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock );
				gl.glColor4f(0,1,0,0);
				//2
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( -sizeBlock, sizeBlock, -sizeBlock );
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock ); 
				//3
				gl.glColor4f(0,0,1,1);
				gl.glVertex3f( -sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock );
				gl.glVertex3f( -sizeBlock, sizeBlock, -sizeBlock );
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock ); 
				//4
				gl.glColor4f(1,1,0.01f,1);
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, sizeBlock );
				gl.glVertex3f( sizeBlock, -sizeBlock, sizeBlock );
				//5
				gl.glColor4f(0, 1, 1,1);
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock );
				gl.glVertex3f( sizeBlock, sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock );
				gl.glVertex3f( -sizeBlock, sizeBlock, -sizeBlock );
				//6
				gl.glColor4f(1, 0, 1, 1);
				gl.glVertex3f( -sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, sizeBlock );
			
			    gl.glEnd();
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
