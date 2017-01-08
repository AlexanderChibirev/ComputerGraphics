import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

import OBJLoader.OBJModel;



public class Player {
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private Vector3f position;
	private float angle = 180;
	private OBJModel tankMajorModel;
	public static Direction direction = Direction.UP;
	
	public Player(Vector3f pos, GL2 gl) {
		position = pos;
		tankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
	}
	public void draw(GL2 gl) {
		updatePosition(gl);
		updateRotation(gl);
		getDirection();
		tankMajorModel.draw(gl);
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
		gl.glRotatef(angle, 0, 1, 0);
	}
	
	private void updatePosition(GL2 gl) {
		final float shiftTranslated = 0.001f;
		if(InputHandler.sKeyPressedW){
			if(direction == Direction.UP) {
				position.z += shiftTranslated;
			}
			else if(direction == Direction.DOWN) {
				position.z -= shiftTranslated;
			}
			else if(direction == Direction.RIGHT) {
				position.x += shiftTranslated;
			}	
			else if(direction == Direction.LEFT) {
				position.x -= shiftTranslated;
			}
			//System.out.println(position.x); //75
		}
		gl.glTranslated(position.x, position.y, position.z);
	}
}
