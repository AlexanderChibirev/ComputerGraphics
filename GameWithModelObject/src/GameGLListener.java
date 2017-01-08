import java.util.*;

import javax.swing.JFrame;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import OBJLoader.OBJModel;
import Utils.TextureUtils;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;


enum PossitionID {
	BACKGROUND(0),
	BOX(1),
	BLOCK1(2),
	BLOCK2(3),
	BLOCK3(4),
	MOVING_PLATFORM(5);
	private final Integer value;
	
	PossitionID(Integer value) {
        this.value = value;
    }
	public Integer getValue()   { return value; }
}

@SuppressWarnings("serial")
public class GameGLListener extends JFrame implements GLEventListener {
	protected GLCanvas canvas;
	public static  World sWorld;
	protected long lastTime;
	private Vector<File> mTextures = new Vector<File>();
	private Vector<Integer> mTexturesID = new Vector<Integer>();
	RectangularPrism mGLBox;
	private static final double Z_DIST = 7.0;      // for the camera position
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private GLU glu;
	
	private OBJModel tankEnemyModel;
	protected long mLast;
	private Player tankMajor;
	
	private final static int FLOOR_LEN = 150;  // should be even
	private int starsDList;	
	private Camera mCamera = new Camera();
	
	DYN4JBlock mPhysicsBlock = new DYN4JBlock();
	private GLBlock mGlBlock = new GLBlock();
	

	public void start() {
		this.lastTime = System.nanoTime();
		Animator animator = new Animator(this.canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();	
	}	
	
	private void initTexture(GL2 gl) {
		for(File name : mTextures) {
			try{
				Texture texture = TextureIO.newTexture(name,true);
				mTexturesID.add(texture.getTextureObject(gl));
			}
			catch(IOException e){
				e.printStackTrace();
			}	
		}
	}
	
	public GameGLListener(int windowWidth, int windowHeight) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(windowWidth, windowHeight);
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		canvas = new GLCanvas(caps);
		canvas.setPreferredSize(size);
		canvas.setMinimumSize(size);
		canvas.setMaximumSize(size);
		canvas.setIgnoreRepaint(true);
		canvas.addGLEventListener(this);
		InputHandler inputHandler;
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		add(this.canvas);
		setResizable(false);
		pack();
	}
	
	private void initializeTexturesName() {
		mTextures.add(new File("images/background.jpg"));
		mTextures.add(new File("images/boxPlatform.jpg"));
		mTextures.add(new File("images/block1.jpg"));
		mTextures.add(new File("images/block2.jpg"));
		mTextures.add(new File("images/block3.jpg"));
		mTextures.add(new File("images/movingPlatform.jpg"));
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		
		final GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		mGLBox = new RectangularPrism(new Vector3f( FLOOR_LEN/2, 0.1f, FLOOR_LEN/2), gl);
		initializeWorldPhysics();
		initializeTexturesName();
		initTexture(gl);
		tankMajor = new Player(new Vector3f(0, 1.2f, 0), gl);

		gl.setSwapInterval(0);   

		gl.glEnable(GL2.GL_DEPTH_TEST);		
		gl.glShadeModel(GL2.GL_SMOOTH);		
		addLight(gl);
		
		// load the OBJ model
		
		//tankEnemyModel = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
	} // end of init()
	
	private void addLight(GL2 gl) {// two white light sources
	    // enable light sources
	    gl.glEnable(GL2.GL_LIGHTING);
	    gl.glEnable(GL2.GL_LIGHT0);
	    gl.glEnable(GL2.GL_LIGHT1);
	
	    float[] whiteLight = {1.0f, 1.0f, 1.0f, 1.0f};  // bright white
	
	    float lightPos[] = {10.0f, 10.0f, -10.0f, 1.0f}; 
	    // light located at the right, top, and back 
	    // light 0 has white ambient, diffuse, and specular components by default
	    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
	    
	    float lightPos1[] = {-10.0f, -10.0f, 10.0f, 1.0f};
	    // light located at the left, bottom, and front 
	    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, whiteLight, 0);  // diffuse white
	    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos1, 0);
	} // end of addLight()
	

	private void updateGLBlocks(GL2 gl) {
		for (double i = RangesConst.RANGE_BEGIN_FOR_BLOCKS.getValue(); i < sWorld.getBodyCount(); ++i) {
			mGlBlock = (GLBlock) sWorld.getBody((int) i);
			mGlBlock.render(gl, mTexturesID.get(PossitionID.BLOCK1.getValue()));
		}
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {  // called when the drawable component is moved or resized
	  
	    final GL2 gl = drawable.getGL().getGL2();
	    if (height == 0)
	      height = 1;    // to avoid division by 0 in aspect ratio below
	
	    gl.glViewport(x, y, width, height);  // size of drawing area 
	
	    gl.glMatrixMode(GL2.GL_PROJECTION);
	    gl.glLoadIdentity();
	    glu.gluPerspective(45.0, (float)width/(float)height, 1, 100); // 5, 100); 
	    // fov, aspect ratio, near & far clipping planes
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
	} // end of reshape()
	
	protected void update() {
        long time = System.nanoTime();
        long diff = time - this.mLast;
        this.mLast = time;
    	double elapsedTime = diff / WorldConsts.NANO_TO_BASE.getValue();
    	if(InputHandler.sKeyPressedEnter) {
    		sWorld.update(elapsedTime);
    	}
	}
	protected void initializeWorldPhysics() {//initial bodyes
		
		sWorld = new World();
		sWorld.setGravity(new Vector2(0,0));
		sWorld.getSettings().setRestitutionVelocity(0);
		//addPlat
		//sWorld.addBody(mPhysicsMovingPlatform.getMovingPlatform());
		//addBox
		//DYN4JBox mPhysicsBox = new DYN4JBox();
		//for(Body boxPart: mPhysicsBox.getBox()) {
		//	sWorld.addBody(boxPart);
		//}
		//addBall
		//sWorld.addBody(mBall.getBall());
		//addBlocks
		
		for(GLBlock block: mPhysicsBlock.getBlock()) {
			System.out.println("sadad");
			sWorld.addBody(block);
		}
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {// the model is rotated and rendered

	    // update the rotations (if rotations were specified)
	    final GL2 gl = drawable.getGL().getGL2();

	    // clear colour and depth buffers
	    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    glu.gluLookAt(0,0, Z_DIST, 0,0,0, 0, 1, 0);   // position camera

	    mGLBox.drawBackground(gl, mTexturesID);
	    
	    mCamera.update(glu, gl);
	    mGLBox.drawFloor(gl, mTexturesID.get(PossitionID.BLOCK3.getValue()));
	    drawSkyBox(gl);
	    updateGLBlocks(gl);		
	    tankMajor.draw(gl);
	    gl.glFlush();
	} // end of display
	
	private void drawSkyBox(GL2 gl) {
		 starsDList = gl.glGenLists(1);
		 gl.glNewList(starsDList, GL2.GL_COMPILE);
		 mGLBox.drawStars(gl, FLOOR_LEN);
		 gl.glEndList();
		 gl.glCallList(starsDList);
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
 
 } // end of ModelLoaderGLListener class

