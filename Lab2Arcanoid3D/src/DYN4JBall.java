import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

enum TypeHit {
	NORMAL_BOX,
	INVERTE_BOX,
	MOVING_PLATFORM,
	BLOCK
}

public class DYN4JBall extends Body {
	private GLBall mBall = new GLBall();
	private TypeHit mTypeHit;
	private int mQuantityOfDestroyedBlocks = 0;
	private boolean mIsDead = false;
	
	public DYN4JBall(Vector2 velocity) {
		this.velocity = velocity;
		createBall(100, 0.20, new  Vector2(0, -4), this.velocity);
	}
	
	private void createBall(final int precision, final double radius, Vector2 pos, Vector2 velocity)
	{
		mBall.addFixture(Geometry.createUnitCirclePolygon(precision, radius));
		mBall.setMass(MassType.NORMAL);
		mBall.setLinearVelocity(velocity.x, velocity.y);
		mBall.translate(pos.x,pos.y);
	}
	
	public GLBall getBall() {
		return mBall;
	}
	public int getQuantityOfDestroyedBlocks() {
		return mQuantityOfDestroyedBlocks;		
	}
	
	
	private void checkCollisionWithBlock(GL2 gl) {
		for(int i = RangesConst.RANGE_BEGIN_FOR_BLOCKS.getValue(); i < DialDisplay.sWorld.getBodyCount(); i++) {
			if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
				Vector2 posCenterBlock = DialDisplay.sWorld.getBody(i).getWorldCenter();
				Vector2 posCenterBall = mBall.getWorldCenter();
				float limitValue = 0.9f;
				if(Math.abs(posCenterBall.x - posCenterBlock.x) > limitValue) {
					this.velocity.x *= -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					mTypeHit = TypeHit.BLOCK;
				}
				else if(Math.abs(posCenterBall.y - posCenterBlock.y) > limitValue) {
					this.velocity.y *= -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					mTypeHit = TypeHit.BLOCK;
				}
				DialDisplay.sWorld.removeBody((i));
				mQuantityOfDestroyedBlocks++;
				break;
			}
		}
	}
	
	private void checkCollisionWithMovingPlatform() {
		int movingPlatformPossition = 0;
		if(mBall.isInContact(DialDisplay.sWorld.getBody(movingPlatformPossition))) {
			if(mTypeHit != TypeHit.MOVING_PLATFORM) {
				this.velocity.y *= -1;
				mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
				mTypeHit = TypeHit.MOVING_PLATFORM;
			}
		}
	}
	
	private void checkCollisionWithMovingBox() {
		int posDownBox = 4;
		for(int i = RangesConst.RANGE_BEGIN_FOR_BOX.getValue(); i <  RangesConst.RANGE_END_FOR_BOX.getValue(); i++) {
			if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
					if(i % 2 == 0) {
						if(i == posDownBox) {
							mIsDead = true;
						}
						if(mTypeHit != TypeHit.NORMAL_BOX) {
							this.velocity.y *= -1;
							mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
							mTypeHit = TypeHit.NORMAL_BOX;
						}
					}
					else {
						
						if(mTypeHit !=  TypeHit.INVERTE_BOX) {
							this.velocity.x *= -1;
							mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
							mTypeHit =  TypeHit.INVERTE_BOX;
						}
				}
			}
		}
	}

	public boolean isDead() {
		return mIsDead;
	}
	
	public void update(GL2 gl, GLU glu) {
		int ballID = (int) WorldConsts.POSSITION_BALL.getValue();
		mBall = (GLBall) DialDisplay.sWorld.getBody(ballID);
		mBall.render(gl, glu);	
		checkCollisionWithMovingPlatform();
		checkCollisionWithMovingBox();
		checkCollisionWithBlock(gl);
	}
}