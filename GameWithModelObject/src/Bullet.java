import java.util.Vector;

import javax.vecmath.Vector2d;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;

public class Bullet extends BodyBound {
	public static Vector<Bullet> sBulletsArray = new Vector<>();
	private float mSpeed = 0.01f;
	private double mDistance;
	private Vector2d mEndPoint;
	private OBJModel mBullet;
	
	public Bullet(float x, float y, int width, int height, Vector2d endPoint, GL2 gl) {
		super(x, y, width, height, 0);
		mEndPoint = endPoint;
		mBullet = new OBJModel("bullet", 0.3f, gl, true);
	}
	
	public void render(GL2 gl, GLU glu) {		
		gl.glPushMatrix();
		mDistance = Math.sqrt((mEndPoint.x - x) * (mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));
		x += mSpeed * (mEndPoint.x - x) / mDistance; //идем по иксу с помощью вектора нормали
		y += mSpeed * (mEndPoint.y - y) / mDistance; //идем по игреку так же
		gl.glTranslated(x, 0.5f, y);
		float rotateBullet = 50;
		gl.glRotatef(-rotateBullet, 1,0, 0);
		mBullet.draw(gl);
		gl.glPopMatrix();		
	}
}
	
