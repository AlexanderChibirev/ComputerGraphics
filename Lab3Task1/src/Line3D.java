import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Line3D {
	private Vector<SVertexP3N> mVertices;
	private IntBuffer mIndicies;

	
	Line3D(float startX, float endX, float step) {
		tesselate(startX, endX, step);
	}
	
	private void tesselate(float startX, float endX, float step) {
		mVertices.clear();
		
		int steps = (int)(Math.abs(endX - startX) / step);
		for (int i = 0; i <= steps; ++i)
		{
			SVertexP3N vertex = new SVertexP3N();
			vertex.position = new Vector3f(startX + i * step, 0.f, 0.f );
			vertex.normal = new Vector3f(0.f, 0.f, 1.f);
			mVertices.add(vertex);
			mIndicies.put(i, i);
		
			vertex.position = new Vector3f(startX + i * step, 0.1f, 0.f);
			vertex.normal =  new Vector3f(0.f, 0.f, 1.f);
			mVertices.add(vertex);
			mIndicies.put(i, i);
		}
	}
	private void drawElements(GL2 gl) {

        gl.glDrawElements(GL2.GL_TRIANGLE_FAN, mIndicies.limit(), GL2.GL_INT, mIndicies);
    }
	
	public void draw(GLAutoDrawable drawable) {
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
