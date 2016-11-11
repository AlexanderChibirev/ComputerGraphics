import java.util.Vector;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class DYN4JBlock extends Body {
private Vector<GLBlock> mBox = new Vector<GLBlock>();
	private Vector2 mPositionBlock = new Vector2(-7.0f, -0.3f);
	public DYN4JBlock() {
		createBlocks();
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
	
	private void createNewColumnBlocks() {
		float shiftY = 1.1f;
		float shiftX = 2.00f;
		float startPositionX = -7.0f;
		for(int i = 0; i < 8; ++i) {
			createRect(new Vector2(2, 1), 0, new Vector2(mPositionBlock.x, mPositionBlock.y));
			mPositionBlock.x += shiftX;
		}
		mPositionBlock.y += shiftY;
		mPositionBlock.x = startPositionX;
	}
	
	private  void createBlocks() {
		createNewColumnBlocks();
		createNewColumnBlocks();
		createNewColumnBlocks();
	}
	
	public Vector<GLBlock> getBlock() {
		return mBox;
	}
}
