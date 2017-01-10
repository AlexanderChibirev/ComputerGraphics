

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBullet extends BodyBound {
	
	private double mAngle;
	private float speed = 0.01f;
	private double mDistance;
	private Vector2d mEndPoint;
	public GLBullet(float x, float y, int width, int height, double angle) {
		super(x, y, width, height);
		
		mAngle = angle - 180;		
		
		
		
		
		double b = 75 * getSinInDegrees(mAngle) / getSinInDegrees(180 - 90 - mAngle);		
		if( (mAngle >= -90 && mAngle <= 0) || (mAngle <= 90 && mAngle > 0)) {
			mEndPoint = new Vector2d(b, 80);
		}
		else {
			if(mAngle < 90) {
				mEndPoint = new Vector2d(-b, -80);
			}
			else {
				mAngle -= 90;
				b = 75 * getSinInDegrees(mAngle) / getSinInDegrees(180 - 90 - mAngle);
				mEndPoint = new Vector2d(80, -b);				
			}
		}
		mDistance = Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));//считаем дистанцию (длину от точки ј до точки Ѕ). формула длины вектора		
	}
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}

	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
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
	
