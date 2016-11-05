import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;

public class DYN4JBall extends Body {
	private GLBall ball = new GLBall();
	private Vector2 velocity = new Vector2();
	public DYN4JBall(Vector2 velocity) {
		this.velocity = velocity;
		createBall(100, 0.28, new  Vector2(0, 0), this.velocity);
	}
	
	private void createBall(final int precision, final double radius, Vector2 pos, Vector2 velocity)
	{
		ball.addFixture(Geometry.createUnitCirclePolygon(precision, radius));
		ball.setMass(MassType.NORMAL);
		ball.setLinearVelocity(velocity.x, velocity.y);
		ball.translate(pos.x,pos.y);
		ball.setAngularDamping(12);
		ball.applyImpulse(101);
	}
	
	public GLBall getBall() {
		return ball;
	}
	
	public void update(GL2 gl) {
		for (double ballID = RangesConst.RANGE_BEGIN_FOR_BALL.getValue(); ballID < DialDisplay.sWorld.getBodyCount(); ballID++) {
			GLBall glObjects = (GLBall) DialDisplay.sWorld.getBody((int) ballID);
			glObjects.render(gl);
		}
	}
}