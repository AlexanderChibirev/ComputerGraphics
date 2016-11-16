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
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		gl.glTranslated(this.transform.getTranslationX(), this.transform.getTranslationY(), 0.0);
		gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
		for (BodyFixture fixture : this.fixtures) {
			//System.out.println(fixture.);
			Convex convex = fixture.getShape();
			if (convex instanceof Polygon) {
				Polygon p = (Polygon) convex;
				gl.glColor4fv(this.color, 0);
				gl.glBegin(GL2.GL_QUADS);
				float sizeBlock = (float) Math.abs(p.getVertices()[0].x);
				for (Vector2 v : p.getVertices()) {
					
					gl.glVertex3d(v.x, v.y, 0.0);
				}
				//2
				gl.glVertex3f( sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock );
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, sizeBlock ); 
				//3
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock );
				gl.glVertex3f( -sizeBlock, sizeBlock, -sizeBlock );
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock ); 
				//4
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock ); 
				gl.glVertex3f( -sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock );
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock );
				//5
				gl.glVertex3f( -sizeBlock, -sizeBlock, sizeBlock );
				gl.glVertex3f( sizeBlock, -sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, -sizeBlock, -sizeBlock );
				gl.glVertex3f( -sizeBlock, -sizeBlock, -sizeBlock );
				//6
				gl.glVertex3f( -sizeBlock, sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, sizeBlock ); 
				gl.glVertex3f( sizeBlock, sizeBlock, -sizeBlock ); 
				gl.glVertex3f( -sizeBlock, sizeBlock, -sizeBlock );
			
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
