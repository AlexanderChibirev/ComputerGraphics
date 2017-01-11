import javax.vecmath.Vector2d;

import com.jogamp.opengl.GL2;
import OBJLoader.OBJModel;

public class Enemy extends BodyBound {
	
	private static final float MAX_SIZE = 6.f;  // for a model's dimension
	private static final int SIZE_WIDTH = 6;  
	private static final int SIZE_HEIGHT = 6; 
	private static final int MAX_COOLDOWN_WAIT_TIME = 8; 
	private float cooldown = 0;	
	private float angle;
	private OBJModel tank;
	private float speed = 0.01f;
	private Direction direction;
	private int endCoordForBullet = 1000;
	private Vector2d pointForEndCoordBullet = new Vector2d(0, 1000);
	
	public Enemy(float x, float y, Direction direction, GL2 gl) {
		super(x, y, SIZE_WIDTH, SIZE_HEIGHT, 0);
		this.direction = direction;
		setDirectionForCollision();
		this.tank = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
	}

	public OBJModel getTank() {
		return tank;
	}

	public void update(GL2 gl) {
		float shiftForY = 1.8f;
		gl.glTranslated(this.x, shiftForY, this.y);
		gl.glRotated(angle, 0, 1, 0);
		tank.draw(gl);
		gl.glRotated(-angle, 0, 1, 0);
		gl.glTranslated(-this.x, -shiftForY,  -this.y);
		
		if(cooldown > MAX_COOLDOWN_WAIT_TIME) {
			float shiftForBullet = 7;
			float shiftY = 1.5f;
			if(direction == Direction.UP)
				Bullet.sBulletsArray.add(new Bullet(x, y + shiftForBullet, 2, 1, pointForEndCoordBullet, gl, shiftY));
			else if(direction == Direction.DOWN)
				Bullet.sBulletsArray.add(new Bullet(x, y - shiftForBullet, 2, 1, pointForEndCoordBullet, gl, shiftY));
			else if(direction == Direction.RIGHT)
				Bullet.sBulletsArray.add(new Bullet(x - shiftForBullet, y, 2, 1, pointForEndCoordBullet, gl, shiftY));
			else if(direction == Direction.LEFT)
				Bullet.sBulletsArray.add(new Bullet(x + shiftForBullet, y, 2, 1, pointForEndCoordBullet, gl, shiftY));			
			cooldown = 0;
		}
		
		float shiftCooldown = 0.01f;
		cooldown += shiftCooldown;
		
		switch (direction) {
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
		
		for(int j = 0; j < Entity.sBlockUndestroyables.size(); j++) {
			if(this.getBounds().intersects(Entity.sBlockUndestroyables.get(j).getBounds())){
				setDirectionForCollision();
				break;
			}
		}
		
		for(int j = 0; j < Entity.sBlockDestroyables.size(); j++) {
			if(this.getBounds().intersects(Entity.sBlockDestroyables.get(j).getBounds())){
				setDirectionForCollision();
				break;
			}
		}		
	}
	
	private void setDirectionForCollision() {
		if(direction == Direction.UP) {
			angle = 0;
			direction = Direction.DOWN;
		}			
		else if(direction == Direction.DOWN) {
			angle = 180;
			direction = Direction.UP;
		}
		else if(direction == Direction.LEFT) {
			angle = 90;
			direction = Direction.RIGHT;
		}
		else if(direction == Direction.RIGHT) {
			angle = 270;
			direction = Direction.LEFT;
		}
	}
}
