import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class PositionLightSource extends AbstractLightSource {
	private float[] mDirection;
	
	PositionLightSource(int index) {
		super(index);
	}	
	
	public Vector3f getDirection() {
		return new Vector3f(mDirection[0], mDirection[1], mDirection[2]);
	}
	
	@Override
	public void setup(GL2 gl) {
		setupImpl(gl);
		gl.glLightfv(getIndex(), GL2.GL_POSITION, mDirection, 0);
	}
}
