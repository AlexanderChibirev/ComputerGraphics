import java.util.Vector;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class DYN4JBasePlatform {
	private Vector<GLBasePlatform> basePlatform = new Vector<GLBasePlatform>();
	
	public DYN4JBasePlatform() {
		createBasePlatform();
	}
	private void createRect(final Vector2 size, final double angle, Vector2 translate)
	{
		GLBasePlatform rect = new GLBasePlatform();
		rect.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		rect.setMass(MassType.INFINITE);
		rect.rotate(angle);
		rect.translate(translate.x, translate.y);
		basePlatform.add(rect);
	}
	
	private  void createBasePlatform() {
		createRect(new Vector2(10, .4), -.2, new Vector2(-4.5, -4));//left floor 0
		createRect(new Vector2(9, .4), .4, new Vector2(4.5, -1.0));//right floor 1
		createRect(new Vector2(15.0, .5), 1.58, new Vector2(-8.8, .2));//left wall 2
		createRect(new Vector2(15.0, .5), 1.58, new Vector2(8.8, .2));//right wall 3
		createRect(new Vector2(18, .5), 0, new Vector2(0, 7.5));// ceiling  4
		createRect(new Vector2(3,1.5), 0.6, new Vector2(-4,-0.5));//boxes 5
		createRect(new Vector2(2,1.5), -0.6, new Vector2(-5, 5));//6
		createRect(new Vector2(2.5,1.5), 0, new Vector2(6,3)); //7
	}
	
	public Vector<GLBasePlatform> getBasePlatform() {
		return basePlatform;
	}
}
