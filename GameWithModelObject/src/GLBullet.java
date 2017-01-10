import javax.vecmath.Vector2d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBullet extends BodyBound {
	private float speed = 0.01f;
	private double mDistance;
	private Vector2d mEndPoint;
	public GLBullet(float x, float y, int width, int height, Vector2d endPoint, double angle) {
		super(x, y, width, height);
		mEndPoint = endPoint;
	} 
	

	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		mDistance = Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));
		x += speed * (mEndPoint.x - x) / mDistance; //идем по иксу с помощью вектора нормали
		y += speed * (mEndPoint.y - y) / mDistance; //идем по игреку так же
		gl.glTranslated(x, 0.5f, y);
		GLUquadric quad = glu.gluNewQuadric();
		double radius = 0.05; 
		int slices = 10;
		int stacks = 10;
		glu.gluSphere(quad, radius, slices, stacks);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();		
	}
}
	
