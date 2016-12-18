import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TransformationController  extends KeyAdapter {
	
	public static boolean sKeyPressedPlus = false;
	public static boolean sKeyPressedMinus = false;	
	
	private float mCurrentTwistValue = 1;
	private final float MIN_TWIST = -2.f;
	private final float MAX_TWIST = 2.f;
	private final float NEXT_TWIST_STEP = 1f;
	
	@Override
	public void keyPressed(KeyEvent e) {
	  if (e.getKeyCode() == KeyEvent.VK_1) {
		  sKeyPressedPlus = true;
		//  if(mCurrentTwistValue > MIN_TWIST) {
			  mCurrentTwistValue -= NEXT_TWIST_STEP;
		//  }
		 // else
		 // {
			//  mCurrentTwistValue =  MIN_TWIST;
		 // } 
		  
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_2) {
		  sKeyPressedMinus = true;
		  mCurrentTwistValue += NEXT_TWIST_STEP;
		  System.out.println(mCurrentTwistValue);
		 
		//  if(mCurrentTwistValue < MAX_TWIST) {
			  mCurrentTwistValue += NEXT_TWIST_STEP;
		//  }
		//  else
		//  {
		//	  mCurrentTwistValue = MAX_TWIST;
		//  }
	}	
	  System.out.println(mCurrentTwistValue);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		sKeyPressedPlus = false;
		sKeyPressedMinus = false;
	}

	public float getCurrentValue() {
		return mCurrentTwistValue;
	}
}
