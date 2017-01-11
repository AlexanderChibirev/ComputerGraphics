import java.util.Vector;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class BlockUndestroyable  {
	private int mSizeBlock = 3;
	float shiftForY = 0.1f;
	public static RectangularPrism sBlockUndestroyable;
	public static Vector<BodyBound> sBlockUndestroyables = new Vector<>();
	float sizeShift = 70;
	int mTypeBlock;
	
	BlockUndestroyable() {
		sBlockUndestroyable = new RectangularPrism();
		
		float x = -mSizeBlock * 5;
		float z = 0;
		int typeBlock = 0;
		for (int i = 0; i < Map.HEIGHT_MAP; i++) {
			for (int j = 0; j < Map.WIDTH_MAP; j++)
			{
				if (Map.TileMap[i].charAt(j) == 'd') {
					
					
					/*sBlockUndestroyables.get(1).height;
					sBlockUndestroyables.get(1).x;
					sBlockUndestroyables.get(1).y;
					sBlockUndestroyables.get(1).typeSprite;
					*/
					
					//gl.glTranslated(sBlockUndestroyables.get(1).x, mSizeBlock + shiftForY, sBlockUndestroyables.get(1).y);
					//sBlockUndestroyable.drawFloor(gl,
					//		GameGLListener.sTexturesID.get(sBlockUndestroyables.get(1).typeSprite),
					//		new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
					//gl.glTranslated(-sBlockUndestroyables.get(1).x, -mSizeBlock - shiftForY,  -sBlockUndestroyables.get(1).y);
					//setPosBlock(x, z, typeBlock, gl, i, j);
				} 
				if ((Map.TileMap[i].charAt(j) == 'u')) {
					typeBlock = PossitionID.BLOCK22.getValue();
					z = -i * mSizeBlock * 2;
					x = j * mSizeBlock * 2;
					//System.out.println(sBlockUndestroyables.size());
					sBlockUndestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, mSizeBlock, mSizeBlock, typeBlock));
					//z = -i * mSizeBlock * 2;
					//x = j * mSizeBlock * 2;
					//sBlockUndestroyables.addElement(new BodyBound(x - sizeShift, z + sizeShift, mSizeBlock, mSizeBlock, typeBlock));
					//typeBlock = PossitionID.BOX.getValue();
					//setPosBlock(x, z, typeBlock, gl, i, j);
				}
			}
		}
	}
	private void setPosBlock(float x, float z, int typeBlock, GL2 gl, int i, int j) {
		//z = -i * mSizeBlock * 2;
		//x = j * mSizeBlock * 2;		
		gl.glTranslated(x - sizeShift, mSizeBlock + shiftForY, z + sizeShift);
		sBlockUndestroyable.drawFloor(gl,
				GameGLListener.sTexturesID.get(typeBlock),
				new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
		gl.glTranslated(-x + sizeShift, -mSizeBlock - shiftForY, -z - sizeShift);
	}
	
	public void draw(GL2 gl) {
		//System.out.println(sBlockUndestroyables.size());
		for(BodyBound block : BlockUndestroyable.sBlockUndestroyables) {
			gl.glTranslated(block.x, mSizeBlock + shiftForY, block.y);
			sBlockUndestroyable.drawFloor(gl,
					GameGLListener.sTexturesID.get(block.typeSprite),
					new Vector3f(mSizeBlock, mSizeBlock, mSizeBlock));
			gl.glTranslated(-block.x, -mSizeBlock - shiftForY,  -block.y);
			//setPosBlock(x, z, typeBlock, gl, i, j);
		}	
	}
}
