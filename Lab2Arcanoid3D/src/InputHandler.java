import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class InputHandler extends KeyAdapter {
	public static boolean sKeyPressedA = false;
	public static boolean sKeyPressedD = false;
	
	public static boolean sKeyPressedUP = false;
	public static boolean sKeyPressedDOWN = false;
	public static boolean sKeyPressedLeft = false;
	public static boolean sKeyPressedRight = false;
	
	public static boolean sKeyPressedW = false;
	public static boolean sKeyPressedS = false;
	
	@Override
	public void keyPressed(KeyEvent e) {
	  if(e.getKeyCode() == KeyEvent.VK_D) {
		  sKeyPressedD = true;
	  }
	  else if(e.getKeyCode() == KeyEvent.VK_A) {
		  sKeyPressedA = true;
	  }
	  
	  else if (e.getKeyCode() == KeyEvent.VK_UP) {
		  sKeyPressedUP = true;
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		  sKeyPressedDOWN = true;
	  }
	  
	  else if (e.getKeyCode() == KeyEvent.VK_W) {
		  sKeyPressedW = true;
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_S) {
		  sKeyPressedS = true;
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		  sKeyPressedLeft = true;
	  } 
	  else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		  sKeyPressedRight = true;
	  }  
	  
	}

	@Override
	public void keyReleased(KeyEvent e) {
		sKeyPressedD = false;
		sKeyPressedA = false;
		sKeyPressedUP = false;
		sKeyPressedDOWN = false;
		sKeyPressedLeft = false;
		sKeyPressedRight = false;
		sKeyPressedW = false;
		sKeyPressedS = false;		
	}
}