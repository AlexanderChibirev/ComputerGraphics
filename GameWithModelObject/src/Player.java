import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;



public class Player extends BodyBound {
	private static final float MAX_SIZE = 6.0f;  // for a model's dimension
	private static final int SIZE_WIDTH = 3;  // for a model's dimension
	private static final int SIZE_HEIGHT = 4;  // for a model's dimension
	private static final int MAX_COOLDOWN_WAIT_TIME = 6;  // for a model's dimension
	public static float sShiftX = 0;
	public static float sShiftY = 0;
	
	private double mTankStartAngle = 180;
	private double mAngleForMoveTank = 0;
	private float mCooldown = 0;
	private OBJModel mTankMajorModel;
	private float mSpeed = 0.001f;
	private Vector2d mEndPoint;
	private double mDistance = 0;
	
	public Player(Vector3f pos, GL2 gl, GLU glu) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT,0);
		mTankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
	}
	
	
	
	public void draw(GL2 gl) {
		
		updateRotation(gl);
		updatePosition(gl);
		float shiftForY = 1.8f;
		gl.glTranslated(x, shiftForY, y);
		gl.glRotatef((float) mTankStartAngle, 0, 1, 0);		
		mTankMajorModel.draw(gl);
		
		gl.glTranslated(-x, -shiftForY, -y);
		gl.glRotatef((float) -mTankStartAngle, 0, 1, 0);
		updateBullet(gl);
	}	
	
	
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}

	private void updateBullet(GL2 gl) {
		if(InputHandler.sKeyPressedSpace) {
			if(mCooldown > MAX_COOLDOWN_WAIT_TIME) {
				Bullet.sBulletsArray.add(new Bullet(x, y, 2, 1, mEndPoint, gl));//new GLBall(x, y, 1, 1);
				mCooldown = 0;
			}
		}
		float shiftCooldown = 0.003f;
		mCooldown += shiftCooldown;
	}
	
	private void updateRotation(GL2 gl) {
		float speedRotation = 0.03f;
		if(InputHandler.sKeyPressedA) {
			mTankStartAngle -=speedRotation;
			mAngleForMoveTank -= speedRotation;
		}
		else if(InputHandler.sKeyPressedD) {
			mTankStartAngle += speedRotation;	
			mAngleForMoveTank += speedRotation;
			
		}
		resetAngle();		
	}
	
	private void resetAngle() {
		if(mTankStartAngle > 360) {
			mTankStartAngle = 0;
		}		
		else if(mTankStartAngle < 0) {
			mTankStartAngle = 360;
		}		
		if(mAngleForMoveTank > 360) {
			mAngleForMoveTank = 0;
		}
		else if(mAngleForMoveTank < -270) {			
			mAngleForMoveTank = 90.1f;
		}
	}
	
	private void updatePosition(GL2 gl) {
		double sizeSkybox = WorldConsts.SIZE_SKYBOX.getValue();
		double triangleSide = sizeSkybox 
				* getSinInDegrees(mAngleForMoveTank)
				/ getSinInDegrees(180 - 90 - mAngleForMoveTank);	// 180 - 90 - angleForMoveTank == gama(Y) angle	
		if( (mAngleForMoveTank >= -90 && mAngleForMoveTank <= 0) 
				|| (mAngleForMoveTank <= 90 && mAngleForMoveTank > 0)) { // 1 и 2 ая четверть
			mEndPoint = new Vector2d(triangleSide, sizeSkybox);
		}
		else {//когда нижняя координата переводим в верхнюю и меняем координаты
			if(mAngleForMoveTank < 90) {
				mEndPoint = new Vector2d(-triangleSide, -sizeSkybox);				
			}
			else {
				triangleSide = sizeSkybox
						* getSinInDegrees(mAngleForMoveTank - 90) 
						/ getSinInDegrees(180 - 90 - mAngleForMoveTank - 90);//180 - 90 - angleForMoveTank - 90 =  gama(Y) смещенная для 4ой четверти
				if(mAngleForMoveTank > 180) {
					
					mEndPoint = new Vector2d(-sizeSkybox, -triangleSide);
				}
				else {					
					mEndPoint = new Vector2d(sizeSkybox, triangleSide);
				}		
			}
		}
		mDistance = Math.sqrt((mEndPoint.x - x)*(mEndPoint.x - x) + (mEndPoint.y - y) * (mEndPoint.y - y)); //считаем дистанцию (длину от точки А до точки Б). формула длины вектора		
		if(InputHandler.sKeyPressedW && !InputHandler.sKeyPressedD && !InputHandler.sKeyPressedA) {
			x += mSpeed * (mEndPoint.x - x) / mDistance;
			y += mSpeed * (mEndPoint.y - y) / mDistance;	
			sShiftX = x;
			sShiftY = y;
		}
	}
}
