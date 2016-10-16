import java.awt.MouseInfo;
import java.awt.Point;
import com.jogamp.opengl.glu.GLU;

public class Camera {
	private float angleX; 
	private float angleY; // ”глы поворота камеры
	public void update(GLU glu) {
		Point location = MouseInfo.getPointerInfo().getLocation();
		double mouseX = location.getX() - 959;
		double mouseY = location.getY() - 553;
		if(CustomListener.mousePressed == true) {
			if(mouseY < 50) {
				angleY -= 1.50f;
			}
			if(mouseY > 50) {
				angleY += 1.50f;
			}
			if(mouseX < 50) {
				angleX -= 1.50f;
			}
			if(mouseX > 50) {
				angleX += 1.50f;
			}
		}
		glu.gluLookAt(0, 0, 0, 0-Math.sin(angleX/180*Math.PI),0 +(Math.tan(angleY/180*Math.PI)), 0-Math.cos(angleX/180*Math.PI), 0, 1, 0);
	}	
}
