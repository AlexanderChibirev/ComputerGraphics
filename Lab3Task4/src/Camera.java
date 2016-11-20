
import javax.vecmath.Vector2f;

public class Camera { 
	private float mRateOfChanges = 0.01f;
	private float mRateOfChangeIterations = 1;
	private Vector2f mSize = new Vector2f(5,5);
	private Vector2f mPos = new Vector2f(-2,-2);
	private int mIterations = 64;
	
	public void update(ShaderManager m) {
		if(InputHandler.sKeyPressedUP) {
			mPos.y -= mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedDOWN) {
			mPos.y += mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedRight) {
			mPos.x += mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedLeft) {
			mPos.x -= mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedW) {
			mSize.x -= mRateOfChanges;
			mSize.y -= mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedS) {
			mSize.x += mRateOfChanges;
			mSize.y += mRateOfChanges;
		}
		else if(InputHandler.sKeyPressedQ) {
			mIterations += mRateOfChangeIterations;
		}
		else if(InputHandler.sKeyPressedE) {
			mIterations -= mRateOfChangeIterations;
		}
		m.interpolate(mSize, mPos, mIterations);
	}	
	
}
