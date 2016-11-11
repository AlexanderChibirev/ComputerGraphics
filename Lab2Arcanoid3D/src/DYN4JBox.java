import java.util.Vector;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class DYN4JBox {
	private Vector<GLBox> mBox = new Vector<GLBox>();
	
	public DYN4JBox() {
		createBox();
	}
	private void createRect(final Vector2 size, final double angle, Vector2 translate)
	{
		GLBox rect = new GLBox();
		rect.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		rect.setMass(MassType.INFINITE);
		rect.rotate(angle);
		rect.translate(translate.x, translate.y);
		mBox.add(rect);
	}
	
	private  void createBox() {
		createRect(new Vector2(13.2, .5), 1.57, new Vector2(8.6, 0));//right
		createRect(new Vector2(17.0, .5), 0, new Vector2(0, 6.4)); //up
		createRect(new Vector2(13.2, .5), -1.57, new Vector2(-8.6, 0));//left
		createRect(new Vector2(17.0, .5), 0, new Vector2(0, -6.4)); //floor for died ball
	}
	
	public Vector<GLBox> getBox() {
		return mBox;
	}
}
