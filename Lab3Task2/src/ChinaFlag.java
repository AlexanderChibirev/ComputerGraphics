import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class ChinaFlag {
	private final float MAX_TEX_COORD_X = 5.f;
	private final float MAX_TEX_COORD_Y = 3.f;
	private Vector<SVertexP3N> mVertices = new  Vector<SVertexP3N>();
	private IntBuffer mIndicies;
	
	ChinaFlag(final Vector3f leftTop, final Vector3f size) {
		 SVertexP3N vLeftTop = new SVertexP3N(leftTop,  new Vector3f(0, 0, 0) );
		 
		 SVertexP3N vRightTop = new SVertexP3N(
				 new Vector3f(leftTop.x + size.x, leftTop.y, 0), 
				 new Vector3f(MAX_TEX_COORD_X, 0, 0) );
		 
		 SVertexP3N vLeftBottom  =new SVertexP3N (
				 new Vector3f(leftTop.x, leftTop.y +  size.y, 0),
				 new Vector3f(0, MAX_TEX_COORD_Y, 0));
		
		 SVertexP3N vRightBottom = new SVertexP3N(
				 new Vector3f(leftTop.x +  size.x, leftTop.y +  size.y, 0), 
				 new Vector3f(MAX_TEX_COORD_X, MAX_TEX_COORD_Y, 0));
		
		mVertices.add( vLeftTop);
		mVertices.add( vRightTop);
		mVertices.add( vLeftBottom);
		mVertices.add( vRightBottom);
		
		mIndicies = BufferUtil.newIntBuffer(6);// q element
		mIndicies.put(0);
		mIndicies.put(1);
		mIndicies.put(2);
		mIndicies.put(1);
		mIndicies.put(3);
		mIndicies.put(2);
		mIndicies.rewind();
	}
	
	private void drawElements(GL2 gl) {
		 gl.glDrawElements(GL2.GL_TRIANGLES, mIndicies.limit(), GL2.GL_UNSIGNED_INT, mIndicies);
    }
	
	public final void  draw(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		BindedUtils.doWithBindedArrays(mVertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
            	drawElements(gl);
                return null;
            }
        });
	}
}
