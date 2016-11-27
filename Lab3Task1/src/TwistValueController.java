import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TwistValueController  extends KeyAdapter {
	
	public static boolean sKeyPressedPlus = false;
	public static boolean sKeyPressedMinus = false;	
	
	private float mCurrentTwistValue = 0;
	private float mNextTwistValue = 0;
	
	private final float MIN_TWIST = -2.f;
	private final float MAX_TWIST = 2.f;
	private final float NEXT_TWIST_STEP = 0.2f;
	private final float TWIST_CHANGE_SPEED = 1.f;
	
	@Override
	public void keyPressed(KeyEvent e) {
	  if (e.getKeyCode() == KeyEvent.VK_PLUS) {
		  sKeyPressedPlus = true;
		  mNextTwistValue = Math.min(mNextTwistValue + NEXT_TWIST_STEP, MAX_TWIST);
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
		  sKeyPressedMinus = true;
		  mNextTwistValue = Math.min(mNextTwistValue - NEXT_TWIST_STEP, MIN_TWIST);
	  }	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		sKeyPressedPlus = false;
		sKeyPressedMinus = false;
	}
	
	
	public void update(float deltaSeconds) {
		 final float twistDiff = Math.abs(mNextTwistValue - mCurrentTwistValue);
		 //double epsilon =  Math.pow(2, -52);
		 if (twistDiff > Math.ulp(1.0)) {
			 	final float sign = (mNextTwistValue > mCurrentTwistValue) ? 1.f : -1.f;
			 	final float growth = deltaSeconds * TWIST_CHANGE_SPEED;
		        if (growth > twistDiff) {
		        	mCurrentTwistValue = mNextTwistValue;
		        }
		        else {
		        	mCurrentTwistValue += sign * growth;
		        }
		    }
	}
	public float getCurrentValue() {
		return mCurrentTwistValue;
	}
}
