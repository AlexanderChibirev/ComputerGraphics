import java.util.Vector;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;


public class DYN4JMovingPlatform {
	private GLMovingPlatform basePlatform = new GLMovingPlatform();
	
	public DYN4JMovingPlatform() {
		createMovingPlatform();
	}
	private void createRect(final Vector2 size, final double angle, Vector2 translate)
	{
		GLMovingPlatform rect = new GLMovingPlatform();
		rect.addFixture(new BodyFixture(new Rectangle(size.x, size.y)));
		rect.setMass(MassType.INFINITE);
		rect.rotate(angle);
		rect.translate(translate.x, translate.y);
		basePlatform = rect;
	}
	
	private  void createMovingPlatform() {
		createRect(new Vector2(3, 0.4), 0, new Vector2(0, -5));//left floor 0
	}
	
	public GLMovingPlatform getMovingPlatform() {
		return basePlatform;
	}
	
	public void updatePossitionMovingPlatform() {
		float dx = 0;
		if(InputHandler.sKeyPressedA == true) {
			dx -= 0.20f;
		}
		else if(InputHandler.sKeyPressedD == true) {
			dx += 0.20f;
		}
		DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(dx, 0);
	}
}
