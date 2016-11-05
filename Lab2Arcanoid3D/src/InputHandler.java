import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class InputHandler extends KeyAdapter {
	public static boolean sKeyPressedA = false;
	public static boolean sKeyPressedD = false;
	@Override
	public void keyPressed(KeyEvent e) {
	  if(e.getKeyCode() == KeyEvent.VK_D) {
		  sKeyPressedD = true;
	  }
	  else if(e.getKeyCode() == KeyEvent.VK_A) {
		  sKeyPressedA = true;
	  }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sKeyPressedD = false;
		sKeyPressedA = false;
	}
}