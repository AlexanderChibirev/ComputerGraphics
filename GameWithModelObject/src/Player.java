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
	
	public static float shiftX = 0;
	public static float shiftY = 0;
	private double angle = 180;
	private double angleForMoveTank = 0;
	private float cooldown = 0;
	private OBJModel tankMajorModel;
	private float speed = 0.001f;
	public static Direction direction = Direction.UP;
	private Vector2d mEndPoint;
	private double mDistance = 0;
	
	public Player(Vector3f pos, GL2 gl, GLU glu) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT);
		tankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
	}
	
	public void draw(GL2 gl) {
		
		updateRotation(gl);
		updatePosition(gl);

		getDirection();		
		float shiftForY = 1.2f;
		gl.glTranslated(x, shiftForY, y);
		gl.glRotatef((float) angle, 0, 1, 0);
		
		tankMajorModel.draw(gl);
		
		gl.glTranslated(-x, -shiftForY, -y);
		gl.glRotatef((float) -angle, 0, 1, 0);
		updateBullet(gl);
	}	
	
	
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}
	
	private double getCosDegrees(double angle) {
		return Math.cos( Math.toRadians(angle));
	}
	
	private void updateBullet(GL2 gl) {
		if(InputHandler.sKeyPressedSpace) {
			if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
				GameGLListener.glball.add(new GLBullet(x, y, 2, 1, mEndPoint, angle));//new GLBall(x, y, 1, 1);
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
		float speedRotation = 0.03f;
		if(InputHandler.sKeyPressedA) {
			angle -=speedRotation;
			angleForMoveTank -= speedRotation;
		}
		else if(InputHandler.sKeyPressedD) {
			angle += speedRotation;	
			angleForMoveTank += speedRotation;
			
		}
		
		if(angle > 360) {
			angle = 0;
		}		
		if(angle < 0) {
			angle = 360;
		}
		
		if(angleForMoveTank > 360) {		
			angleForMoveTank = 0;
		}
		if(angleForMoveTank < -270) {			
			angleForMoveTank = 90.1f;
		}
	}
	
	private void updatePosition(GL2 gl) {
		double sizeSkybox = WorldConsts.SIZE_SKYBOX.getValue();
		double triangleSide = sizeSkybox 
				* getSinInDegrees(angleForMoveTank)
				/ getSinInDegrees(180 - 90 - angleForMoveTank);	// 180 - 90 - angleForMoveTank == gama(Y) angle	
		if( (angleForMoveTank >= -90 && angleForMoveTank <= 0) 
				|| (angleForMoveTank <= 90 && angleForMoveTank > 0)) { // 1 � 2 �� ��������
			mEndPoint = new Vector2d(triangleSide, sizeSkybox);
		}
		else {//����� ������ ���������� ��������� � ������� � ������ ����������
			if(angleForMoveTank < 90) {
				mEndPoint = new Vector2d(-triangleSide, -sizeSkybox);				
			}
			else {
				triangleSide = sizeSkybox
						* getSinInDegrees(angleForMoveTank - 90) 
						/ getSinInDegrees(180 - 90 - angleForMoveTank - 90);//180 - 90 - angleForMoveTank - 90 =  gama(Y) ��������� ��� 4�� ��������
				if(angleForMoveTank > 180) {
					
					mEndPoint = new Vector2d(-sizeSkybox, -triangleSide);
				}
				else {					
					mEndPoint = new Vector2d(sizeSkybox, triangleSide);
				}		
			}
		}
		mDistance = Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y)); //������� ��������� (����� �� ����� � �� ����� �). ������� ����� �������		
		if(InputHandler.sKeyPressedW && !InputHandler.sKeyPressedD && !InputHandler.sKeyPressedA) {
			x += speed * (mEndPoint.x - x) / mDistance;
			y += speed * (mEndPoint.y - y) / mDistance;			
			shiftX = x;
			shiftY = y;
		}		
	}
}
