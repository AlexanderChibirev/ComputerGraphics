package ska;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


class Renderer implements GLEventListener
{
	
	public void display(GLAutoDrawable gLDrawable) {
	 	final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glColor3f(1, 2, 0);
        drawCoordinatePlane(gl);
        drawStipple(gl);
        drawBezierCurve(gl);
        gl.glFlush();
	}
	
	private void drawStipple(GL2 gl)
	{
		gl.glPointSize(4);
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex3f(-0.50f,-0.50f, 0);
		gl.glVertex3f(-0.40f,0.50f, 0);
		gl.glVertex3f(0.60f,0.40f, 0);
		gl.glVertex3f(0.50f,-0.50f, 0);
        gl.glEnd();
        gl.glEnable( GL2.GL_LINE_STIPPLE);
        gl.glLineStipple(1, (short) 0x1C47);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(-0.50f,-0.50f, 0);
		gl.glVertex3f(-0.40f,0.50f, 0);
		gl.glVertex3f(-0.40f,0.50f, 0);
		gl.glVertex3f(0.60f,0.40f, 0);
		gl.glVertex3f(0.60f,0.40f, 0);
		gl.glVertex3f(0.50f,-0.50f, 0);
        gl.glEnd(); 
        gl.glDisable( GL2.GL_LINE_STIPPLE);
	}
	
	private void drawBezierCurve(GL2 gl)
	{
		float ctrlpoints[][] = { {-0.50f,-0.50f}, {-0.40f,0.50f}, {0.60f,0.40f}, {0.50f,-0.50f} }; //кубически задали
        int k;
        int quantity = 3;
        int coefficients[] = new int[4];
        countingOfCoefficients(quantity,coefficients);
        gl.glColor3f(1, 0, 0);
        gl.glBegin(GL2.GL_LINE_STRIP);
        double blend;
        for(double i=0; i < 1.0; i += 0.01) {
        	float x=0;
        	float y=0;
        	for(k=0; k<4; k++) {
        		blend=coefficients[k] * Math.pow(i,k) * Math.pow(1 - i,quantity - k);
        		x+= ctrlpoints[k][0] * blend;//смесь
        		y+= ctrlpoints[k][1] * blend;
        	}
        	gl.glVertex2f(x,y);
        }
        gl.glEnd();   
	}
	
	private void  countingOfCoefficients(int n, int coefficients[]) {
		int k;
		int i;
		for(k=0; k <= n; k++) {
			coefficients[k]=1;
			for(i=n; i>=k+1; i--) {
				coefficients[k]*= i;
			}
			for(i=n-k; i>=2; i--) {
				coefficients[k]/= i;
			}
		}
	}
	
	private void drawCoordinatePlane(GL2 gl) {
		drawDatumLines(gl);
		drawStrokes(gl);
		float mirrorForDatumLines = -1;
		float mirrorForPartArrows = -1;
		for (int i = 0; i < 4; ++i)	{
			if(i < 2) {
				drawArrows(gl, mirrorForDatumLines, mirrorForPartArrows);
				mirrorForDatumLines*= -1;
			}
			else {
				drawArrows(gl, mirrorForDatumLines, mirrorForPartArrows);
				mirrorForDatumLines*= -1;
				mirrorForPartArrows*= -1;
				drawArrows(gl, -1, 1);
			}
		}
	}
	
	private void drawArrows(GL2 gl, float mirrorForDatumLines, float mirrorForPartArrows) {
		 gl.glBegin (GL2.GL_LINES);
	     gl.glVertex3f(0,  0.99f * mirrorForPartArrows, 0);
	     gl.glVertex3f(0.015f * mirrorForDatumLines, 0.93f * mirrorForPartArrows, 0);
	     gl.glVertex3f(0.99f * mirrorForPartArrows, 0 , 0);
	     gl.glVertex3f(0.93f * mirrorForPartArrows, 0.015f * mirrorForDatumLines, 0);
	     gl.glEnd();
	}
	
	private void drawDatumLines(GL2 gl){
		gl.glBegin (GL2.GL_LINES);
        gl.glVertex3f(0.0f, 0.99f, 0); 
        gl.glVertex3f(0, -0.99f ,0);
        gl.glVertex3f(0.99f, 0, 0);
        gl.glVertex3f(-0.99f, 0, 0);
        gl.glEnd(); 
	}
	
	private void drawStrokes(GL2 gl){
		gl.glBegin (GL2.GL_LINES);
        for (float unit = 0.9f; unit > -1.f; unit -= 0.1f) {
	        gl.glVertex3f(unit, 0.01f, 0);
	        gl.glVertex3f(unit, -0.01f ,0);
	        gl.glVertex3f(0.01f, unit, 0);
	        gl.glVertex3f(-0.01f, unit ,0);	        
        }
        gl.glEnd();	
	}
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void init(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}
}