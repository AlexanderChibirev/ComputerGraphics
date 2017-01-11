import javax.vecmath.Vector2d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;



public class Player extends BodyBound {
	private static final float MAX_SIZE = 6.0f;  // for a model's dimension
	private static final int SIZE_WIDTH = 4;  // for a model's dimension
	private static final int SIZE_HEIGHT = 7;  // for a model's dimension
	private static final int MAX_COOLDOWN_WAIT_TIME = 6;  // for a model's dimension
	
	public static float sShiftX = 0;
	public static float sShiftY = 0;
	
	private float speedRotation = 0.3f;
	private float cooldown = 0;
	
	private double tankStartAngle = 180;
	private double angleForMoveTank = 0;
	
	private OBJModel tankModel;
	private float speed = 0.03f;
	private Vector2d endPointForMovingTank;
	private Vector2d oldPosition;
	
	public Player(Vector3f pos, GL2 gl, GLU glu) {
		super(pos.x, pos.z, SIZE_WIDTH, SIZE_HEIGHT,0);
		tankModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
		oldPosition = new Vector2d(this.x, this.y);
	}
	
	
	
	public void draw(GL2 gl) {
		updateRotation(gl);
		updatePosition(gl);
		float shiftForY = 1.8f;
		gl.glTranslated(x, shiftForY, y);
		gl.glRotatef((float) tankStartAngle, 0, 1, 0);		
		tankModel.draw(gl);
		
		gl.glTranslated(-x, -shiftForY, -y);
		gl.glRotatef((float) -tankStartAngle, 0, 1, 0);
		updateBullet(gl);
		for(BodyBound block : BlockDestroyable.sBlockDestroyables) {
			if(this.getBounds().intersects(block.getBounds())) {
				x = (float) oldPosition.x;
				y = (float) oldPosition.y;
			}
		}	
		for(BodyBound block : BlockUndestroyable.sBlockUndestroyables) {
			if(this.getBounds().intersects(block.getBounds())) {
				x = (float) oldPosition.x;
				y = (float) oldPosition.y;
			}
		}	
	}	
	
	private double getSinInDegrees(double angle) {
		return Math.sin( Math.toRadians(angle));
	}

	private void updateBullet(GL2 gl) {
		if(InputHandler.sKeyPressedSpace) {
			if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
				Bullet.sBulletsArray.add(new Bullet(x, y, 2, 1, endPointForMovingTank, gl, 0.5f));//new GLBall(x, y, 1, 1);
				cooldown = 0;
			}
		}
		float shiftCooldown = 0.03f;
		cooldown += shiftCooldown;
	}
	
	private void updateRotation(GL2 gl) {
		
		if(InputHandler.sKeyPressedA) {
			tankStartAngle -=speedRotation;
			angleForMoveTank -= speedRotation;
		}
		else if(InputHandler.sKeyPressedD) {
			tankStartAngle += speedRotation;	
			angleForMoveTank += speedRotation;
			
		}
		resetAngle();		
	}
	
	private void resetAngle() {
		if(tankStartAngle > 360) {
			tankStartAngle = 0;
		}		
		else if(tankStartAngle < 0) {
			tankStartAngle = 360;
		}		
		if(angleForMoveTank > 360) {
			angleForMoveTank = 0;
		}
		else if(angleForMoveTank < -270) {			
			angleForMoveTank = 90.1f;
		}
	}
	//поднять "y"  у баунда
	private void updatePosition(GL2 gl) {
		oldPosition.x = this.x;
		oldPosition.y = this.y;
		
		double sizeSkybox = WorldConsts.SIZE_SKYBOX.getValue();
		double triangleSide = sizeSkybox 
				* getSinInDegrees(angleForMoveTank)
				/ getSinInDegrees(180 - 90 - angleForMoveTank);	// 180 - 90 - angleForMoveTank == gama(Y) angle	
		if( (angleForMoveTank >= -90 && angleForMoveTank <= 0) 
				|| (angleForMoveTank <= 90 && angleForMoveTank > 0)) { // 1 и 2 ая четверть
			endPointForMovingTank = new Vector2d(triangleSide, sizeSkybox);
		}
		else {//когда нижняя координата переводим в верхнюю и меняем координаты
			if(angleForMoveTank < 90) {
				endPointForMovingTank = new Vector2d(-triangleSide, -sizeSkybox);				
			}
			else {
				triangleSide = sizeSkybox
						* getSinInDegrees(angleForMoveTank - 90) 
						/ getSinInDegrees(180 - 90 - angleForMoveTank - 90);//180 - 90 - angleForMoveTank - 90 =  gama(Y) смещенная для 4ой четверти
				if(angleForMoveTank > 180) {
					
					endPointForMovingTank = new Vector2d(-sizeSkybox, -triangleSide);
				}
				else {					
					endPointForMovingTank = new Vector2d(sizeSkybox, triangleSide);
				}
			}
		}
		
		double mDistance = Math.sqrt((endPointForMovingTank.x - x)*(endPointForMovingTank.x - x) + (endPointForMovingTank.y - y) * (endPointForMovingTank.y - y)); //считаем дистанцию (длину от точки А до точки Б). формула длины вектора		
		if(InputHandler.sKeyPressedW && !InputHandler.sKeyPressedD && !InputHandler.sKeyPressedA) {
			x += speed * (endPointForMovingTank.x - x) / mDistance;
			y += speed * (endPointForMovingTank.y - y) / mDistance;	
			sShiftX = x;
			sShiftY = y;
		}
	}
}
