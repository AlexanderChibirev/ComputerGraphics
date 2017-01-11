import java.util.*;

import javax.swing.JFrame;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


import java.awt.Dimension;
import java.io.File;
import java.io.IOException;


enum PossitionTextureID {
	BACKGROUND(0),
	BLOCK_UNDESTROYABLES(1),
	BLOCK1(2),
	BLOCK_DESTROYABLES(3),
	BLOCK3(4),
	PLATFORM(5), BLOCK22(6);
	private final Integer value;
	
	PossitionTextureID(Integer value) {
        this.value = value;
    }
	public Integer getValue()   { return value; }
}

@SuppressWarnings("serial")
public class GameGLListener extends JFrame implements GLEventListener {
	protected GLCanvas canvas;
	protected long lastTime;
	private Vector<File> mTextures = new Vector<File>();
	public static Vector<Integer> sTexturesID = new Vector<Integer>();
	RectangularPrism mSkyBox;
	private GLU mGlu;
	protected long mLast;
	private Player mTankMajor;
	private Enemy mTankEnemy;
	static double elapsedTime;
	private final static int FLOOR_LEN = 150;  // should be even
	
	private Camera mCamera = new Camera();
	private BlockUndestroyable mBlocksUndestroyable = new BlockUndestroyable();
	private BlockDestroyable mBlocksDestroyable = new BlockDestroyable();
	public static Vector<Enemy> sTankEnemyes = new Vector<>();
	
	protected void update() {
        long time = System.nanoTime();
        long diff = time - this.mLast;
        this.mLast = time;
    	elapsedTime = diff / WorldConsts.NANO_TO_BASE.getValue();
	}

	public void start() {
		this.lastTime = System.nanoTime();
		System.out.println(lastTime);
		Animator animator = new Animator(this.canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();	
	}	
	
	private void initTexture(GL2 gl) {
		for(File name : mTextures) {
			try{
				Texture texture = TextureIO.newTexture(name,true);
				sTexturesID.add(texture.getTextureObject(gl));
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
	
	private void initMap(GL2 gl) {
		int size = 3;
		float x = -size * 5;
		int sizeShift = 70;
		float z = 0;
		int typeBlock = 0;
		int quantE = 0;
		for (int i = 0; i < Map.HEIGHT_MAP; i++) {
			for (int j = 0; j < Map.WIDTH_MAP; j++)
			{
				final char tile = Map.TileMap[i].charAt(j);
				if(tile == 'd' || tile == 'u' ||  tile == 'e') {
					z = -i * size * 2;
					x = j * size * 2;
					if (tile == 'd') {
						typeBlock = PossitionTextureID.BLOCK_DESTROYABLES.getValue();
						BlockDestroyable.sBlockDestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, size * 2, size * 2, typeBlock));
					}
					else if ((tile == 'u')) {
						typeBlock = PossitionTextureID.BLOCK_UNDESTROYABLES.getValue();
						BlockUndestroyable.sBlockUndestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, size, size, typeBlock));
					}
					
					else if ((tile == 'e')) {
						if(quantE % 2 == 0)
							sTankEnemyes.addElement(new Enemy(x - sizeShift, z + sizeShift, Direction.UP, gl));	
						else 
							sTankEnemyes.addElement(new Enemy(x - sizeShift, z + sizeShift, Direction.RIGHT, gl));	
						quantE++;
					}
				}				
			}
		}
	}

	private void initializeTexturesName() {
		mTextures.add(new File("images/background.jpg"));
		mTextures.add(new File("images/boxPlatform.jpg"));
		mTextures.add(new File("images/block1.jpg"));
		mTextures.add(new File("images/block2.jpg"));
		mTextures.add(new File("images/block3.jpg"));
		mTextures.add(new File("images/movingPlatform.jpg"));
		mTextures.add(new File("images/block22.jpg"));
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
	
		final GL2 gl = drawable.getGL().getGL2();
		mGlu = new GLU();
		
		mSkyBox = new RectangularPrism(gl);
		initializeTexturesName();
		initTexture(gl);
		mTankMajor = new Player(new Vector3f(0, 0, 0), gl, mGlu);
		initMap(gl);
		float angle = 90f;
		
		gl.setSwapInterval(0);   

		gl.glEnable(GL2.GL_DEPTH_TEST);		
		gl.glShadeModel(GL2.GL_SMOOTH);		
		addLight(gl);
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
	    mGlu.gluPerspective(45.0, (float)width/(float)height, 1, 100); 
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
	    final double Z_DIST = 7.0; // for the camera position
	    mGlu.gluLookAt(0,0, Z_DIST, 0,0,0, 0, 1, 0);   // position camera
	    update();
	    RectangularPrism.drawBackground(gl, sTexturesID);
	    mCamera.update(mGlu, gl);
	    
	    mBlocksUndestroyable.draw(gl);
	    mBlocksDestroyable.draw(gl);
	    RectangularPrism.drawFloor(gl, sTexturesID.get(PossitionTextureID.PLATFORM.getValue()), new Vector3f(FLOOR_LEN/2, 0.1f, FLOOR_LEN/2));
	    
	    for(int i = 0; i < sTankEnemyes.size(); i++ ) {
	    	sTankEnemyes.get(i).update(gl);
	    }
	    
	    RectangularPrism.drawBox(gl, FLOOR_LEN);
	    
	    /////////////////////////////////////////////////////////////update bullet
	    for(int i = 0; i < Bullet.sBulletsArray.size(); i++) {
	    	Bullet.sBulletsArray.get(i).render(gl, mGlu);
	    	for(int j = 0; j < sTankEnemyes.size(); j++){
		    	if(Bullet.sBulletsArray.get(i).getBounds().intersects(sTankEnemyes.get(j).getBounds())) {
		    		Bullet.sBulletsArray.remove(i);
		    		sTankEnemyes.remove(j);
		    		break;
		    	}
	    	}	    	
	    }
	    for(int i = 0; i < Bullet.sBulletsArray.size(); i++) {
	    	if(Bullet.sBulletsArray.get(i).x > 75 || Bullet.sBulletsArray.get(i).x < -75 || Bullet.sBulletsArray.get(i).y > 75 || Bullet.sBulletsArray.get(i).y < -75) {
	     		Bullet.sBulletsArray.remove(i);
	     		break;
	 		}	    	
	    }
	    /////////////////////////////////////////////////////////////
	    
	    
	    
	    for(int i = 0; i < BlockDestroyable.sBlockDestroyables.size(); ++i) {
			for(int j = 0; j < Bullet.sBulletsArray.size(); ++j) {
				if(BlockDestroyable.sBlockDestroyables.get(i).getBounds().
						intersects(Bullet.sBulletsArray.get(j).getBounds())) {
					System.out.println("asdsa");
					BlockDestroyable.sBlockDestroyables.remove(i);
					Bullet.sBulletsArray.remove(j);
					break;
				}
			}
		}
	    
	    mTankMajor.draw(gl);	 
	    gl.glFlush();
	}
	
	

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
 
 } // end of ModelLoaderGLListener class

