import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;

public class Quadrangle {
	static final float MAX_TEX_COORD = 4.f;
	private Vector<SVertexP2T2> mVertices = new  Vector<SVertexP2T2>();
	private float[] mIndicies = { 0, 1, 2, 1, 3, 2 };
	
	Quadrangle(final Vector2f leftTop, final Vector2f size) {
		 SVertexP2T2 vLeftTop = new SVertexP2T2(leftTop,  new Vector2f(0, 0) );
		 
		 SVertexP2T2 vRightTop = new SVertexP2T2(new Vector2f(leftTop.x + size.x, leftTop.y), 
				 new Vector2f(MAX_TEX_COORD, 0) );
		 
		 SVertexP2T2 vLeftBottom  =new SVertexP2T2 (new Vector2f(leftTop.x, leftTop.x +  size.y),
				 new Vector2f(0, MAX_TEX_COORD));
		
		 SVertexP2T2 vRightBottom = new SVertexP2T2(new Vector2f(leftTop.x +  size.x, leftTop.x +  size.y), 
				 new Vector2f(MAX_TEX_COORD, MAX_TEX_COORD));
		
		mVertices.add( vLeftTop);
		mVertices.add( vRightTop);
		mVertices.add( vLeftBottom);
		mVertices.add( vRightBottom);
	}
	
	private FloatBuffer toFloatBuffer(float[] buffer) {
		FloatBuffer floatBuffer;
		ByteBuffer buf= ByteBuffer.allocateDirect(6 * 4);
		buf.order(ByteOrder.nativeOrder());
		floatBuffer = buf.asFloatBuffer();
		floatBuffer.put(buffer);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	private FloatBuffer toFloatBuffer(Vector2f vec) {
		float[] buffer  = {vec.x, vec.y};
		FloatBuffer floatBuffer;
		ByteBuffer buf= ByteBuffer.allocateDirect(4 * 4);
		buf.order(ByteOrder.nativeOrder());
		floatBuffer = buf.asFloatBuffer();
		floatBuffer.put(buffer);
		floatBuffer.position(0);
		return floatBuffer;
	}
	
	public final void  draw(GL2 gl) {
		//DoWithBindedArrays
		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

	    // Выполняем привязку vertex array и normal array
	    final int stride = 32;
	    gl.glVertexPointer(2, GL2.GL_FLOAT, stride, toFloatBuffer(mVertices.get(0).getPosition()));
	    gl.glTexCoordPointer(2, GL2.GL_FLOAT, stride, toFloatBuffer(mVertices.get(0).getTexCoord()));
	    //Рисуем
	    gl.glDrawElements(GL2.GL_TRIANGLES, 6, GL2.GL_UNSIGNED_BYTE, toFloatBuffer(mIndicies));
	    // Выключаем режим vertex array и normal array.
	    gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	    
	    
	    
	  /*  for (Integer it : mIndicies) {
	    	SVertexP2T2 v = mVertices.get(it);
			gl.glColor3f(0.1f, 0.85f, 1);
			gl.glVertex3f(v.getPosition().x, v.getPosition().y, v.getPosition().z);
			gl.glNormal3f(v.getNormal().x, v.getNormal().y, v.getNormal().z);
		}*/
	}
}
