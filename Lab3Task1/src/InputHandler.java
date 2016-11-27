import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class InputHandler extends KeyAdapter {
	
	public static boolean sKeyPressedPlus = false;
	public static boolean sKeyPressedMinus = false;	
	@Override
	public void keyPressed(KeyEvent e) {
	  if (e.getKeyCode() == KeyEvent.VK_PLUS) {
		  sKeyPressedPlus = true;
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
		  sKeyPressedMinus = true;
	  }	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sKeyPressedPlus = false;
		sKeyPressedMinus = false;
	}
}