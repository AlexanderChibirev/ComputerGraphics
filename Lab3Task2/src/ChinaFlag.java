import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;

import javax.vecmath.Vector2f;

class ChinaFlag {

	private static final float MAX_TEX_COORD = 4.f;

	private Vector<SVertexP2T2> mVertices;
	private IntBuffer mIndicies;

	ChinaFlag(Vector2f leftTop, Vector2f size) {

		SVertexP2T2 vLeftTop = new SVertexP2T2(leftTop, new Vector2f(0, 0));
		SVertexP2T2 vRightTop = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(size.x, 0.f)), new Vector2f(MAX_TEX_COORD, 0));
		SVertexP2T2 vLeftBottom = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(0.f, size.y)), new Vector2f(0, MAX_TEX_COORD));
		SVertexP2T2 vRightBottom = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(size.x, size.y)), new Vector2f(MAX_TEX_COORD, MAX_TEX_COORD));

		mVertices = new Vector<>(Arrays.asList(vLeftTop, vRightTop, vLeftBottom, vRightBottom));
		mIndicies = BufferUtil.newIntBuffer(6);
		int[] indArray = {0, 1, 2, 1, 3, 2};
		mIndicies.put(indArray);
		mIndicies.rewind();
	}
	
	private void drawElements(GL2 gl) {

		gl.glDrawElements(GL2.GL_TRIANGLES, mIndicies.limit(), GL2.GL_UNSIGNED_INT, mIndicies);
	}

	void draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		BindedUtils.doWithBindedArrays(mVertices, drawable, () -> {
			drawElements(gl);
			return null;
		});
	}
}