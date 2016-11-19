import java.awt.Dimension;


import javax.swing.JFrame;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
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
	public static  World sWorld;
	protected long mLast;
	
	private GLMovingPlatform mGlMovingPlatform = new GLMovingPlatform();
	private GLBlock mGlBlock = new GLBlock();
	
	private DYN4JBall mBall = new DYN4JBall(new Vector2(10,10));
	private DYN4JMovingPlatform mPhysicsMovingPlatform = new DYN4JMovingPlatform();
	
	public static TextForGame sScore = new TextForGame(new Vector2f(-360f, 250f));
	public static TextForGame sLevel = new TextForGame(new Vector2f(300f, 250f));
	
	private final GLU mGlu = new GLU();
	private Camera mCamera = new Camera();
	private float[] mColorBox = {1, 1, 1};
	RectangularPrism mGLBox = new RectangularPrism(new Vector3f(8.5f, 6.3f, -1.0f), mColorBox);
	private File mImage;
	private int mTextureId = 0;
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		//drawBackground(gl);
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
		mImage = new File("src/images/background.jpg");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width, height);
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		mCanvas = new GLCanvas(caps);
		mCanvas.setPreferredSize(size);
		mCanvas.setMinimumSize(size);
		mCanvas.setMaximumSize(size);
		mCanvas.setIgnoreRepaint(true);
		mCanvas.addGLEventListener(this);
		InputHandler inputHandler;
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		add(this.mCanvas);
		setResizable(false);
		pack();
		initializeWorldPhysics();
	}
	
	private void drawBackground (GL2 gl) {
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
		gl.glBindTexture(GL2.GL_TEXTURE_2D, mTextureId);	
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
		gl.glPopMatrix();
	}

	
	protected void initializeWorldPhysics() {//initial bodyes
		
		sWorld = new World();
		sWorld.setGravity(new Vector2(0,0));
		sWorld.getSettings().setRestitutionVelocity(0);
		//addPlat
		sWorld.addBody(mPhysicsMovingPlatform.getMovingPlatform());
		//addBox
		DYN4JBox mPhysicsBox = new DYN4JBox();
		for(Body boxPart: mPhysicsBox.getBox()) {
			sWorld.addBody(boxPart);
		}
		//addBall
		sWorld.addBody(mBall.getBall());
		//addBlocks
		DYN4JBlock mPhysicsBlock = new DYN4JBlock();
		for(GLBlock block: mPhysicsBlock.getBlock()) {
			sWorld.addBody(block);
		}
	}
	
	protected void render(GL2 gl) {//update bodyes 
		sScore.setText(gl,"Score: " + String.valueOf(mBall.getQuantityOfDestroyedBlocks()));
		sLevel.setText(gl,"Level: " + 1);
		gl.glScaled(WorldConsts.SCALE.getValue(), WorldConsts.SCALE.getValue(), WorldConsts.SCALE.getValue());
		if(mBall.isDead()) {
			//System.exit(0);
		}
		//set param for cube3d
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();	
		mCamera.update(mGlu, gl);
		
		mGLBox.draw(gl);
		mGlBlock.updateBlocks(gl, mGlu);
		updateMovingPlatform(gl);
		mBall.update(gl, mGlu);		
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
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	 }
	
	@Override
	public void init(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
	    float ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	    float diffuse[] ={ 1.0f, 1.0f, 1.0f, 1.0f };
	    float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	    float position[] ={ 0.0f, 3.0f, 2.0f, 0.0f };
	    float lmodel_ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
	    float local_view[] ={ 0.0f };

	   gl.glEnable(GL.GL_DEPTH_TEST);
	    gl.glDepthFunc(GL.GL_LESS);
	    
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);
	    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);
	    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view, 0);

	    gl.glEnable(GL2.GL_LIGHTING);
	    gl.glEnable(GL2.GL_LIGHT0);


		gl.setSwapInterval(0);
		initBackground(gl);
	}
	
	private void initBackground(GL2 gl) {
		try{
			//JPG!!!
			Texture texture = TextureIO.newTexture(mImage,true);
			mTextureId = texture.getTextureObject(gl);
		}
		catch(IOException e){
			e.printStackTrace();
		}	
	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glFlush();
	}
	
	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int w, int h) {
		// TODO Auto-generated method stub
	}
}
