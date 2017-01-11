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

import OBJLoader.OBJModel;
import Utils.LoaderTextures;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;


enum PossitionTextureID {
	BACKGROUND(0),
	BLOCK_UNDESTROYABLES(1),
	BLOCK1(2),
	BLOCK_DESTROYABLES(3),
	THE_END(4),
	PLATFORM(5), BLOCK22(6), SKY_BOX(8);
	private final Integer value;
	
	PossitionTextureID(Integer value) {
        this.value = value;
    }
	public Integer getValue()   { return value; }
}

@SuppressWarnings("serial")
public class GameGLListener extends JFrame implements GLEventListener { 
	
	protected double elapsedTime;	
	protected GLCanvas canvas;
	protected long lastTime;
	protected long pLast;
	
	private GLU glu;
	private Player tankMajor;	
	private Camera camera = new Camera();
	private BlockUndestroyable blocksUndestroyable = new BlockUndestroyable();
	private BlockDestroyable mBlocksDestroyable = new BlockDestroyable();

	
	
	protected void updateTime() {
        long time = System.nanoTime();
        long diff = time - this.pLast;
        this.pLast = time;
        
    	elapsedTime = diff / WorldConst.NANO_TO_BASE;
	}

	public void start() {
		this.lastTime = System.nanoTime();
		Animator animator = new Animator(this.canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();	
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
	
	
	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();		
		LoaderTextures.loadTextures(gl);
		tankMajor = new Player(new Vector3f(0, 0, 0), gl, glu);
		Entity.initMap(gl);
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
	    glu.gluPerspective(45.0, (float)width/(float)height, 1, 100); 
	    gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
	} // end of reshape()
	
	private void updateWorld(GL2 gl) {
	    
	    for(int i = 0; i < Entity.sTankEnemyes.size(); i++ ) {
	    	Entity.sTankEnemyes.get(i).update(gl);
	    }	    
	    updateBullet(gl);   
	    updateBlocks();	    
	    Entity.sBoss.update(gl);	   
	    tankMajor.update(gl);	    
	    gl.glFlush();
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {// the model is rotated and rendered
	    // update the rotations (if rotations were specified)
	    final GL2 gl = drawable.getGL().getGL2();
	    // clear colour and depth buffers
	    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();   
	    final double Z_DIST = 7.0; // for the camera position
		glu.gluLookAt(0,0, Z_DIST, 0,0,0, 0, 1, 0);// position camera
	    if(Entity.sBoss.isDead()) {
	    	DrawRects.drawBackground(gl, LoaderTextures.sTexturesID);
	    }
	    else {
	    	updateTime();
	    	DrawRects.drawBackground(gl, LoaderTextures.sTexturesID);
	    	camera.update(glu, gl);
		    DrawRects.drawFloor(gl, PossitionTextureID.SKY_BOX.getValue(), new Vector3f(WorldConst.SKYBOX_SIZE, WorldConst.SKYBOX_SIZE, WorldConst.SKYBOX_SIZE));
		    DrawRects.drawFloor(gl, LoaderTextures.sTexturesID.get(PossitionTextureID.PLATFORM.getValue()), new Vector3f(WorldConst.SKYBOX_SIZE, 0.1f, WorldConst.SKYBOX_SIZE));
		    blocksUndestroyable.draw(gl);
		    mBlocksDestroyable.draw(gl);
		    updateWorld(gl);
	    }
	}
	
	private void updateBullet(GL2 gl) {
		 for(int i = 0; i < Bullet.sBulletsArray.size(); i++) {
		    	Bullet.sBulletsArray.get(i).render(gl, glu);
		    	for(int j = 0; j < Entity.sTankEnemyes.size(); j++){
			    	if(Bullet.sBulletsArray.get(i).getBounds().intersects(Entity.sTankEnemyes.get(j).getBounds())) {
			    		Bullet.sBulletsArray.remove(i);
			    		Entity.sTankEnemyes.remove(j);
			    		break;
			    	}
		    	}	    	
		    }
		    for(int i = 0; i < Bullet.sBulletsArray.size(); i++) {
		    	if(Bullet.sBulletsArray.get(i).x > WorldConst.SKYBOX_SIZE 
		    			|| Bullet.sBulletsArray.get(i).x < -WorldConst.SKYBOX_SIZE 
		    			|| Bullet.sBulletsArray.get(i).y > WorldConst.SKYBOX_SIZE 
		    			|| Bullet.sBulletsArray.get(i).y < -WorldConst.SKYBOX_SIZE) {
		     		Bullet.sBulletsArray.remove(i);
		     		break;
		 		}	    	
		    }
	}
	
	private void updateBlocks() {
		for(int i = 0; i < Entity.sBlockDestroyables.size(); ++i) {
			for(int j = 0; j < Bullet.sBulletsArray.size(); ++j) {
				if(Entity.sBlockDestroyables.get(i).getBounds().
						intersects(Bullet.sBulletsArray.get(j).getBounds())) {
					Entity.sBlockDestroyables.remove(i);
					Bullet.sBulletsArray.remove(j);
					break;
				}
			}
		}	    
	    for(int i = 0; i < Entity.sBlockUndestroyables.size(); ++i) {
			for(int j = 0; j < Bullet.sBulletsArray.size(); ++j) {
				if(Entity.sBlockUndestroyables.get(i).getBounds().
						intersects(Bullet.sBulletsArray.get(j).getBounds())) {
					Bullet.sBulletsArray.remove(j);
					break;
				}
			}
		}
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
 
 } // end of ModelLoaderGLListener class

