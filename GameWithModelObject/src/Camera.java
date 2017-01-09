

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float mAngleX;
	private float mAngleY;
	private float mShiftAngleY = 0.03f;
	private float mShiftAngleX = 0.03f;
	private float mShifRZ = 0.001f;
	private float mRZ = -5;
	
	private float mStepZ = 0;
	private float mStepX = 0;
	private float mShiftUp = 0.0002f;
	
	public void update(GLU glu, GL2 gl) {
		if(InputHandler.sKeyPressedUP) {
			mAngleY -= mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedDOWN) {
			mAngleY += mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedE) {			mRZ -= mShifRZ;
		}
		else if(InputHandler.sKeyPressedQ) {			mRZ += mShifRZ;
		}
		else if(InputHandler.sKeyPressedRight) {
			mAngleX -= mShiftAngleX;
		}
		else if(InputHandler.sKeyPressedW) {
			
			if(Player.direction == Direction.RIGHT){
				mStepX += mShiftUp;
			}
			else if(Player.direction == Direction.LEFT){
				mStepX -= mShiftUp;
			}
			else if(Player.direction == Direction.UP){
				mStepZ -= mShiftUp;
			}
			else if(Player.direction == Direction.DOWN){
				mStepZ += mShiftUp;
			}
		}
		else if(InputHandler.sKeyPressedLeft) {
			mAngleX += mShiftAngleX;
		}
		
		if (mAngleY < -89.0){mAngleY= -89.0f;}
		if (mAngleY > 89.0){mAngleY= 89.0f;}
	
		glu.gluLookAt(mStepX, 0, mStepZ, mStepX - Math.sin(mAngleX / 180 * Math.PI), 0 + (Math.tan(mAngleY / 180 * Math.PI)), mStepZ-Math.cos(mAngleX / 180 * Math.PI), 0, 1, 0);
		gl.glOrtho(-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
	}	
}
