

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBullet extends BodyBound {
	private Direction directionBullet;
	private double mAngle;
	private float speed = 0.01f;
	private double mDistance;
	private Vector2d mEndPoint;
	public GLBullet(float x, float y, int width, int height, double angle) {
		super(x, y, width, height);
		mAngle = angle - 180;
		//mAngle = 1;//Math.abs(angle - 90);
		//directionBullet = Player.direction;
		System.out.print("mAngle: ");
		System.out.println(mAngle);
		//System.out.println(getSinInDegrees(180 - mAngle - 90));
		//System.out.println(getSinInDegrees(90));
		
		
		double a = 75 * getSinInDegrees(90) / getSinInDegrees(180 - 90 - mAngle);		
		double b = 75 * getSinInDegrees(mAngle) / getSinInDegrees(180 - 90 - mAngle);
		System.out.print("b: ");
		System.out.println(b);
		System.out.print("a: ");
		System.out.println(a);
		
		if(mAngle < 45) {
			System.out.println("TYT");
			mEndPoint = new Vector2d(b, 80);
		}
		else{
			mEndPoint = new Vector2d(80, a);
		}
		
		mDistance = Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));//считаем дистанцию (длину от точки ј до точки Ѕ). формула длины вектора
		//double result  = getCosInDegrees(mAngle);
		//System.out.println( result ) ;
		
		
		//System.out.println(toDeg(Math.sin(1)));
		
	}
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}
	
	private double getCosInDegrees(double angle) {
		return Math.cos( Math.toRadians(angle));
	}

	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
	//	System.out.print("Ball x: ");
	//	System.out.println(x);
	//	System.out.print("Ball y: ");
	//	System.out.println(y);
		float shift = 0.01f;

		//x += getCosInDegrees(mAngle) * speed -  getSinInDegrees(mAngle) * speed;
		//y += getSinInDegrees(mAngle)* speed + getCosInDegrees(mAngle) * speed;
		//System.out.print("mAngle: ");
		//System.out.println(mAngle);
		//System.out.print("Ball xx: ");
		//System.out.println(x);
		//System.out.print("Ball yy: ");
		//System.out.println(y);
		x += 0.01* (mEndPoint.x - x) / mDistance; //идем по иксу с помощью вектора нормали
		y += 0.01* (mEndPoint.y - y) / mDistance; //идем по игреку так же
		
		gl.glTranslated(x, 0.5f, y);
		/*switch (directionBullet) {
			case UP:	
				y += 0.01;
				break;
			case DOWN:	
				y -= 0.01;
				break;
			case LEFT: 
				x -= 0.01;
				break;
			case RIGHT: 
				x += 0.01;
				break;
		}
		*/
		GLUquadric quad = glu.gluNewQuadric();
		double radius = 0.05; 
		int slices = 10;
		int stacks = 10;
		glu.gluSphere(quad, radius, slices, stacks);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();
		
	}
}
	
