import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class BindedUtils {
	static void doWithBindedArrays(Vector<SVertexP2T2> vertices, GLAutoDrawable drawable, Callable callable){

		GL2 gl = drawable.getGL().getGL2();

		gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);

		FloatBuffer positions = fillPositionsBuffer(vertices);
		FloatBuffer textures = fillTexturesBuffer(vertices);

		positions.rewind();
		textures.rewind();

		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 0, textures);
		gl.glVertexPointer(2, GL2.GL_FLOAT, 0, positions);

		try {
			callable.call();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		gl.glDisableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
	}

	private static FloatBuffer fillPositionsBuffer(final Vector<SVertexP2T2> vertices){

		FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 2);

		for (SVertexP2T2 vertice : vertices) {

			positions.put(vertice.position.x);
			positions.put(vertice.position.y);
		}

		return positions;
	}

	private static FloatBuffer fillTexturesBuffer(final Vector<SVertexP2T2> vertices){

		FloatBuffer texCoords = BufferUtil.newFloatBuffer(vertices.size() * 2);

		for (SVertexP2T2 vertice : vertices) {

			texCoords.put(vertice.texCoord.x);
			texCoords.put(vertice.texCoord.y);
		}
		return texCoords;
	}
}
