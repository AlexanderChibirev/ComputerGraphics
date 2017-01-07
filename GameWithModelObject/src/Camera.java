

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float mAngleX; 
	private float mAngleY; 
	private float mShiftAngleY = 1.0f;
	private float mShiftAngleX = 1.0f;
	private float mShifRZ = 0.08f;
	private float mRZ = -8;
	
	public void update(GLU glu, GL2 gl) {
		if(InputHandler.sKeyPressedUP) {
			mAngleY -= mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedDOWN) {
			mAngleY += mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedW) {			mRZ -= mShifRZ;
		}
		else if(InputHandler.sKeyPressedS) {			mRZ += mShifRZ;
		}
		else if(InputHandler.sKeyPressedRight) {
			mAngleX += mShiftAngleX;
		}
		else if(InputHandler.sKeyPressedLeft) {
			mAngleX -= mShiftAngleX;
		}
		
		if (mAngleY < -89.0){mAngleY= -89.0f;}
		if (mAngleY > 89.0){mAngleY= 89.0f;}
	
		glu.gluLookAt(0, 0, 0, 0 - Math.sin(mAngleX / 180 * Math.PI),0 + (Math.tan(mAngleY / 180 * Math.PI)), 0-Math.cos(mAngleX / 180 * Math.PI), 0, 1, 0);
		gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
	}	
}
