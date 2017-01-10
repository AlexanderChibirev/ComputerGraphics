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
	protected long lastTime;
	private Vector<File> mTextures = new Vector<File>();
	private Vector<Integer> mTexturesID = new Vector<Integer>();
	RectangularPrism mGLBox;
	private static final double Z_DIST = 7.0;      // for the camera position
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private GLU glu;
	protected long mLast;
	private Player tankMajor;
	private Enemy tankEnemy;
	//private GLBall ball = new GLBall(0f, 0f, 4, 4); 
	private final static int FLOOR_LEN = 150;  // should be even
	private int starsDList;	
	private Camera mCamera = new Camera();
	public static Vector<GLBullet> glball = new Vector<>();
	

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
		mGLBox = new RectangularPrism(gl);
		initializeTexturesName();
		initTexture(gl);
		tankMajor = new Player(new Vector3f(0, 0, 0), gl, glu);
		float angle = 90f;
		tankEnemy =  new Enemy(new Vector3f(11, 0, 0), gl, angle);
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
	 //   mGLBox.drawFloor(gl, mTexturesID.get(PossitionID.BLOCK3.getValue()));
	    
	    mGLBox.drawFloor(gl, mTexturesID.get(PossitionID.BLOCK2.getValue()), new Vector3f(FLOOR_LEN/2, 0.1f, FLOOR_LEN/2));
	    tankEnemy.draw(gl);
	   
	    //mGLBox.drawBox(gl, 10);	   
	    mGLBox.drawBox(gl, FLOOR_LEN);
	    
	    // ball.render(gl, glu);
	    for(int i = 0; i < glball.size(); i++) {
	    	glball.get(i).render(gl, glu);
	    	if(glball.get(i).getBounds().intersects(tankEnemy.getBounds())) {
	    		//System.out.println(tankEnemy.getBounds());
	    		//System.out.println(glball.get(i).getBounds());
	    		glball.remove(i);// удаляем пулю
	    		//System.out.println(tankEnemy.getBounds());
	    		break;
	    		
	    	}
	    	//if(glball.get(i).x == 75 || glball.get(i).x == -75 || glball.get(i).y == 75 || glball.get(i).y == -75) {
    		//	glball.remove(i);// удаляем пулю
    		//}
	    }
	   // System.out.print("size: ");
	   // System.out.println(glball.size());
	    
	    tankMajor.draw(gl);
	   
	    if(tankMajor.getBounds().intersects(tankEnemy.getBounds())){
	    	
	    	//System.out.println("intersects");
	    	//System.out.println(tankMajor.getBounds());
	 	   // System.out.println(tankEnemy.getBounds());
	    }
	   // System.out.println(  glball.size());
	 
	    gl.glFlush();
	}// end of display
	
	

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
 
 } // end of ModelLoaderGLListener class

