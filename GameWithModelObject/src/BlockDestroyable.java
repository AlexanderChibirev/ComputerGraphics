import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class BlockDestroyable {
	private int mSizeBlock = 3;
	float shiftForY = 0.1f;
	public static RectangularPrism sBlockDestroyable;
	public static Vector<BodyBound> sBlockDestroyables = new Vector<>();
	float sizeShift = 70;
	int mTypeBlock;
	
	BlockDestroyable() {
		sBlockDestroyable = new RectangularPrism();
	}
	
	public void draw(GL2 gl) {
		for(BodyBound block : BlockDestroyable.sBlockDestroyables) {
			gl.glTranslated(block.x, mSizeBlock + shiftForY, block.y);
			sBlockDestroyable.drawFloor(gl,
					GameGLListener.sTexturesID.get(block.typeSprite),
					new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
			gl.glTranslated(-block.x, -mSizeBlock - shiftForY,  -block.y);
		}
	}
}
