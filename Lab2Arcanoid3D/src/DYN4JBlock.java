import java.util.Vector;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class DYN4JBlock extends Body {
private Vector<GLBlock> mBox = new Vector<GLBlock>();
	
	public DYN4JBlock() {
		createBlock();
	}
	
	private void createRect(final Vector2 size, final double angle, Vector2 translate)
	{
		GLBlock rect = new GLBlock();
		rect.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		rect.setMass(MassType.INFINITE);
		rect.rotate(angle);
		rect.translate(translate.x, translate.y);
		mBox.add(rect);
	}
	
	private  void createBlock() {
		float x = -3.95f;
		float y = -0.3f;
		float shiftX = 0.70f;
		for(int i = 0; i < 13; ++i) {
			createRect(new Vector2(0.5, 0.5), 0, new Vector2(x, y));
			x += shiftX;
		}
		//createRect(new Vector2(0.6, 0.6), 0, new Vector2(x, y));//6
		//createRect(new Vector2(0.6, 0.6), 0, new Vector2(x + shiftX, y));//7
		//createRect(new Vector2(0.6, 0.6), 0, new Vector2(-3.25, -0.3));//7
	}
	
	public Vector<GLBlock> getBlock() {
		return mBox;
	}
}
