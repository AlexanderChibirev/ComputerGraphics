import java.util.Vector;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;


public class DYN4JMovingPlatform {
	private GLMovingPlatform basePlatform = new GLMovingPlatform();
	private float x = 0;
	private boolean stepL = true;
	private boolean stepR = true;
	
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
		final float shiftPlatform = 0.20f;
		final float leftPartBorder = -6.6f;
		final float rightPartBorder = 7.3f;
		float dx = 0;
		if(InputHandler.sKeyPressedA == true && stepL) {
			dx -= shiftPlatform;
			x += dx;
			System.out.println(x);
			stepR = true;
		}
		else if(InputHandler.sKeyPressedD == true && stepR) {
			dx += shiftPlatform;
			x += dx;
			System.out.println(x);
			stepL = true;
		}
		
		if(x > rightPartBorder) {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(-1 * dx, 0);
			x += dx;
			stepR = false;
		}
		else if(x < leftPartBorder) {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(-1 * dx, 0);
			x += dx;
			stepL = false;
		}
		else {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(dx, 0);
		}
	}
}
