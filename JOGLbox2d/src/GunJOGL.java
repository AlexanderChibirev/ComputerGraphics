import java.awt.Dimension;

import javax.swing.JFrame;
import org.dyn4j.dynamics.World;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;



public class GunJOGL extends JFrame implements GLEventListener {
	
	private static final long serialVersionUID = 6057447011902836594L;
	public static final double SCALE = 45.0;
	public static final double NANO_TO_BASE = 1.0e9;
	protected GLCanvas canvas;
	protected Animator animator;
	protected World world;
	protected long last;
	private ObjectWithMassInfiniti objects = new ObjectWithMassInfiniti() ;
	public GunJOGL(int width, int height) {
		super("JOGL Example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width, height);
		// setup OpenGL capabilities
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		// create a canvas to paint to 
		this.canvas = new GLCanvas(caps);
		this.canvas.setPreferredSize(size);
		this.canvas.setMinimumSize(size);
		this.canvas.setMaximumSize(size);
		this.canvas.setIgnoreRepaint(true);
		this.canvas.addGLEventListener(this);
		
		this.add(this.canvas);
		this.setResizable(false);
		this.pack();
		this.initializeWorld();
	}
	
	/**
	 * Creates game objects and adds them to the world.
	 * <p>
	 * Basically the same shapes from the Shapes test in
	 * the TestBed.
	 */
	protected void initializeWorld() {
		// create the world
		this.world = new World();

		// create all your bodies/joints
		// create the floor
		for(int i = 0; i < objects.getBasePlatform().size(); ++i){
			this.world.addBody(objects.getBasePlatform().get(i));		
		}
	}
	
	/**
	 * Start active rendering the example.
	 * <p>
	 * This should be called after the JFrame has been shown.
	 */
	public void start() {
		this.last = System.nanoTime();
		Animator animator = new Animator(this.canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();
	}
	
	@Override
	public void init(GLAutoDrawable glDrawable) {
		GL2 gl = glDrawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-400, 400, -300, 300, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.setSwapInterval(0);
	}
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		//тут update-им
		GL2 gl = glDrawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		this.render(gl);
		this.update();
	}
	

	
	
	protected void update() {
        long time = System.nanoTime();
        // get the elapsed time from the last iteration
        long diff = time - this.last;
        // set the last time
        this.last = time;
    	// convert from nanoseconds to seconds
    	double elapsedTime = diff / NANO_TO_BASE;
        // update the world with the elapsed time
        this.world.update(elapsedTime);
	}
	
	protected void render(GL2 gl) {
		// apply a scaling transformation
		gl.glScaled(SCALE, SCALE, SCALE);
		// lets move the view up some
		gl.glTranslated(0.0, -1.0, 0.0);
		// draw all the objects in the world
		for (int i = 0; i < this.world.getBodyCount(); i++) {
			// get the object
			GLObject go = (GLObject) this.world.getBody(i);
			// draw the object
			go.render(gl);
		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
	}
	
}
