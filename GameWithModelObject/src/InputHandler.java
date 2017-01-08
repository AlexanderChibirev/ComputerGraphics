import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class InputHandler extends KeyAdapter {
	public static boolean sKeyPressedEnter = false;
	
	
	public static boolean sKeyPressedW = false;
	public static boolean sKeyPressedS = false;
	public static boolean sKeyPressedA = false;
	public static boolean sKeyPressedD = false;
	
	public static boolean sKeyPressedUP = false;
	public static boolean sKeyPressedDOWN = false;
	public static boolean sKeyPressedLeft = false;
	public static boolean sKeyPressedRight = false;
	
	public static boolean sKeyPressedE = false;
	public static boolean sKeyPressedQ = false;
	
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
	  
	  else if (e.getKeyCode() == KeyEvent.VK_E) {
		  sKeyPressedE = true;
	  }
	  else if (e.getKeyCode() == KeyEvent.VK_Q) {
		  sKeyPressedQ = true;
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
	  else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		  sKeyPressedEnter = true;
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
		sKeyPressedE = false;
		sKeyPressedQ = false;
		sKeyPressedW = false;
		sKeyPressedS = false;
	}
}