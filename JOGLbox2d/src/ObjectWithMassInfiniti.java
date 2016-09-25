import java.util.Vector;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class ObjectWithMassInfiniti {
	private Vector<GLObject> basePlatform = new Vector<GLObject>();
	
	public ObjectWithMassInfiniti() {
		createBasePlatform();
	}
	
	private void createRect(final Vector2 rect, final double angle, Vector2 translate)
	{
		GLObject glFloorLeft = new GLObject();
		glFloorLeft.addFixture(new BodyFixture(new Rectangle(rect.x, rect.y)));
		glFloorLeft.setMass(MassType.INFINITE);
		glFloorLeft.rotate(angle);
		glFloorLeft.translate(translate.x, translate.y);
		basePlatform.add(glFloorLeft);
	}
	private  void createBasePlatform() {
		createRect(new Vector2(10, .4), -.2, new Vector2(-4.5, -4));//left floor
		createRect(new Vector2(9, .4), .4, new Vector2(4.5, -1.0));//right floor
		createRect(new Vector2(15.0, .5), 1.58, new Vector2(-8.8, .2));//left wall
		createRect(new Vector2(15.0, .5), 1.58, new Vector2(8.8, .2));//right wall
		createRect(new Vector2(18, .5), 0, new Vector2(0, 7.5));// ceiling 
		createRect(new Vector2(3,1.5), 0.6, new Vector2(-2,-1));//boxes
		createRect(new Vector2(2,1.5), -0.6, new Vector2(-5, 3));
		createRect(new Vector2(2.5,1.5), 0, new Vector2(5,3));
	}
	
	public Vector<GLObject> getBasePlatform() {
		return basePlatform;
	}
}
