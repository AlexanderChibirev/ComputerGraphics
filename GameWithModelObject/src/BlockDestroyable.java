import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class BlockDestroyable {

	private float mSizeBlock = 4.0f;
	private int mQantityBlock = 50;
	float shiftForY = 0.1f;
	public static Vector<RectangularPrism> sBlockDestroyable = new Vector<>();

	BlockDestroyable() {
		for(int i = 0; i < mQantityBlock; i++) {
			sBlockDestroyable.add(new RectangularPrism());
		}		
	}
	
	
	public void draw(GL2 gl) {
		/*float x = -mSizeBlock * 19;
		float z = 0;
		float shiftForX = mSizeBlock * 2;
		float shiftForZ = mSizeBlock * 2;
		for(int i = 0; i < mQantityBlock; i++) {
			if(i < 7) {		
				x += shiftForX;
			}
			gl.glTranslated(x, mSizeBlock + shiftForY, z);
			sBlockDestroyable.get(i).drawFloor(gl,
					GameGLListener.sTexturesID.get(PossitionID.BOX.getValue()),
					new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
			
			gl.glTranslated(-x, -mSizeBlock - shiftForY, -z);
		}*/
	}	
}
