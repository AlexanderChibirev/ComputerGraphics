import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class DYN4JBall extends Body {
	private GLBall mBall = new GLBall();
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
	
	public void update(GL2 gl) {
		int ballID = (int) WorldConsts.POSSITION_BALL.getValue();
		mBall = (GLBall) DialDisplay.sWorld.getBody(ballID);
		mBall.render(gl);
		//System.out.println(ball.getLinearVelocity());
		int movingPlatformPossition = 0;
		if(mBall.isInContact(DialDisplay.sWorld.getBody(movingPlatformPossition))) {
		
				if(this.velocity.x > 0) {
					this.velocity.y = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
				}
				else {
					this.velocity.x = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
				}
		}
		for(int i = RangesConst.RANGE_BEGIN_FOR_BOX.getValue(); i <  RangesConst.RANGE_END_FOR_BOX.getValue(); i++) {
			if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
				if(i % 2 == 0) {
					this.velocity.y = this.velocity.y * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					break;
				}
				else {
					this.velocity.x = this.velocity.x * -1;
					mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
					break;
				}
			}
		}
		for(int i = RangesConst.RANGE_BEGIN_FOR_BLOCKS.getValue(); i < DialDisplay.sWorld.getBodyCount(); i++) {
			if(mBall.isInContact(DialDisplay.sWorld.getBody(i))) {
				this.velocity.y = this.velocity.y * -1;
				mBall.setLinearVelocity(this.velocity.x,this.velocity.y);
				DialDisplay.sWorld.removeBody((i));
				break;
			}
		}
			
	}
}