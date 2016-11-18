import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class GLBall extends Body {
		protected float[] color = new float[4];
		public GLBall() {
			this.color[0] = (float)Math.random() * 0.5f + 0.5f;
			this.color[1] = (float)Math.random() * 0.5f + 0.5f;
			this.color[2] = (float)Math.random() * 0.5f + 0.5f;
			this.color[3] = 1.0f;
		}
		
		public void gltDrawSphere(GL2 gl, float fRadius, int iSlices, int iStacks)  
		  {  
		  float drho = (float)(3.141592653589) / (float) iStacks;  
		  float dtheta = 2.0f * (float)(3.141592653589) / (float) iSlices;  
		  float ds = 1.0f / (float) iSlices;  
		  float dt = 1.0f / (float) iStacks;  
		  float t = 1.0f;      
		  float s = 0.0f;  
		  int i, j;     // Looping variables  

		  for (i = 0; i < iStacks; i++)   
		      {  
		      float rho = (float)i * drho;  
		      float srho = (float)(Math.sin(rho));  
		      float crho = (float)(Math.cos(rho));  
		      float srhodrho = (float)(Math.sin(rho + drho));  
		      float crhodrho = (float)(Math.cos(rho + drho));  

		      gl.glBegin(GL.GL_TRIANGLE_STRIP);  
		      s = 0.0f;  
		      for ( j = 0; j <= iSlices; j++)   
		          {  
		          float theta = (j == iSlices) ? 0.0f : j * dtheta;  
		          float stheta = (float)(-Math.sin(theta));  
		          float ctheta = (float)(Math.cos(theta));  

		          float x = stheta * srho;  
		          float y = ctheta * srho;  
		          float z = crho;  
		            
		          gl.glTexCoord2f(s, t);  
		          gl.glNormal3f(x, y, z);  
		          gl.glVertex3f(x * fRadius, y * fRadius, z * fRadius);  

		          x = stheta * srhodrho;  
		          y = ctheta * srhodrho;  
		          z = crhodrho;  
		          gl.glTexCoord2f(s, t - dt);  
		          s += ds;  
		          gl.glNormal3f(x, y, z);  
		          gl.glVertex3f(x * fRadius, y * fRadius, z * fRadius);  
		          }  
		      gl.glEnd();  

		      t -= dt;  
		      }  
		  }  
		
		public void render(GL2 gl) {
			gl.glPushMatrix();
			System.out.print("X = ");
			System.out.println(this.transform.getTranslationX());
			System.out.println("==================================");
			System.out.print("Y = ");
			System.out.println(this.transform.getTranslationY());
			gl.glTranslated(this.transform.getTranslationX() , this.transform.getTranslationY(), 0.0);
			gl.glRotated(Math.toDegrees(this.transform.getRotation()), 0.0, 0.0, 1.0);
			gltDrawSphere(gl,1.0f,10, 10);
			/*for (BodyFixture fixture : this.fixtures) {
				Convex convex = fixture.getShape();
				if (convex instanceof Polygon) {
					Polygon p = (Polygon) convex;
					gl.glColor4fv(this.color, 0);
					gl.glBegin(GL2.GL_POLYGON);
					for (Vector2 v : p.getVertices()) {
						gl.glVertex3d(v.x, v.y, 0.0);
					}
					gl.glEnd();
					gl.glColor4f(this.color[0] * 0.8f, this.color[1] * 0.8f, this.color[2] * 0.8f, 1.0f);
					gl.glBegin(GL.GL_LINE_LOOP);
					for (Vector2 v : p.getVertices()) {
						gl.glVertex3d(v.x, v.y, 0.0);
					}
					gl.glEnd();
				}
			}*/
			gl.glPopMatrix();
		}
}
	
