import java.util.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import OBJLoader.OBJModel;

import java.text.DecimalFormat;


public class DialDisplay implements GLEventListener 
{
  private static final double Z_DIST = 7.0;      // for the camera position
  private static final float MAX_SIZE = 4.0f;  // for a model's dimension
  private GLU glu;
  private OBJModel tankMajorModel;
  private OBJModel tankEnemyModel;
  // rotation variables
  public DialDisplay() { 
	
  } 
  
  @Override
  public void init(GLAutoDrawable drawable)   // perform start-up tasks
  {
	final GL2 gl = drawable.getGL().getGL2();
    glu = new GLU();

    // gl.setSwapInterval(0);   
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


  private void addLight(GL2 gl)
  // two white light sources 
  {
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
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
  // called when the drawable component is moved or resized
  {
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
  public void display(GLAutoDrawable drawable) 
  // the model is rotated and rendered
  {
    // update the rotations (if rotations were specified)
    final GL2 gl = drawable.getGL().getGL2();

    // clear colour and depth buffers
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    glu.gluLookAt(0,0, Z_DIST, 0,0,0, 0, 1, 0);   // position camera
    // apply rotations to the x,y,z axes
    tankMajorModel.draw(gl);  // draw the model
    tankEnemyModel.draw(gl);
    gl.glFlush();
  } // end of display


  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, 
                             boolean deviceChanged) 
  /* Called when the display mode or device has changed.
     Currently unimplemented in JOGL */
  {}


	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
 
 } // end of ModelLoaderGLListener class

