import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class LineToArrow extends JFrame  {
	private static final long serialVersionUID = -8427804125587735669L;
	public static Animator animator = null;
    public static void main(String[] args) {
        final LineToArrow app = new LineToArrow();
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
  
    public LineToArrow() {
        super("Cannabola");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TwistValueController inputHandler =  new TwistValueController();
		addKeyListener(inputHandler);
        DialDisplay display = new DialDisplay(inputHandler);
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
