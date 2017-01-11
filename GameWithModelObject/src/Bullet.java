import java.util.Vector;
import javax.vecmath.Vector2d;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;

public class Bullet extends BodyBound {
	public static Vector<Bullet> sBulletsArray = new Vector<>();
	private float speed = 0.1f;
	private double distance;
	private Vector2d endPoint;
	private OBJModel bullet;
	private float shiftY;
	
	public Bullet(float x, float y, int width, int height, Vector2d endPoint, GL2 gl, float shiftY) {
		super(x, y, 2, 2, 0);
		this.endPoint = endPoint;
		this.bullet = new OBJModel("bullet", 0.3f, gl, true);
		this.shiftY= shiftY;
	}
	
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		distance = Math.sqrt((endPoint.x - x) * (endPoint.x - x) + (endPoint.y - y) * (endPoint.y - y));
		x += speed * (endPoint.x - x) / distance; //идем по иксу с помощью вектора нормали
		y += speed * (endPoint.y - y) / distance; //идем по игреку так же
		gl.glTranslated(x, shiftY, y);
		float rotateBullet = 50;
		gl.glRotatef(-rotateBullet, 1,0, 0);
		bullet.draw(gl);
		gl.glPopMatrix();
	}
}
	
