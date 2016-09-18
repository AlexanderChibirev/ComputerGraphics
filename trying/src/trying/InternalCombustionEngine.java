package trying;

import com.jogamp.opengl.GL2;

public class InternalCombustionEngine {
	public void drawSparkPlug(GL2 gl) {
		gl.glColor3f(ConstColors.YELLOW.R(),ConstColors.YELLOW.G(),ConstColors.YELLOW.B());
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
		gl.glColor3f(ConstColors.BROWN.R(), ConstColors.BROWN.G(),ConstColors.BROWN.B());
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
		drawCircle(gl, 0.01f,  0, 0.67f, ConstColors.GRAY);
	}
	
	private void drawCircle(GL2 gl, float radius, float x, float y, ConstColors color) {
		gl.glColor3f(color.R(), color.G(), color.B());
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
	
	public void drawCrankshaft(GL2 gl) {
		drawCircle(gl, 0.15f,  0f, -0.25f,  ConstColors.DARK_GRAY);
		drawCircle(gl, 0.05f,  0f,-0.25f, ConstColors.BLACK);
		gl.glBegin(GL2.GL_QUAD_STRIP);
			gl.glVertex3f( -0.05f, -0.25f, 0); //нижн€€ слева
			gl.glVertex3f(  0.05f, -0.25f, 0);//нижн€€ справа
			gl.glVertex3f(  0.05f, -0.07f, 0);//верхн€€ слева
			gl.glVertex3f(  0.1f, -0.10f, 0);//верхн€€ справа
		gl.glEnd();
	}
		
	public void drawConnectingRod(GL2 gl) {
		drawCircle(gl, 0.06f,   0.07f, -0.10f, ConstColors.BLACK );
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.07f, -0.10f,0);
			gl.glVertex3f(0, 0.25f, 0);
		gl.glEnd();
	}
	
	public void drawPiston(GL2 gl) {
		gl.glColor3f(ConstColors.GRAY.R(), ConstColors.DARK_GRAY.G(), ConstColors.DARK_GRAY.B());
		gl.glBegin(GL2.GL_QUAD_STRIP);
			gl.glVertex3f(-0.14f, 0.15f, 0);
			gl.glVertex3f(-0.14f, 0.35f, 0);
			gl.glVertex3f(0.14f, 0.15f, 0);
			gl.glVertex3f(0.14f, 0.35f, 0);
		gl.glEnd();
		drawCircle(gl, 0.04f,  0, 0.25f, ConstColors.BLACK);
	}
	
	public void  drawValveSpringL(GL2 gl) {
		gl.glColor3f(ConstColors.BLACK.R(), ConstColors.BLACK.G(), ConstColors.BLACK.B());
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0.08f, 0.675f, 0);
			gl.glVertex3f(0.08f, 0.475f, 0);
			gl.glVertex3f(0.129f, 0.475f, 0);
			gl.glVertex3f(0.03f, 0.475f, 0);
		gl.glEnd();
	}	
	
	public void  drawValveSpringR(GL2 gl) {
		gl.glColor3f(ConstColors.BLACK.R(), ConstColors.BLACK.G(), ConstColors.BLACK.B());
		gl.glLineWidth(10);
		gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(-0.08f, 0.60f, 0);
			gl.glVertex3f(-0.08f, 0.40f, 0);
			gl.glVertex3f(-0.129f, 0.40f, 0);
			gl.glVertex3f(-0.03f, 0.40f, 0);
		gl.glEnd();
	}	
	
	public void  drawBaseBody(GL2 gl) {
		gl.glColor3f(ConstColors.GRAY.R(), ConstColors.GRAY.G(), ConstColors.GRAY.B());
		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex3f(-0.15f, 0, 0);
			gl.glVertex3f(0.15f, 0, 0);
			gl.glVertex3f(0.25f, -0.15f, 0);
			gl.glVertex3f(0.25f, -0.50f, 0);
			gl.glVertex3f(-0.25f,-0.50f, 0);
			gl.glVertex3f(-0.25f,-0.15f,0);
		gl.glEnd();
		gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex3f(-0.15f, 0,0);
			gl.glVertex3f(-0.15f, 0.50f,0);
			gl.glVertex3f( 0.15f, 0.50f,0);
			gl.glVertex3f( 0.15f, 0, 0);
		gl.glEnd();
		gl.glColor3f(ConstColors.DARK_GRAY.R(), ConstColors.DARK_GRAY.G(), ConstColors.DARK_GRAY.B());
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
}
