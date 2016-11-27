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

        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        FloatBuffer normals = fillArrayBuf(vertices);
        FloatBuffer positions = fillArrayBuf(vertices);

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
        gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);

    }
	
	public static FloatBuffer fillArrayBuf(final Vector<SVertexP3N> vertices) {

        FloatBuffer buf = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            buf.put(vertice.position.x);
            buf.put(vertice.position.y);
            buf.put(vertice.position.z);
        }
        return buf;
    }
}
