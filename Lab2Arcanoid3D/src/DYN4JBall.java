import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class DYN4JBall extends Body {
	private GLBall mBall = new GLBall();
	private int isHit = 0;
	public DYN4JBall(Vector2 velocity) {
		this.velocity = velocity;
		createBall(100, 0.20, new  Vector2(0, 0), this.velocity);
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
	
	private void checkCollisionWithBlock() {
		for(int i = RangesConst.RANGE_BEGIN_FOR_BLOCKS.getValue(); i < DialDisplay.sWorld.getBodyCount(); i++) {
			if(isHit != 5) {
				if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
					this.velocity.y = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					DialDisplay.sWorld.removeBody((i));
					isHit = 5;
					break;
				}
			}
		}
	}
	
	private void checkCollisionWithMovingPlatform() {
		int movingPlatformPossition = 0;
		if(mBall.isInContact(DialDisplay.sWorld.getBody(movingPlatformPossition))) {
			if(this.velocity.x > 0) {
				if(isHit != 1) {
					this.velocity.y = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					isHit = 1;
				}
			}
			else {
				if(isHit != 2) {
					this.velocity.y = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					isHit = 2;
				}
			}
		}
	}
	
	private void checkCollisionWithMovingBox() {
		for(int i = RangesConst.RANGE_BEGIN_FOR_BOX.getValue(); i <  RangesConst.RANGE_END_FOR_BOX.getValue(); i++) {
			if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
					if(i % 2 == 0) {
						if(isHit != 3) {
							this.velocity.y = this.velocity.y * -1;
							mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
							isHit = 3;
						}
					}
					else {
						if(isHit != 4) {
							this.velocity.x = this.velocity.x * -1;
							mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
							isHit = 4;
						}
				}
			}
		}
	}
	
	public void update(GL2 gl) {
		int ballID = (int) WorldConsts.POSSITION_BALL.getValue();
		mBall = (GLBall) DialDisplay.sWorld.getBody(ballID);
		mBall.render(gl);		
		checkCollisionWithMovingPlatform();
		checkCollisionWithMovingBox();
		checkCollisionWithBlock();			
	}
}