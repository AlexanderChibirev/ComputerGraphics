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
		//ball.setAngularDamping(15f);
		ball.applyImpulse(155f);
	}
	
	public GLBall getBall() {
		return ball;
	}
	
	private Vector2 normalazeVector(Vector2 v1) {
		return new Vector2(	
				v1.x / v1.normalize(),
				v1.y / v1.normalize());
	}
	
	private Vector2 mul(Vector2 v1, Vector2 v2) {
		return new Vector2(	
				v1.x / v2.x,
				v1.y / v2.y);
	}
	
	public void update(GL2 gl) {
		int ballID = (int) WorldConsts.POSSITION_BALL.getValue();
		GLBall ball = (GLBall) DialDisplay.sWorld.getBody(ballID);
		//Vector2 mirrorReflection = null;
		//Vector2 d = null;
		ball.render(gl);
		ball.applyImpulse(15);
		for(int i = RangesConst.RANGE_BEGIN_FOR_MOVING_PLATFORM.getValue();
				i < RangesConst.RANGE_END_FOR_BOX.getValue();
				++i) {
			
			if(ball.isInContact(DialDisplay.sWorld.getBody(i))) {
				
				/*//mirrorReflection = new Vector2(this.velocity.x , this.velocity.y);
				d = new Vector2(this.velocity.x , this.velocity.y);
				System.out.println(d);
				Vector2 n = normalazeVector(d);
				System.out.println(n);
				Vector2 dn = mul(d,n);
				System.out.println(dn);
				dn.multiply(2);
				System.out.println(dn);
				mirrorReflection = mul(dn,n);
				mirrorReflection.multiply(2);
				Vector2 vectorD = d.multiply(2);
				System.out.println(vectorD);
				Vector2 vectorD2 = vectorD.multiply(n);
				System.out.println(vectorD2);
				//mirrorReflection = d.subtract(vectorD2);
				//System.out.println(vectorD2);
				this.velocity =  mirrorReflection;
				ball.setLinearVelocity(mirrorReflection);*/
			}
		}
		
		
		
	}
}