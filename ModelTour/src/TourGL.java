
// TourGL.java
// Andrew Davison, 7th February 2007, ad@fivedots.coe.psu.ac.th

/* A JFrame contains a JPanel which holds a Canvas subclass called
   TourCanvasGL. The canvas displays a 3D world consisting of:

     * a green and blue checkboard floor with a red square at its center
       and numbers along its z- and z- axes (as in the Java 3D
       Checkers3D example in chapter 15 of "Killer Game Programming
       in Java; online at http://fivedots.coe.psu.ac.th/~ad/jg/ch8/).

     * a skybox of stars

     * a billboard showing a tree, which rotates around the y-axis
       to always face the camera

     * user navigation using keys to move forward, backwards, left,
       right, up, down, and turn left and right. The user cannot move
       off the checkboard or beyond the skybox

     * the user can quit the game by pressing 'q', ctrl-c, the 'esc' key,
       or by clicking the close box

     * several shapes are placed at random on the ground. The 'game'
       (such as it is) is to navigate over these shapes to make
       them disappear. Then the game ends.

     * a game-over image and message placed as a 2D overlay in front
       of the game at the end

     * the application uses OpenGL bitmap and Java fonts

   This application uses the active rendering framework, seen in 
   CubeGL AR.    

   The game is paused when it is minimized or deactivated. 
   The window can be resized. There is a loading message at start-up time.

   The code uses the JSR-231 1.1.0 RC 2 version of JOGL, 
   released on 23rd Jan. 2007.
*/

import javax.swing.*;

import com.jogamp.nativewindow.awt.AWTGraphicsConfiguration;
import com.jogamp.nativewindow.awt.AWTGraphicsDevice;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

import java.awt.*;
import java.awt.event.*; 


public class TourGL extends JFrame implements WindowListener
{
  private static int DEFAULT_FPS = 80;

  private static final int PWIDTH = 512;   // size of panel
  private static final int PHEIGHT = 512; 


  private TourCanvasGL canvas;
  private JTextField shapesLeftTF;   // displays no. of shapes left
  private JTextField jtfTime;        // displays time spent in game


  public TourGL(long period) 
  {
    super("TourGL");

    Container c = getContentPane();
    c.setLayout( new BorderLayout() );
    c.add(makeRenderPanel(period), BorderLayout.CENTER);

    JPanel ctrls = new JPanel();   // a row of textfields
    ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));

    shapesLeftTF = new JTextField("Shapes Left: 5");
    shapesLeftTF.setEditable(false);
    ctrls.add(shapesLeftTF);

    jtfTime = new JTextField("Time Spent: 0 secs");
    jtfTime.setEditable(false);
    ctrls.add(jtfTime);

    c.add(ctrls, BorderLayout.SOUTH);

    addWindowListener(this);

    pack();
    setVisible(true);
  } // end of TourGL()


  private JPanel makeRenderPanel(long period)
  // construct the canvas
  {
    JPanel renderPane = new JPanel();
    renderPane.setLayout( new BorderLayout() );
    renderPane.setOpaque(false);
    renderPane.setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

    canvas = makeCanvas(period);
    renderPane.add("Center", canvas);

    canvas.setFocusable(true);
    canvas.requestFocus();    // the canvas now has focus, so receives key events

    // detect window resizes, and reshape the canvas accordingly
    renderPane.addComponentListener( new ComponentAdapter() {
      public void componentResized(ComponentEvent evt)
      {  Dimension d = evt.getComponent().getSize();
         // System.out.println("New size: " + d);
         canvas.reshape(d.width, d.height);
      } // end of componentResized()
    });

    return renderPane;
  }  // end of makeRenderPanel()


  private TourCanvasGL makeCanvas(long period)
  {
    // get a configuration suitable for an AWT Canvas (for TourCanvasGL)
    GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));

    GraphicsConfiguration config = null;


    return new TourCanvasGL(this, period, PWIDTH, PHEIGHT, config, caps);
  } // end of makeCanvas()


  public void setShapesLeft(int no)
  // called from CubeCanvasGL to show no. of shapes left
  {  shapesLeftTF.setText("Shapes Left: " + no);  }

  public void setTimeSpent(long t)
  // called from CubeCanvasGL to show time used
  {  jtfTime.setText("Time Spent: " + t + " secs"); }


  // ----------------- window listener methods -------------

  public void windowActivated(WindowEvent e) 
  { canvas.resumeGame();  }

  public void windowDeactivated(WindowEvent e) 
  {  canvas.pauseGame();  }

  public void windowDeiconified(WindowEvent e) 
  {  canvas.resumeGame();  }

  public void windowIconified(WindowEvent e) 
  {  canvas.pauseGame(); }

  public void windowClosing(WindowEvent e)
  {  canvas.stopGame();  }

  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

// -----------------------------------------

  public static void main(String[] args)
  { 
    int fps = DEFAULT_FPS;
    if (args.length != 0)
      fps = Integer.parseInt(args[0]);

    long period = (long) 1000.0/fps;
    System.out.println("fps: " + fps + "; period: " + period + " ms");

    new TourGL(period*1000000L);    // ms --> nanosecs 
  } // end of main()


} // end of TourGL class
