import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

import OBJLoader.OBJModel;

public class Enemy extends BodyBound {
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private float angle = 111;
	private OBJModel tank;
	public static Direction direction = Direction.UP;
	public Enemy(Vector3f pos, GL2 gl, float angle) {
		super(pos.x, pos.z, 4, 4);
		tank = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
	}

	public void draw(GL2 gl) {
		// TODO Auto-generated method stub
		System.out.println(x);
		System.out.println(y);
		gl.glTranslated(x, 1.2f, y);
		gl.glRotatef(angle, 0, 1, 0);
		tank.draw(gl);
		gl.glTranslated(-x, -1.2f, -y);
		gl.glRotatef(-angle, 0, 1, 0);
	}

}
