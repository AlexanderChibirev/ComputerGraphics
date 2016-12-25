import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector2f;

class SolidMoebiusStrips {

    private Vector<Vector2f> mVertices = new Vector<Vector2f>();
    private IntBuffer mIndicies;

    private void calculateTriangleStripIndicies(int columnCount, int rowCount) {

    	mIndicies.clear();
        for (int ci = 0; ci < columnCount - 1; ci++)
        {
            if (ci % 2 == 0)
            {
                for (int ri = 0; ri < rowCount; ri++)
                {
                    int index = ci * rowCount + ri;
                    mIndicies.put(index + rowCount);
                    mIndicies.put(index);
                }
            }
            else
            {
            	 for (int ri = rowCount - 1; ri >= 0; ri--)
                 {
                     int index = ci * rowCount + ri;
                     mIndicies.put(index);
                     mIndicies.put(index + rowCount);
                 }
            }
        }
        mIndicies.rewind();
    }

    void tesselate(final int slices, final int stacks) {
        mIndicies = BufferUtil.newIntBuffer(slices * stacks * 2);
        mVertices.clear();
        for (int ci = 0; ci < slices ; ++ci)
    	{
    		final float u = ((float)(ci) / (float)(slices - 1));
    		for (int ri = 0; ri < stacks; ++ri)
    		{
    			final float v = ((float)(ri) / (float)(stacks - 1));
    			final Vector2f vertex = new Vector2f(u, v );
    			mVertices.add(vertex);
    		}
    	}
        calculateTriangleStripIndicies(slices, stacks);
    }

  
	void draw(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        doWithBindedArrays(mVertices, drawable, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
            	gl.glDrawElements(GL2.GL_TRIANGLE_STRIP, mIndicies.limit(),
            		GL2.GL_UNSIGNED_INT, mIndicies);
                return null;
            }
        });
    }

	private void doWithBindedArrays(Vector<Vector2f> vertices, GLAutoDrawable drawable, Callable<?> callable) {
			GL2 gl = drawable.getGL().getGL2();
	        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

	        // Выполняем привязку vertex array и normal array
	        //final int stride = 24;

	        FloatBuffer normals = fillNormalsArray(vertices);
	        FloatBuffer positions = fillPositionsArray(vertices);
	        
	        normals.flip();
	        positions.flip();

	        gl.glNormalPointer(GL2.GL_FLOAT, 24, normals);
	        gl.glVertexPointer(2, GL2.GL_FLOAT, 0, positions);

	        try {
	            callable.call();
	        }
	        catch (Exception e){
	            e.printStackTrace();
	        }

	        // Выключаем режим vertex array и normal array.
	        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	}
	
	private static FloatBuffer fillPositionsArray(final Vector<Vector2f> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 2);

        for (Vector2f vertice : vertices) {

            positions.put(vertice.x);
            positions.put(vertice.y);
        }

        return positions;
    }

	
    private FloatBuffer fillNormalsArray(Vector<Vector2f> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 2);
        for (Vector2f vertice : vertices) {
            normals.put(vertice.x);
            normals.put(vertice.y);
        }
        return normals;
    }
}

