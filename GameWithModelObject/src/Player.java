import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;



public class Player extends BodyBound {
	
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private static final int SIZE_WIDTH = 3;  // for a model's dimension
	private static final int SIZE_HEIGHT = 4;  // for a model's dimension
	private static final int MAX_COOLDOWN_WAIT_TIME = 6;  // for a model's dimension
	
	private float angle = 180;
	private float cooldown = 0;
	private OBJModel tankMajorModel;
	public static Direction direction = Direction.UP;
	
	public Player(Vector3f pos, GL2 gl, GLU glu) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT);
		tankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
	}
	public void draw(GL2 gl) {
		updatePosition(gl);
		float shiftForY = 1.2f;
		updateRotation(gl);
		getDirection();
		//update state
		//System.out.println(x);
		gl.glTranslated(x, shiftForY, y);
		gl.glRotatef(angle, 0, 1, 0);
		
		tankMajorModel.draw(gl);
		//return old state for next object
		gl.glTranslated(-x, -shiftForY, -y);
		gl.glRotatef(-angle, 0, 1, 0);
		
		updateBullet(gl);
	}	
	
	private void updateBullet(GL2 gl) {
		if(InputHandler.sKeyPressedSpace) {
			if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
				GameGLListener.glball.add(new GLBall(x, y, 2, 1));//new GLBall(x, y, 1, 1);
				cooldown = 0;
			}
		}
		float shiftCooldown = 0.003f;
		cooldown += shiftCooldown;
	}
	private void getDirection() {
		if(angle >= 157 && angle <= 193) {
			direction = Direction.UP;
		}
		else if(angle >= 336 && angle <= 360 || angle >= 0 && angle <= 50 ) {
			direction = Direction.DOWN;
		}
		else if(angle >= 69 && angle <= 120) {
			direction = Direction.LEFT;
		}
		else if(angle >= 251 && angle <= 280) {
			direction = Direction.RIGHT;
		}
	}
	
	private void updateRotation(GL2 gl) {
		if(InputHandler.sKeyPressedA) {
			angle -= 0.03;
		}
		else if(InputHandler.sKeyPressedD) {
			angle += 0.03;			
		}
		
		if(angle > 360) {
			angle = 0;
		}
		if(angle < 0) {
			angle = 360;
		}		
	}
	
	private void updatePosition(GL2 gl) {
		final float shiftTranslated = 0.001f;
		if(InputHandler.sKeyPressedW){
			if(direction == Direction.UP) {
				y += shiftTranslated;
			}
			else if(direction == Direction.DOWN) {
				y -= shiftTranslated;
			}
			else if(direction == Direction.RIGHT) {
				x += shiftTranslated;
			}	
			else if(direction == Direction.LEFT) {
				x -= shiftTranslated;
			}
			//System.out.println(position.x); //75
		}		
	}
}
