import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

import Utils.LoaderTextures;

public class BlockDestroyable {
	private int sizeBlock = 3;
	float shiftForY = 0.1f;
	float sizeShift = 70;
	int typeBlock;
	
	public void draw(GL2 gl) {
		for(BodyBound block : Entity.sBlockDestroyables) {
			gl.glTranslated(block.x, sizeBlock + shiftForY, block.y);
			DrawRects.drawFloor(gl,
					LoaderTextures.sTexturesID.get(block.textureId),
					new Vector3f(sizeBlock, sizeBlock, sizeBlock));
			gl.glTranslated(-block.x, -sizeBlock - shiftForY,  -block.y);
		}
	}
}
