package ska;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.DebugGL3;
import com.jogamp.opengl.DebugGL3bc;
import com.jogamp.opengl.DebugGL4;
import com.jogamp.opengl.DebugGL4bc;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3bc;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLES1;
import com.jogamp.opengl.GLES2;
import com.jogamp.opengl.GLES3;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;


public class InternalCombustionEngine implements GLEventListener {
	private GLU glu = new GLU();
	private float pistolY = 0;
	private float pistolDeltaY = .0001f;
	GLCanvas glCanvas;

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0.9f, 1, 1);
	    gl.glLoadIdentity();
		drawBaseBody(gl);
		drawValveSpringL(gl);
		drawValveSpringR(gl);
		drawSparkPlug(gl);
		drawCrankshaft(gl);
		drawConnectingRod(gl);
		//drawPiston(gl);
		// изменяет вид http://www.java-gaming.org/index.php?;topic=12186.0
		//update(gl,gLDrawable);
		
	}
	
	private void update(GL2 gl, GLAutoDrawable gLDrawable)
	{
		while(pistolY < 1)
		{
			//gl.glTranslatef(0, pistolY, 0);
			drawPiston(gl);
			pistolY += pistolDeltaY;
		}
	}
	
	private void drawSparkPlug(GL2 gl) {
		gl.glColor3f(1, 1, 0);
		gl.glLineWidth(3);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(-0.02f, 0.55f, 0);
			gl.glVertex3f(0.02f, 0.55f, 0);
			gl.glVertex3f(0.021f, 0.60f, 0);
			gl.glVertex3f(0.02f, 0.55f, 0);
			gl.glVertex3f(-0.021f, 0.60f, 0);
			gl.glVertex3f(-0.02f, 0.55f, 0);
			gl.glVertex3f(-0.01f, 0.67f, 0);
			gl.glVertex3f(-0.02f,  0.60f, 0);
			gl.glVertex3f(0.01f, 0.67f, 0);
			gl.glVertex3f(0.02f,  0.60f, 0);
		gl.glEnd();
		gl.glColor3f(0.84f, 0.61f, 0.04f);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0, 0.55f, 0);
			gl.glVertex3f(0, 0.45f, 0);
		gl.glEnd();
		gl.glLineWidth(5);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0, 0.55f, 0);
			gl.glVertex3f(0, 0.475f, 0);
			gl.glVertex3f(-0.029f, 0.60f, 0);//лампа
			gl.glVertex3f(0.029f, 0.60f, 0);
		gl.glEnd();
		drawCircle(gl, 0.01f,  0, 0.67f, new RGB(0.84f, 0.61f, 0.04f));
	}
	
	private void drawCircle(GL2 gl, float radius, float x, float y, RGB color) {
		gl.glColor3f(RGB.R, RGB.G, RGB.B);
		gl.glTranslatef(x,  y, 0.0f);
		gl.glBegin(GL2.GL_TRIANGLE_FAN );
		gl.glVertex2f( 0,  0 ); // вершина в центре круга
	       for(int i = 0; i <= 50; i++ ) {
	           float a = (float)i / 50.0f * 3.1415f * 2.0f;
	           gl.glVertex2d( Math.cos( a ) * radius, Math.sin( a ) * radius );
	       }
       gl.glEnd();
       gl.glTranslatef(-1 * x, -1 * y, 0);//вернуть обратно
	}
	private void drawCrankshaft(GL2 gl) {
		drawCircle(gl, 0.15f,  0f, -0.25f, new RGB(0.5f, 0.5f, 0.5f));
		drawCircle(gl, 0.05f,  0f,-0.25f, new RGB(0, 0, 0));
		gl.glBegin(GL2.GL_QUAD_STRIP);
			gl.glVertex3f( -0.05f, -0.25f, 0); //нижняя слева
			gl.glVertex3f(  0.05f, -0.25f, 0);//нижняя справа
			gl.glVertex3f(  0.05f, -0.07f, 0);//верхняя слева
			gl.glVertex3f(  0.1f, -0.10f, 0);//верхняя справа
		gl.glEnd();
	}
		
	private void drawConnectingRod(GL2 gl) {
		drawCircle(gl, 0.06f,   0.07f, -0.10f, new RGB(0, 0, 0));
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.07f, -0.10f,0);
			gl.glVertex3f(0, 0.25f, 0);
		gl.glEnd();
	}
	
	private void drawPiston(GL2 gl) {
		gl.glColor3f(0.3f, 0.3f, 0.3f);
		gl.glBegin(GL2.GL_QUAD_STRIP);
			gl.glVertex3f(-0.14f, 0.15f, 0);
			gl.glVertex3f(-0.14f, 0.35f, 0);
			gl.glVertex3f(0.14f, 0.15f, 0);
			gl.glVertex3f(0.14f, 0.35f, 0);
		gl.glEnd();
		drawCircle(gl, 0.04f,  0, 0.25f, new RGB(0, 0,0));
	}
	
	private void drawValveSpringL(GL2 gl) {
		gl.glColor3f(0, 0, 0);
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.08f, 0.675f, 0);
			gl.glVertex3f(0.08f, 0.475f, 0);
			gl.glVertex3f(0.129f, 0.475f, 0);
			gl.glVertex3f(0.03f, 0.475f, 0);
		gl.glEnd();
	}	
	
	private void drawValveSpringR(GL2 gl) {
		gl.glColor3f(0, 0, 0);
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(-0.08f, 0.60f, 0);
			gl.glVertex3f(-0.08f, 0.40f, 0);
			gl.glVertex3f(-0.129f, 0.40f, 0);
			gl.glVertex3f(-0.03f, 0.40f, 0);
		gl.glEnd();
	}	
	
	private void drawBaseBody(GL2 gl) {
		gl.glColor3f(0.84f, 0.61f, 0.04f);
		gl.glColor3f(0.85f, 0.85f, 0.85f);
		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex3f(-0.15f, 0, 0);
			gl.glVertex3f(0.15f, 0, 0);
			gl.glVertex3f(0.25f, -0.15f, 0);
			gl.glVertex3f(0.25f, -0.50f, 0);
			gl.glVertex3f(-0.25f,-0.50f, 0);
			gl.glVertex3f(-0.25f,-0.15f,0);
		gl.glEnd();
		gl.glColor3f(0.85f, 0.85f, 0.85f);
		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex3f(-0.15f, 0,0);
			gl.glVertex3f(-0.15f, 0.50f,0);
			gl.glVertex3f( 0.15f, 0.50f,0);
			gl.glVertex3f( 0.15f, 0, 0);
		gl.glEnd();
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glLineWidth(8);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(-0.15f, 0.50f, 0);
			gl.glVertex3f(-0.10f, 0.50f, 0);
			gl.glVertex3f(-0.05f, 0.50f, 0);
			gl.glVertex3f( 0.05f, 0.50f, 0);
			gl.glVertex3f( 0.10f, 0.50f, 0);
			gl.glVertex3f( 0.15f, 0.50f, 0);
			gl.glVertex3f(-0.15f, 0.50f, 0);
			gl.glVertex3f(-0.15f, 0, 0);
			gl.glVertex3f(0.15f, 0.50f, 0);
			gl.glVertex3f(0.15f, 0, 0);
			gl.glVertex3f(0.25f, -0.15f, 0);
			gl.glVertex3f(0.25f, -0.50f, 0);
			gl.glVertex3f(-0.25f,-0.50f, 0);
			gl.glVertex3f(-0.25f,-0.15f,0);
			gl.glVertex3f(0.15f, 0, 0);
			gl.glVertex3f(0.25f, -0.15f, 0);
			gl.glVertex3f(-0.15f, 0, 0);
			gl.glVertex3f(-0.25f, -0.15f, 0);
			gl.glVertex3f(0.25f,-0.50f, 0);
			gl.glVertex3f(-0.25f,-0.50f,0);
		gl.glEnd();
	}
	
	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable,  int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		if(pistolY<1){
			gl.glTranslatef(0, pistolY, 0);
			drawPiston(gl);
			pistolY += pistolDeltaY;
			System.out.println(pistolY);
			
		}
		
	}

}
