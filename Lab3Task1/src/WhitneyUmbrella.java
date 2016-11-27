import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class WhitneyUmbrella {
	private Vector<SVertexP3N> mVertices;
	private IntBuffer mIndicies;

	private final int MIN_PRECISION = 4;
	private final float UV_DELTA = 0.05f;

	WhitneyUmbrella(int slices, int stacks) {
		tesselate(slices, stacks);
	}
	
	private void tesselate(int slices, int stacks) {
		assert((slices >= MIN_PRECISION) && (stacks >= MIN_PRECISION));		
		 for (int ci = 0; ci < slices; ++ci)
		    {
		        final float u = ((float)(ci) / (float)(slices - 1));
		        for (int ri = 0; ri < stacks; ++ri)
		        {
		        	final float v = ((float)(ri) / (float)(stacks - 1));
		            SVertexP3N vertex = new SVertexP3N();
		            vertex.position = BindedUtils.GetSurfacePoint(u, v);
		            
		            Vector3f dir1 = VerticeVec.difference(BindedUtils.GetSurfacePoint(u + UV_DELTA, v), vertex.position);
		            Vector3f dir2 = VerticeVec.difference(BindedUtils.GetSurfacePoint(u, v + UV_DELTA), vertex.position);
		            vertex.normal = VerticeVec.normalize(VerticeVec.cross(dir1, dir2));
		            
		            mVertices.add(vertex);
		        }
		    }
		 BindedUtils.calculateTriangleStripIndicies(mIndicies, slices, stacks);
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
