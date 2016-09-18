package trying;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;




public class DialDisplay implements GLEventListener {
	
	private float pistolY = 0;
	private boolean isPistonMovedDown = false;
	private InternalCombustionEngine internalCombustionEngine = new InternalCombustionEngine();
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0.9f, 1, 1);
	    gl.glLoadIdentity();
	    internalCombustionEngine.drawBaseBody(gl);
	    internalCombustionEngine.drawValveSpringL(gl);
	    internalCombustionEngine.drawValveSpringR(gl);
	    internalCombustionEngine.drawSparkPlug(gl);
	    internalCombustionEngine.drawCrankshaft(gl);
	    internalCombustionEngine.drawConnectingRod(gl);
	    updatePiston(gl);
	}
	
	private void updatePiston(GL2 gl) {
		// TODO Auto-generated method stub
		if(!isPistonMovedDown) {
			gl.glTranslatef(0, pistolY, 0);
		    internalCombustionEngine.drawPiston(gl);
			pistolY += ConstICE.PISTOL_DELTA_Y.getValue();
			if(pistolY > ConstICE.UPPER_SCREEN_PISTOL_THRESHOLD.getValue()) {
				isPistonMovedDown = true;
			}
		}
		else {
			pistolY -= ConstICE.PISTOL_DELTA_Y.getValue();
			gl.glTranslatef(0, pistolY, 0);
			internalCombustionEngine.drawPiston(gl);
			//System.out.println(pistolY);
			if(pistolY < ConstICE.LOWER_SCREEN_PISTOL_THRESHOLD.getValue()) {
				isPistonMovedDown = false;
			}
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