import java.util.*;

import javax.swing.JFrame;
import javax.vecmath.Vector2f;

import org.dyn4j.dynamics.World;

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

@SuppressWarnings("serial")
public class GameGLListener extends JFrame implements GLEventListener {
	protected GLCanvas canvas;
	public static  World sWorld;
	protected long lastTime;
	private Vector<File> mTextures = new Vector<File>();
	private Vector<Integer> mTexturesID = new Vector<Integer>();
	
	private static final double Z_DIST = 7.0;      // for the camera position
	private static final float MAX_SIZE = 4.0f;  // for a model's dimension
	private GLU glu;
	private OBJModel tankMajorModel;
	private OBJModel tankEnemyModel;
	private Texture starsTex;
	private final static int FLOOR_LEN = 50;  // should be even
	private int starsDList;	
	private Camera mCamera = new Camera();

	public void start() {
		this.lastTime = System.nanoTime();
		Animator animator = new Animator(this.canvas);
		animator.setRunAsFastAsPossible(true);
		animator.start();	
	}
	
	private void loadTextures(GL2 gl)
	{
	    starsTex = TextureUtils.loadTexture("stars.jpg", gl);
	    starsTex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	    starsTex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	}  // end of loadTextures()
	
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
	// TODO Auto-generated constructor stub
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
		//initializeWorldPhysics();
	}
	
	private void initializeTexturesName() {
		mTextures.add(new File("images/background.jpg"));
		mTextures.add(new File("images/boxPlatform.jpg"));
		mTextures.add(new File("images/block1.jpg"));
		mTextures.add(new File("images/block2.jpg"));
		mTextures.add(new File("images/block3.jpg"));
		mTextures.add(new File("images/movingPlatform.jpg"));
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
		gl.glEnable(GL2.GL_TEXTURE_2D);	
		gl.glLoadIdentity();
	 }
	
	
	private void drawStars(GL2 gl)
	{
		gl.glDisable(GL2.GL_LIGHTING);
		// enable texturing and choose the 'stars' texture
		gl.glEnable(GL2.GL_TEXTURE_2D);
		starsTex.bind(gl);
		TextureCoords tc = starsTex.getImageTexCoords();
		float lf = tc.left();
		float r = tc.right();
		float b = tc.bottom();
		float t = tc.top();
		// replace the quad colors with the texture
		gl.glTexEnvf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_ENV_MODE,
		GL2.GL_REPLACE);
		gl.glBegin(GL2.GL_QUADS);
		// back wall
		int edge = FLOOR_LEN/2;
		gl.glTexCoord2f(lf, b); gl.glVertex3i(-edge, 0, -edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(edge, 0, -edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(edge, edge, -edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(-edge, edge, -edge);
		// right wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, 0, -edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(edge, 0, edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(edge, edge, edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(edge, edge, -edge);
		// front wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, 0, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, 0, edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(-edge, edge, edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(edge, edge, edge);
		// left wall
		gl.glTexCoord2f(lf, b); gl.glVertex3i(-edge, 0, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, 0, -edge);
		gl.glTexCoord2f(2*r, t); gl.glVertex3i(-edge, edge, -edge);
		gl.glTexCoord2f(lf, t); gl.glVertex3i(-edge, edge, edge);
		// ceiling
		gl.glTexCoord2f(lf, b); gl.glVertex3i(edge, edge, edge);
		gl.glTexCoord2f(2*r, b); gl.glVertex3i(-edge, edge, edge);
		gl.glTexCoord2f(2*r, 2*t); gl.glVertex3i(-edge, edge, -edge);
		gl.glTexCoord2f(lf, 2*t); gl.glVertex3i(edge, edge, -edge);
		gl.glEnd();
		// switch back to modulation of quad colors and texture
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE,
		GL2.GL_MODULATE);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_LIGHTING);
	} // end of drawStars()
	
	@Override
	public void init(GLAutoDrawable drawable) {
		
		final GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		loadTextures(gl);		
		initializeTexturesName();
		initTexture(gl);
		
		gl.setSwapInterval(0);   
		   /* switches off vertical synchronization, for extra speed (maybe) */
		
		// initialize the rotation variables
		gl.glClearColor(1f, 1f, 1f, 1.0f);  
		              // sky colour background for GLCanvas
		
		// z- (depth) buffer initialization for hidden surface removal
		gl.glEnable(GL2.GL_DEPTH_TEST);
		
		gl.glShadeModel(GL2.GL_SMOOTH);    // use smooth shading
		
		addLight(gl);
		
		
		// load the OBJ model
		tankMajorModel = new OBJModel("tankMajor", MAX_SIZE, gl, true);
		tankEnemyModel = new OBJModel("tankEnemy", MAX_SIZE, gl, true);
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
		gl.glEnable(GL2.GL_BLEND);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE); //importante
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA); //importante
		gl.glBindTexture(GL2.GL_TEXTURE_2D, mTexturesID.get(0));	
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
		gl.glPopMatrix();
	}
	@Override
	public void display(GLAutoDrawable drawable) {// the model is rotated and rendered

	    // update the rotations (if rotations were specified)
	    final GL2 gl = drawable.getGL().getGL2();

	    // clear colour and depth buffers
	    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	    glu.gluLookAt(0,0, Z_DIST, 0,0,0, 0, 1, 0);   // position camera
	    
	   // includeMechanisms3DWorld(gl);
	    drawBackground(gl);
	    mCamera.update(glu, gl);
	    // apply rotations to the x,y,z axes
	    
	    starsDList = gl.glGenLists(1);
	    gl.glNewList(starsDList, GL2.GL_COMPILE);
	      drawStars(gl);
	    gl.glEndList();
	    gl.glCallList(starsDList);
	    
	    gl.glTranslated(0, 1.5, 0);
	    tankMajorModel.draw(gl);  // draw the model
	    //gl.glTranslated(5, 0, 0);
	    //tankEnemyModel.draw(gl);
	    gl.glFlush();
	} // end of display


	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
 
 } // end of ModelLoaderGLListener class

