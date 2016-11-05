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
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;



public class DialDisplay extends JFrame implements GLEventListener  {
	private static final long serialVersionUID = 6530460753888462810L;
	private Light mLight = new Light();
	protected GLCanvas mCanvas;
	protected Animator mAnimator;
	public static  World sWorld;
	protected long mLast;

	private DYN4JMovingPlatform mPhysicsMovingPlatform = new DYN4JMovingPlatform();
	private GLMovingPlatform mGlMovingPlatform = new GLMovingPlatform();
	private DYN4JBox mPhysicsBox = new DYN4JBox();
	private GLBox mGlBox = new GLBox();
	private InputHandler inputHandler;
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		drawBackground(gl);
		mLight.setLight(gl);
		this.update();
		this.render(gl);
	}

	protected void update() {
        long time = System.nanoTime();
        long diff = time - this.mLast;
        this.mLast = time;
    	double elapsedTime = diff / WorldConsts.NANO_TO_BASE.getValue();
    	DialDisplay.sWorld.update(elapsedTime);
	}
	public void start() {
		this.mLast = System.nanoTime();
		Animator animator = new Animator(this.mCanvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();	
	}
	
	public DialDisplay(int width, int height) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width, height);
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		this.mCanvas = new GLCanvas(caps);
		this.mCanvas.setPreferredSize(size);
		this.mCanvas.setMinimumSize(size);
		this.mCanvas.setMaximumSize(size);
		this.mCanvas.setIgnoreRepaint(true);
		this.mCanvas.addGLEventListener(this);
		this.inputHandler = new InputHandler();
		this.addKeyListener(this.inputHandler);
		this.add(this.mCanvas);
		this.setResizable(false);
		this.pack();
		this.initializeWorld();
	}
	
	private void drawBackground (GL2 gl) {
		int textureId = 0;
		gl.glDisable(GL.GL_DEPTH_TEST); 
		gl.glDisable(GL.GL_CULL_FACE); 
		gl.glEnable(GL2.GL_TEXTURE_2D);	
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0, 1, 0, 1, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glDepthMask(false);
		gl.glEnable(GL2.GL_TEXTURE_2D);	 
		gl.glEnable(GL2.GL_BLEND);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE); //importante
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); //importante
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);	
		try{
			File im2 = new File("src/images/background.jpg"); //JPG!!!
			Texture t2 = TextureIO.newTexture(im2,true);
			textureId = t2.getTextureObject(gl);			
		}
		catch(IOException e){
			e.printStackTrace();
		}	
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(0f,0f); gl.glVertex2f(0,0);
			gl.glTexCoord2f(0f,1f); gl.glVertex2f(0,1f);
			gl.glTexCoord2f(1f,1f); gl.glVertex2f(1,1);
			gl.glTexCoord2f(1f,0f); gl.glVertex2f(1,0);
		gl.glEnd();		

		gl.glDepthMask(true);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}

	
	protected void initializeWorld() {//initial bodyes
		DialDisplay.sWorld = new World();
		DialDisplay.sWorld.addBody(mPhysicsMovingPlatform.getMovingPlatform());
		for(GLBox boxPart: mPhysicsBox.getBox()) {
			DialDisplay.sWorld.addBody(boxPart);
		}
	}
	
	protected void render(GL2 gl) {//update bodyes 
		gl.glScaled(WorldConsts.SCALE.getValue(), WorldConsts.SCALE.getValue(), WorldConsts.SCALE.getValue());
		updateMovingPlatform(gl);
		mGlBox.updateBox(gl);
	}
	
	private void updateMovingPlatform(GL2 gl) {
		mGlMovingPlatform.updateMovingPlatform(gl);
		mPhysicsMovingPlatform.updatePossitionMovingPlatform();
	}
	
	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glFrontFace(GL2.GL_CCW);
		gl.glCullFace(GL2.GL_BACK);
		//gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	 }
	
	@Override
	public void init(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-400, 400, -300, 300, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.setSwapInterval(0);
	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}
}
