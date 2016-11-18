import java.awt.MouseInfo;
import java.awt.Point;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float mAngleX; 
	private float mAngleY; // ”глы поворота камеры
	private float mShiftX = -959;
	private float mShiftY = -553;
	private float mShiftAngleY = 0.050f;
	private float mShiftAngleX = 0.050f;
	
	public void update(GLU glu) {
		Point location = MouseInfo.getPointerInfo().getLocation();
		double mouseX = location.getX() + mShiftX;
		double mouseY = location.getY() + mShiftY;
		if(CustomListener.mousePressed == true) {
			if(mouseY < 50) {
				mAngleY -= mShiftAngleY;
			}
			if(mouseY > 50) {
				mAngleY += mShiftAngleY;
			}
			if(mouseX < 50) {
				mAngleX -= mShiftAngleX;
			}
			if(mouseX > 50) {
				mAngleX += mShiftAngleX;
			}
			if (mAngleY < -89.0){mAngleY= -89.0f;}
			if (mAngleY > 89.0){mAngleY= 89.0f;}
		}
		glu.gluLookAt(0, 0, 0, 0 - Math.sin(mAngleX / 180 * Math.PI),0 + (Math.tan(mAngleY / 180 * Math.PI)), 0-Math.cos(mAngleX / 180 * Math.PI), 0, 1, 0);
	}	
}
