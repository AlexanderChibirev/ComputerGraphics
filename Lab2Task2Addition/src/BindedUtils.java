import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BindedUtils {
	public static void doWithBindedArrays(final Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        FloatBuffer normals = fillNormalsArray(vertices);
        FloatBuffer positions = fillPositionsArray(vertices);

        normals.flip();
        positions.flip();

        gl.glNormalPointer(GL2.GL_FLOAT, 0, normals);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, positions);

        try {
            callable.call();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Выключаем режим vertex array и normal array.
        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);

    }
	
	public static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices) {

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }
        return positions;
    }

	public static FloatBuffer fillNormalsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            normals.put(vertice.normal.x);
            normals.put(vertice.normal.y);
            normals.put(vertice.normal.z);
        }
        return normals;
    }
	
	public static Vector3f GetSurfacePoint(float u, float v)
	{
	    // Приводим параметры из диапазона [0..1] к диапазону [-3..3]
	    u = 6.f * (u - 0.5f);
	    v = 6.f * (v - 0.5f);
	    return new Vector3f( u * v, u, v * v );
	}

	public static void calculateTriangleStripIndicies(IntBuffer indiciesBuffer, int columnCount, int rowCount) {
		indiciesBuffer.clear();
        // вычисляем индексы вершин.
        for (int ci = 0; ci < columnCount - 1; ci++) {
            if (ci % 2 == 0) {
                for (int ri = 0; ri < rowCount; ri++) {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index + rowCount);
                    indiciesBuffer.put(index);
                }
            }
            else {
                for (int ri = rowCount - 1; ri >= 0; ri--) {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index);
                    indiciesBuffer.put(index + rowCount);
                }
            }
        }
        indiciesBuffer.flip();
	}
}
