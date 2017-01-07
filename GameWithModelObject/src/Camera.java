

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float mAngleX; 
	private float mAngleY; 
	private float mShiftAngleY = 0.03f;
	private float mShiftAngleX = 0.03f;
	private float mShifRZ = 0.001f;
	private float mRZ = -5;
	
	public void update(GLU glu, GL2 gl) {
		if(InputHandler.sKeyPressedUP) {
			mAngleY -= mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedDOWN) {
			mAngleY += mShiftAngleY;
		}
		else if(InputHandler.sKeyPressedW) {
		}
		else if(InputHandler.sKeyPressedS) {
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