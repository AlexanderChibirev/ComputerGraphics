import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;


public class DYN4JBall extends Body {
	private GLObject ball = new GLObject();
	private Vector2 velocity = new Vector2();
	public DYN4JBall(Vector2 velocity) {
		this.velocity = velocity;
		createBall(100, 0.28, new  Vector2(0,3), this.velocity);
	}
	
	private void createBall(final int precision, final double radius, Vector2 pos, Vector2 velocity)
	{
		ball.addFixture(Geometry.createUnitCirclePolygon(precision, radius));
		ball.setMass(MassType.NORMAL);
		ball.setLinearVelocity(velocity.x, velocity.y);
		ball.translate(pos.x,pos.y);
	}
	
	public GLObject getBall() {
		return ball;
	}
}
