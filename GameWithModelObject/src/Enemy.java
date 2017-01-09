import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

import OBJLoader.OBJModel;

public class Enemy extends BodyBound {
	
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private static final int SIZE_WIDTH = 3;  // for a model's dimension
	private static final int SIZE_HEIGHT = 4;  // for a model's dimension
	
	private float angle = 0;
	private OBJModel tank;
	private Direction direction = Direction.UP;
	
	public Enemy(Vector3f pos, GL2 gl, float angle) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT);
		tank = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
	}

	public void draw(GL2 gl) {
		// TODO Auto-generated method stub
		
		//update state
		gl.glTranslated(x, 1.2f, y);
		gl.glRotatef(angle, 0, 1, 0);
		
		tank.draw(gl);

		//return old state for next object
		gl.glTranslated(-x, -1.2f, -y);
		gl.glRotatef(-angle, 0, 1, 0);
		
	}
}
