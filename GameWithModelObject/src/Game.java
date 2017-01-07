import java.awt.*;

import javax.swing.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;


public class Game extends JFrame{
	private static final long serialVersionUID = -2687152741472560149L;
	public static Animator animator = null;
    public static void main(String[] args) {
        final Game app = new Game();
        // запуск асинхронной операции
        SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    app.setVisible(true);
                }
            }
        );
        
        SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    animator.start();
                }
            }
        );
    }
  
    public Game() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelLoaderGLListener display = new ModelLoaderGLListener();
        GLCanvas glcanvas =  new GLCanvas();
        glcanvas.addGLEventListener(display);
        
        animator = new Animator(glcanvas);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 800);
        centerWindow(this);
    }
    
    private void centerWindow(Component frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowX = Math.max(0,(screenSize.width - frame.getWidth()) / 2);
		int windowY = Math.max(0,(screenSize.height - frame.getHeight()) / 2);
		frame.setLocation(windowX, windowY);
    }
}
