import java.awt.MouseInfo;
import java.awt.Point;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float angleX; 
	private float angleY; // ���� �������� ������
	private float shiftX = -959;
	private float shiftY = -553;
	public void update(GLU glu) {
		Point location = MouseInfo.getPointerInfo().getLocation();
		double mouseX = location.getX() + shiftX;
		double mouseY = location.getY() + shiftY;
		if(CustomListener.mousePressed == true) {
			if(mouseY < 50) {
				angleY -= 1.50f;
			}
			if(mouseY > 50) {
				angleY += 1.50f;
			}
			if(mouseX < 50) {
				angleX -= 2.50f;
			}
			if(mouseX > 50) {
				angleX += 2.50f;
			}
			if (angleY < -89.0){angleY= -89.0f;}
			if (angleY > 89.0){angleY= 89.0f;}
		}
		glu.gluLookAt(0, 0, 0, 0-Math.sin(angleX/180*Math.PI),0 +(Math.tan(angleY/180*Math.PI)), 0-Math.cos(angleX/180*Math.PI), 0, 1, 0);
	}	
}
