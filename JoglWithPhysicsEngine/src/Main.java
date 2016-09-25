import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	private static final int WINDOW_HEIGHT = 700;
	private static final int WINDOW_WIDTH = 800;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Renderer window = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setVisible(true);
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
