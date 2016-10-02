package ska;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;


public class Main {
	private static final int WINDOW_HEIGHT = 800;
	private static final int WINDOW_WIDTH = 800;

	
	public static void main(String[] args){ 
		final GLProfile profile = GLProfile.get(GLProfile.GL2);	
		GLCapabilities capabilities = new GLCapabilities(profile);
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(new Renderer());
    	glcanvas.setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
        JFrame frame = new JFrame( "TwoProject" );
        
        frame.getContentPane().add(glcanvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        
        frame.setSize( frame.getContentPane().getPreferredSize() );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowX = Math.max(0,(screenSize.width - frame.getWidth()) / 2);
        int windowY = Math.max(0,(screenSize.height - frame.getHeight()) / 2);
        frame.setLocation(windowX, windowY);
        frame.setVisible( true );
	}
}
