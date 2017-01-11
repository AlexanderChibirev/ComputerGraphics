import javax.vecmath.Vector2d;

import com.jogamp.opengl.GL2;
import OBJLoader.OBJModel;

public class Enemy extends BodyBound {
	
	private static final float MAX_SIZE = 6.f;  // for a model's dimension
	private static final int SIZE_WIDTH = 6;  // for a model's dimension
	private static final int SIZE_HEIGHT = 6;  // for a model's dimension
	private static final int MAX_COOLDOWN_WAIT_TIME = 8;  // for a model's dimension
	private float cooldown = 0;	
	private float mAngle;
	private OBJModel mTank;
	private float speed = 0.01f;
	private Direction mDirection;
	private int endCoordForBullet = 1000;
	private Vector2d pointForEndCoordBullet = new Vector2d(0, 1000);
	
	public Enemy(float x, float y, Direction direction, GL2 gl) {
		super(x, y, SIZE_WIDTH, SIZE_HEIGHT, 0);
		mDirection = direction;
		setDirectionForCollision();
		mTank = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
	}

	public OBJModel getTank() {
		return mTank;
	}

	public void update(GL2 gl) {
		gl.glTranslated(this.x, 1.8f, this.y);
		gl.glRotated(mAngle, 0, 1, 0);
		mTank.draw(gl);
		gl.glRotated(-mAngle, 0, 1, 0);
		gl.glTranslated(-this.x, -1.8f,  -this.y);
		
		if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
			if(mDirection == Direction.UP)
				Bullet.sBulletsArray.add(new Bullet(x, y + 7, 2, 1, pointForEndCoordBullet, gl, 1.5f));
			else if(mDirection == Direction.DOWN)
				Bullet.sBulletsArray.add(new Bullet(x, y - 7, 2, 1, pointForEndCoordBullet, gl, 1.5f));
			else if(mDirection == Direction.RIGHT)
				Bullet.sBulletsArray.add(new Bullet(x - 7, y, 2, 1, pointForEndCoordBullet, gl, 1.5f));
			else if(mDirection == Direction.LEFT)
				Bullet.sBulletsArray.add(new Bullet(x + 7, y, 2, 1, pointForEndCoordBullet, gl, 1.5f));			
			cooldown = 0;
		}
		
		float shiftCooldown = 0.01f;
		cooldown += shiftCooldown;
		
		switch (mDirection) {
		case UP:
			this.y += speed;
			pointForEndCoordBullet.x = 0;
			pointForEndCoordBullet.y = endCoordForBullet;
			break;
		case DOWN:
			pointForEndCoordBullet.x = 0;
			pointForEndCoordBullet.y = -endCoordForBullet;
			this.y -= speed;
			break;
		case LEFT:
			this.x += speed;
			pointForEndCoordBullet.x = endCoordForBullet;
			pointForEndCoordBullet.y = 0;
			break;
		case RIGHT:		
			this.x -= speed;
			pointForEndCoordBullet.x = -endCoordForBullet;
			pointForEndCoordBullet.y = 0;
			break;
		}
		
		for(int j = 0; j < BlockUndestroyable.sBlockUndestroyables.size(); j++) {
			if(this.getBounds().intersects(BlockUndestroyable.sBlockUndestroyables.get(j).getBounds())){
				setDirectionForCollision();
				break;
			}
		}
		
		for(int j = 0; j < BlockDestroyable.sBlockDestroyables.size(); j++) {
			if(this.getBounds().intersects(BlockDestroyable.sBlockDestroyables.get(j).getBounds())){
				setDirectionForCollision();
				break;
			}
		}		
		
	}
	
	private void setDirectionForCollision() {
		if(mDirection == Direction.UP) {
			mAngle = 0;
			mDirection = Direction.DOWN;
		}			
		else if(mDirection == Direction.DOWN) {
			mAngle = 180;
			mDirection = Direction.UP;
		}
		else if(mDirection == Direction.LEFT) {
			mAngle = 90;
			mDirection = Direction.RIGHT;
		}
		else if(mDirection == Direction.RIGHT) {
			mAngle = 270;
			mDirection = Direction.LEFT;
		}
	}
}
