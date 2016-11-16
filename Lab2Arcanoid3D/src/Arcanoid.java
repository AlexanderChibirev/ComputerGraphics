import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Arcanoid   {

	private static final int WINDOW_HEIGHT = 600;
	private static final int WINDOW_WIDTH = 800;

	public static void main(String[] args) {
		DialDisplay window = new DialDisplay(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setVisible(true);
		window.setTitle("Arcanoid 3D");
		centerWindow(window);
		window.start();
	}
	
	public static void centerWindow(Component frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowX = Math.max(0,(screenSize.width - frame.getWidth()) / 2);
		int windowY = Math.max(0,(screenSize.height - frame.getHeight()) / 2);
		frame.setLocation(windowX, windowY);
    }
}
