

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class GLBall extends BodyBound {
	private Direction directionBullet;
	public GLBall(float x, float y, int width, int height) {
		super(x, y, width, height);
		directionBullet = Player.direction;
		// TODO Auto-generated constructor stub
	}
	public void render(GL2 gl, GLU glu) {
		gl.glPushMatrix();
		System.out.print("Ball x: ");
		System.out.println(x);
		System.out.print("Ball y: ");
		System.out.println(y);
		gl.glTranslated(x, 1.2f, y);
		
		switch (Player.direction) {
			case UP:	
				y += 0.01;
				break;
			case DOWN:	
				y -= 0.01;
				break;
			case LEFT: 
				x -= 0.01;
				break;
			case RIGHT: 
				x += 0.01;
				break;		 
		}
		
		GLUquadric quad = glu.gluNewQuadric();
		double radius = 0.1; 
		int slices = 10;
		int stacks = 10;
		glu.gluSphere(quad, radius, slices, stacks);
		glu.gluDeleteQuadric(quad);
		gl.glPopMatrix();
		if(x == 75 || x == -75 || y == 75 || y == -75) {
			
		}
	}
}
	
