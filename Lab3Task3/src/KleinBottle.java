import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

final class FunctionSurfaces{

    static Vector3f getPosition(final Function<Vector3f, Float> fn, float x, float z){

        return fn.eval(x, z);
    }

    static void calculateNormals(Vector<SVertexP3N> vertice, final Function<Vector3f, Float> fn, float step){

        for (SVertexP3N vertices : vertice) {
        final Vector3f position = vertices.position;
        Vector3f dir1 = getPosition(fn, position.x, position.z + step);
        dir1.sub(position);
        Vector3f dir2 = getPosition(fn, position.x + step, position.z);
        dir2.sub(position);

        vertices.normal = MathVector3f.normalize(MathVector3f.cross(dir1, dir2));
        }
    }
    
    static void calculateNormal(SVertexP3N vertice, final Function<Vector3f, Float> fn, float step){

        final Vector3f position = vertice.position;
        Vector3f dir1 = getPosition(fn, position.x, position.z + step);
        dir1.sub(position);
        Vector3f dir2 = getPosition(fn, position.x + step, position.z);
        dir2.sub(position);

        vertice.normal = MathVector3f.normalize(MathVector3f.cross(dir1, dir2));
    }

    static void doWithBindedArrays(Vector<Vector2f> vertices, GLAutoDrawable drawable, Callable callable){

        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

        FloatBuffer normals = fillNormalsArray(vertices);
        normals.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, normals);

        try {
            callable.call();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }

    private static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }

        return positions;
    }

    private static FloatBuffer fillNormalsArray(Vector<Vector2f> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3 + 1);
        for (Vector2f vertice : vertices) {

            normals.put(vertice.x);
            normals.put(vertice.y);
        }
        return normals;
    }
}

class SolidMoebiusStrips {

    private Vector<Vector2f> mVertices = new Vector<Vector2f>();
    private IntBuffer mIndicies;
    int mdrawMode = GL2.GL_FILL;

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

    void tesselate(final int slices, final int stacks){
        mIndicies = BufferUtil.newIntBuffer(slices * stacks * 2);
        mVertices.clear();
        for (int ci = 0; ci < slices ; ++ci)
    	{
    		final float u = ((float)(ci) / (float)(slices - 1));
    		for (int ri = 0; ri < stacks; ++ri)
    		{
    			final float v = ((float)(ri) / (float)(stacks - 1));

    			//glm::vec2 vertex;
    			//vertex = { u, v };
    			//m_vertices.push_back(vertex);
    		}
    	}
        calculateTriangleStripIndicies(slices, stacks);
    }

    private void drawElements(GL2 gl){

        gl.glDrawElements(GL2.GL_TRIANGLE_STRIP, mIndicies.limit(), GL2.GL_UNSIGNED_INT, mIndicies);
    }

    void draw(GLAutoDrawable drawable){
        final GL2 gl = drawable.getGL().getGL2();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        FunctionSurfaces.doWithBindedArrays(mVertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });
    }
}

