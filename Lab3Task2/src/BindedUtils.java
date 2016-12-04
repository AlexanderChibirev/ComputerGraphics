import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BindedUtils {
	public static void doWithBindedArrays(Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable){

        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        FloatBuffer positions = fillPositionsArray(vertices);
        FloatBuffer normals = fillNormalsArray(vertices);

        normals.rewind();
        positions.rewind();

        gl.glNormalPointer(GL2.GL_FLOAT, 0, normals);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, positions);

        try {
            callable.call();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);

    }

	public static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }

        return positions;
    }

	public static FloatBuffer fillNormalsArray(Vector<SVertexP3N> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3 + 1);

        for (SVertexP3N vertice : vertices) {

            normals.put(vertice.normal.x);
            normals.put(vertice.normal.y);
            normals.put(vertice.normal.z);
        }

        return normals;
    }
}
