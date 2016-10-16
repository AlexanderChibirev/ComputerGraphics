import java.awt.event.MouseListener;


public class CustomListener  implements MouseListener {
	public static boolean mousePressed = false;
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {//кто только щелчек
		
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		mousePressed = true;
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		mousePressed = false;
	}
}

