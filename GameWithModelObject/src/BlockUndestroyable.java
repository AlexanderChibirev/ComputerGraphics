import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class BlockUndestroyable {
	private int mSizeBlock = 3;
	float shiftForY = 0.1f;
	public static RectangularPrism sBlockUndestroyable;
	public static Vector<BodyBound> sBlockUndestroyables = new Vector<>();
	float sizeShift = 70;
	int mTypeBlock;
	
	BlockUndestroyable() {
		sBlockUndestroyable = new RectangularPrism();
	}
	
	public void draw(GL2 gl) {
		for(BodyBound block : BlockUndestroyable.sBlockUndestroyables) {
			gl.glTranslated(block.x, mSizeBlock + shiftForY, block.y);
			sBlockUndestroyable.drawFloor(gl,
					GameGLListener.sTexturesID.get(block.typeSprite),
					new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
			gl.glTranslated(-block.x, -mSizeBlock - shiftForY,  -block.y);
		}
	}
}
