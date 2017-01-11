import java.util.Vector;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;

public class Bullet extends BodyBound {
	public static Vector<Bullet> sBulletsArray = new Vector<>();
	private float mSpeed = 0.1f;
	private double mDistance;
	private Vector2d mEndPoint;
	private OBJModel mBullet;
	private float shiftY;
	
	public Bullet(float x, float y, int width, int height, Vector2d endPoint, GL2 gl, float shiftY) {
		super(x, y, 2, 2, 0);
		mEndPoint = endPoint;
		mBullet = new OBJModel("bullet", 0.3f, gl, true);
		this.shiftY= shiftY;
	}
	
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		mDistance = Math.sqrt((mEndPoint.x - x) * (mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));
		x += mSpeed * (mEndPoint.x - x) / mDistance; //идем по иксу с помощью вектора нормали
		y += mSpeed * (mEndPoint.y - y) / mDistance; //идем по игреку так же
		gl.glTranslated(x, shiftY, y);
		float rotateBullet = 50;
		gl.glRotatef(-rotateBullet, 1,0, 0);
		mBullet.draw(gl);
		gl.glPopMatrix();
	}
}
	
