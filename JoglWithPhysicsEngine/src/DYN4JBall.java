import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL2;


public class DYN4JBall extends Body {
	private GLObject ball = new GLObject();
	private Vector2 velocity = new Vector2();
	public DYN4JBall(Vector2 velocity) {
		this.velocity = velocity;
		//System.out.println(DYN4JCannon.mouseX);
		createBall(100, 0.28, new  Vector2(0, 0), this.velocity);
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
	
	public void update(GL2 gl) {
		for (double ballID = Const.RANGE_END_FOR_CANNON.getValue(); ballID < Renderer.world.getBodyCount(); ballID++) {
			GLObject glObjects = (GLObject) Renderer.world.getBody((int) ballID);
			glObjects.render(gl);
			for (double platformID = Const.RANGE_BEGIN_FOR_BASE_PLATFORM.getValue(); platformID < Const.RANGE_END_FOR_CANNON.getValue(); platformID++) {
				if(Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody((int) platformID))) {
					Renderer.world.getBody((int) ballID).applyImpulse(0.05f);
				}
				if (Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody((int) Const.ID_FLOOR.getValue()))) {
					Renderer.world.removeBody(((int) ballID));
					if (Const.RANGE_END_FOR_CANNON.getValue() == Const.RANGE_END_FOR_CANNON.getValue())
					{
						break;
					}
				}
			}
		}
	}
	
}
