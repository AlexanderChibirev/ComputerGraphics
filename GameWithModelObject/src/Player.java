import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;



public class Player extends BodyBound {
	
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private static final int SIZE_WIDTH = 3;  // for a model's dimension
	private static final int SIZE_HEIGHT = 4;  // for a model's dimension
	private static final int MAX_COOLDOWN_WAIT_TIME = 6;  // for a model's dimension
	
	private double angle = 180;
	private double angleForMove = 0;
	private float cooldown = 0;
	private OBJModel tankMajorModel;
	private float speed = 0.01f;
	public static Direction direction = Direction.UP;
	private Vector2d mEndPoint;
	private double mDistance = 0;
	
	public Player(Vector3f pos, GL2 gl, GLU glu) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT);
		tankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
	}
	public void draw(GL2 gl) {
		updatePosition(gl);
		float shiftForY = 1.2f;
		updateRotation(gl);		
		/////////////////
		System.out.print("angleForMove in Player: ");
		System.out.println(angleForMove);
		
		System.out.print("mEndPoint in Player: ");
		System.out.println(mEndPoint);
		//System.out.print("angleForMove: ");
		//System.out.println(angleForMove);
	
		
		double b = 75 * getSinInDegrees(angleForMove) / getSinInDegrees(180 - 90 - angleForMove);		
		if( (angleForMove >= -90 && angleForMove <= 0) || (angleForMove <= 90 && angleForMove > 0)) {
			mEndPoint = new Vector2d(b, 80);
		}
		else {
			if(angleForMove < 90) {
				mEndPoint = new Vector2d(-b, -80);
			}
			else {
				System.out.println("TET");
				//angleForMove -= 90;
				b = 75 * getSinInDegrees(angleForMove - 90) / getSinInDegrees(180 - 90 - angleForMove - 90);
				if(angleForMove > 180) {
					mEndPoint = new Vector2d(-80, -b);
				}
				else{mEndPoint = new Vector2d(80, b);}		
			}
		}
		mDistance = 2000; //Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y));//считаем дистанцию (длину от точки ј до точки Ѕ). формула длины вектора		
		
		getDirection();
		//update state
		gl.glTranslated(x, shiftForY, y);
		gl.glRotatef((float) angle, 0, 1, 0);
		
		tankMajorModel.draw(gl);
		//return old state for next object
		gl.glTranslated(-x, -shiftForY, -y);
		gl.glRotatef((float) -angle, 0, 1, 0);
		
		updateBullet(gl);
	}	
	
	
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}
	
	private void updateBullet(GL2 gl) {
		if(InputHandler.sKeyPressedSpace) {
			if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
				System.out.println(mEndPoint);
				GameGLListener.glball.add(new GLBullet(x, y, 2, 1, mEndPoint));//new GLBall(x, y, 1, 1);
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
			angleForMove -= 0.03;
			/*if(angleForMove < 0) {
				//angleForMove = 360;
			}
			else{
				
			}*/
		}
		else if(InputHandler.sKeyPressedD) {
			angle += 0.03;	
			angleForMove += 0.03;
			
		}		
		if(angle > 360) {
			angle = 0;
		}
		if(angle < 0) {
			angle = 360;
		}
		
		//System.out.println(angleForMove);
		if(angleForMove > 360) {			
			angleForMove = 0;
		}
		if(angleForMove < -270) {			
			angleForMove = 90;
		}
	}
	
	private void updatePosition(GL2 gl) {
		final float shiftTranslated = 0.001f;
		if(InputHandler.sKeyPressedW){
			if(direction == Direction.UP) {
				//x += speed * (mEndPoint.x - x) / mDistance; //идем по иксу с помощью вектора нормали				//y += speed * (mEndPoint.y - y) / mDistance; //идем по игреку так же
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
		}		
	}
}
