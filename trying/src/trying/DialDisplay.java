package trying;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.gl2.GLUgl2;




public class DialDisplay implements GLEventListener {
	private float pistolY = 0;
	private boolean isPistonMovedDown = true;
	private InternalCombustionEngine internalCombustionEngine = new InternalCombustionEngine();
	private float rotationAngle = 0f;
	private float dx, dy = 0f;
	private float beginAngle = 250f;
	private boolean isSpringLeftStay = false;
	private float springUP = 0;
	private float springDOWN = 0;
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0.9f, 1, 1);
	    gl.glLoadIdentity();
	    internalCombustionEngine.drawBaseBody(gl);
	    internalCombustionEngine.drawSparkPlug(gl);
	    updatePiston(gl);
	    if(isSpringLeftStay) {
	    	internalCombustionEngine.drawValveSpring(gl, 0, -1);
	    	updateDrawValveSpring(gl, 1);
	    }
	    else {
	    	internalCombustionEngine.drawValveSpring(gl, 0, 1);
	    	updateDrawValveSpring(gl,-1);
	    }
	    updateConnectingRod(gl);
	    updateCrankshaft(gl);
	    
	}
	
	private void updateConnectingRod(GL2 gl) {
		float radius = 0.15f;
		float a = (float) (beginAngle / ConstICE.SPEED_ROD.getValue() * Math.PI * 1.4f);
        dx = (float) (Math.cos( a ) * radius);
        dy = (float) (Math.sin( a ) * radius) ;
        internalCombustionEngine.drawConnectingRod(gl, pistolY, dx, dy);
        beginAngle++;
	}


	private void updateCrankshaft(GL2 gl) {
		gl.glTranslatef(0f, -0.25f, 0);
		gl.glRotatef(rotationAngle, 0f, 0f, 1f);
		gl.glTranslatef(0f, 0.25f, 0);
		internalCombustionEngine.drawCrankshaft(gl);
		rotationAngle += ConstICE.SPEED_ROTATION_CRANKSHAGT.getValue();
	    if (rotationAngle >= 360f) {
	       rotationAngle %= 360f;
	    }
	}
	
	private void updatePiston(GL2 gl) {
		// TODO Auto-generated method stub
		if(!isPistonMovedDown) {
		    internalCombustionEngine.drawPiston(gl,  pistolY);
			pistolY += ConstICE.PISTOL_DELTA_Y.getValue();
			if(pistolY > ConstICE.UPPER_SCREEN_PISTOL_THRESHOLD.getValue()) {
				isPistonMovedDown = true;
			}
		}
		else {
			pistolY -= ConstICE.PISTOL_DELTA_Y.getValue();
			internalCombustionEngine.drawPiston(gl,pistolY);
			if(pistolY < ConstICE.LOWER_SCREEN_PISTOL_THRESHOLD.getValue()) {
				isPistonMovedDown = false;
			}
		}
	}
	
	private void updateDrawValveSpring(GL2 gl, int mirror) {
		if(springUP > -0.075 && springUP < 0.075f)
		{
			springUP -= ConstICE.SPEED_VALVESPRING.getValue();
			internalCombustionEngine.drawValveSpring(gl, springUP, mirror);
			springDOWN = springUP;
			System.out.println(springDOWN);
		}
		else if(springUP < -0.075 && springDOWN < 0.001) {
			springDOWN += ConstICE.SPEED_VALVESPRING.getValue();
			internalCombustionEngine.drawValveSpring(gl, springDOWN, mirror);
			System.out.println(springDOWN);
		}
		else {
			isSpringLeftStay = !isSpringLeftStay;
			springDOWN = 0;
			springUP = 0;
			internalCombustionEngine.drawValveSpring(gl, 0, mirror);
			internalCombustionEngine.drawValveSpring(gl, 0, mirror);
		}
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
		
	}
	
	
}