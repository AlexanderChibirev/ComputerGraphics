import java.awt.Dimension;
import javax.swing.JFrame;
import org.dyn4j.dynamics.World;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;



public class Renderer extends JFrame implements GLEventListener {
	protected GLCanvas canvas;
	protected Animator animator;
	protected long last;
	
	private static final long serialVersionUID = 6057447011902836594L;
	private DYN4JBasePlatform objectsMassInfinity = new DYN4JBasePlatform() ;
	private DYN4JCannon cannon = new DYN4JCannon();
	
	public static  World world;
	
	
	public Renderer(int width, int height) {
		super("JOGL Example");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width, height);
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		
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
		this.canvas.addMouseListener(new CustomListener());
		
	}
	protected void initializeWorld() {
		Renderer.world = new World();
		for(int i = 0; i < objectsMassInfinity.getBasePlatform().size(); ++i) {
			Renderer.world.addBody(objectsMassInfinity.getBasePlatform().get(i));
		}
		for(int i = 0; i < cannon.getCannon().size(); ++i) {
			Renderer.world.addBody(cannon.getCannon().get(i));
		}
	}
	
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
		GL2 gl = glDrawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		this.update();
		this.render(gl);
		
	}
	
	protected void update() {
        long time = System.nanoTime();
        long diff = time - this.last;
        this.last = time;
    	double elapsedTime = diff / Const.NANO_TO_BASE.getValue();
    	Renderer.world.update(elapsedTime);
	}
	
	protected void render(GL2 gl) {
		gl.glScaled(Const.SCALE.getValue(), Const.SCALE.getValue(), Const.SCALE.getValue());
		gl.glTranslated(0.0, -1.0, 0.0);
		for (int i = 0;	i < Const.RANGE_END_FOR_BASE_PLATFORM.getValue(); ++i) {
			GLObject glObjects = (GLObject) Renderer.world.getBody((int) i);
			glObjects.render(gl);
		}
		updateBall(gl);
		updateCannon(gl);
	}
	
	private void updateBall(GL2 gl) {
		for (double ballID = Const.RANGE_END_FOR_CANNON.getValue(); ballID < Renderer.world.getBodyCount(); ballID++) {
			GLObject glObjects = (GLObject) Renderer.world.getBody((int) ballID);
			glObjects.render(gl);
			for (double platformID = Const.RANGE_BEGIN_FOR_BASE_PLATFORM.getValue(); platformID < Const.RANGE_END_FOR_CANNON.getValue(); platformID++) {
				if(Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody((int) platformID))) {
					Renderer.world.getBody((int) ballID).applyImpulse(0.05f);
				}
				if(Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody((int) Const.ID_FLOOR.getValue()))){
					Renderer.world.removeBody(((int) ballID));
					if(Const.RANGE_END_FOR_CANNON.getValue() == Const.RANGE_END_FOR_CANNON.getValue())
					{
						break;
					}
				}
			}
		}
	}

	private void updateCannon(GL2 gl) {
		for (double i = Const.RANGE_BEGIN_FOR_CANNON.getValue(); 
				i < Const.RANGE_END_FOR_CANNON.getValue(); ++i) {
			GLCannon glObjects = (GLCannon) Renderer.world.getBody((int) i);
			glObjects.render(gl);
			cannon.updatePhysicsCannon();
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
