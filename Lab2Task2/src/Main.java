import java.awt.*;

import javax.swing.*;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;


public class Main extends JFrame {
	private CustomListener mMouseListener = new CustomListener();
	private static final long serialVersionUID = -2687152741472560149L;
	public static Animator animator = null;
    public static void main(String[] args) {
        final Main app = new Main();
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
  
    public Main() {
        super("3D function");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DialDisplay display = new DialDisplay();
        GLCanvas glcanvas =  new GLCanvas();
        glcanvas.addGLEventListener(display);
        glcanvas.addMouseMotionListener(mMouseListener);
        glcanvas.addMouseListener(mMouseListener);
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