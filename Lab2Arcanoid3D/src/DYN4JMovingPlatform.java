import javax.vecmath.Vector2f;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;


public class DYN4JMovingPlatform {
	private GLMovingPlatform mBasePlatform = new GLMovingPlatform();
	private Vector2f mMovingPlatformPossition = new Vector2f(0,0);
	private boolean mStepL = true;
	private boolean mStepR = true;
	
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
		mBasePlatform = rect;
	}
	
	private  void createMovingPlatform() {
		createRect(new Vector2(3, 0.5), 0, new Vector2(0, -5));//left floor 0
	}
	
	public GLMovingPlatform getMovingPlatform() {
		return mBasePlatform;
	}
	
	public void updatePossitionMovingPlatform() {
		final float shiftPlatform = 0.010f;
		final float leftPartBorder = -6.8f;
		final float rightPartBorder = 6.8f;
		float dx = 0;
		if(InputHandler.sKeyPressedA == true && mStepL) {
			dx -= shiftPlatform;
			mMovingPlatformPossition.x += dx;
			mStepR = true;
		}
		else if(InputHandler.sKeyPressedD == true && mStepR) {
			dx += shiftPlatform;
			mMovingPlatformPossition.x += dx;
			mStepL = true;
		}
		
		if(mMovingPlatformPossition.x > rightPartBorder) {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(-1 * dx, 0);
			mMovingPlatformPossition.x += dx;
			mStepR = false;
		}
		else if(mMovingPlatformPossition.x < leftPartBorder) {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(-1 * dx, 0);
			mMovingPlatformPossition.x += dx;
			mStepL = false;
		}
		else {
			DialDisplay.sWorld.getBody((int) WorldConsts.POSSITION_MOVING_PLATFORM.getValue()).translate(dx, 0);
		}
	}
}
