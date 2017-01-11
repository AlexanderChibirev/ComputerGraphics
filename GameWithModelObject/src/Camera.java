

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float mAngleX;
	private float mAngleY;
	private float mShiftAngleY = 0.3f;
	private float mShiftAngleX = 0.3f;
	private float mShifRZ = 0.01f;
	private float mRZ = -8;
	
	private float mStepZ = 0;
	private float mStepX = 0;
	private float mSpeed = 0.5f;
	
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
		else if(InputHandler.sKeyPressedD) {
			mAngleX -= mShiftAngleX;
		}		
		else if(InputHandler.sKeyPressedA) {
			mAngleX += mShiftAngleX;
		}		
		else if(InputHandler.sKeyPressedW) {			
			mStepX = Player.sShiftX * mSpeed;
			mStepZ = -Player.sShiftY * mSpeed;
		}
		
		if (mAngleY < -89.0){mAngleY= -89.0f;}
		if (mAngleY > 89.0){mAngleY= 89.0f;}
	
		glu.gluLookAt(mStepX, 0, mStepZ, mStepX - Math.sin(mAngleX / 180 * Math.PI), 0 + (Math.tan(mAngleY / 180 * Math.PI)), mStepZ-Math.cos(mAngleX / 180 * Math.PI), 0, 1, 0);
		gl.glOrtho(-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
	}	
}
