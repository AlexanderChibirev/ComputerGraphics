import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Vector;

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
	private float[] bodyImpulse = new float[50];
	private static final long serialVersionUID = 6057447011902836594L;
	protected GLCanvas canvas;
	protected Animator animator;
	public static  World world;
	protected long last;
	private DYN4JBasePlatform objectsMassInfinity = new DYN4JBasePlatform() ;
	private DYN4JCannon cannon = new DYN4JCannon();
	public static double mouseX = 0;
	public static double mouseY = 0;
	private static float rotationAngle = 0f;
	public static boolean mouseClicked = false;
	//private float impulse = 0.1f;
	public Renderer(int width, int height) {
		
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
		initImpulse();
		this.canvas.addMouseListener(new CustomListener());
	}
	
	private void initImpulse(){
		for(int i = 0; i < bodyImpulse.length; ++i){
			bodyImpulse[i] = 0.1f;
		}
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
		if(mouseClicked) {
			System.out.println(mouseClicked);
		}
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
    	double elapsedTime = diff / Const.NANO_TO_BASE.getValue();
        // update the world with the elapsed time
    	Renderer.world.update(elapsedTime);
	}
	
	protected void render(GL2 gl) {
		gl.glScaled(Const.SCALE.getValue(), Const.SCALE.getValue(), Const.SCALE.getValue());
		gl.glTranslated(0.0, -1.0, 0.0);
		for (double i = Const.RANGE_BEGIN_FOR_BASE_PLATFORM.getValue();
				i < Const.RANGE_END_FOR_BASE_PLATFORM.getValue(); ++i){
			GLObject glObjects = (GLObject) Renderer.world.getBody((int) i);
			glObjects.render(gl);
		}
		updateBall(gl);//11 
		updateCannon(gl);
	}
	
	private void updateBall(GL2 gl) {
		for (double ballID = Const.RANGE_END_FOR_CANNON.getValue(); ballID < Renderer.world.getBodyCount(); ballID++) {
			GLObject glObjects = (GLObject) Renderer.world.getBody((int) ballID);
			glObjects.render(gl);
			for (int platformID = 0; platformID < Const.RANGE_END_FOR_CANNON.getValue(); platformID++) {
				if(Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody(platformID))) {
					int it = (int) (ballID -  Const.RANGE_END_FOR_CANNON.getValue());
					if(bodyImpulse[it] <= 0) { 
						bodyImpulse[it] = 0;
					}
					else {
						bodyImpulse[it] -= 0.0001f;
					}
					System.out.println(bodyImpulse[it]);
					Renderer.world.getBody((int) ballID).applyImpulse(bodyImpulse[it]);
				}
				if(Renderer.world.getBody((int) ballID).isInContact(Renderer.world.getBody(8))){
					bodyImpulse[(int) (ballID -  Const.RANGE_END_FOR_CANNON.getValue())] = 0.1f;
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
		gl.glTranslated(Const.GUN_TURRET_TRANSLATE_X.getValue(), Const.GUN_TURRET_TRANSLATE_Y.getValue(), 0);
		gl.glRotatef(rotationAngle, 0f, 0f, 1f);
		gl.glTranslated(-1 * Const.GUN_TURRET_TRANSLATE_X.getValue(), -1 * Const.GUN_TURRET_TRANSLATE_Y.getValue(), 0);
		for (double i = Const.RANGE_BEGIN_FOR_CANNON.getValue(); 
				i < Const.RANGE_END_FOR_CANNON.getValue(); ++i) {
			GLCannon glObjects = (GLCannon) Renderer.world.getBody((int) i);
			glObjects.render(gl);
		}
		Point location = MouseInfo.getPointerInfo().getLocation();
		mouseX = location.getX() - 959;
		mouseY = location.getY() - 445;
		if(mouseX  < 0) {
			rotationAngle = ((float) (Math.atan2(mouseX,mouseY) *  180 / 3.14159265)) - 90;	
		}
		else {
			rotationAngle = (float) (Math.atan2(mouseX, mouseY) * 180 / 3.14159265) - 90;
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
