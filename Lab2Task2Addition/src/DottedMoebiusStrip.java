import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

final class FunctionSurface{

    static Vector3f getPosition(final Function<Vector3f, Float> fn, float x, float z){

        return fn.eval(x, z);
    }

    static void calculateNormals(Vector<SVertexP3N> vertices, final Function<Vector3f, Float> fn, float step){

        for (SVertexP3N vertice : vertices){

            final Vector3f position = vertice.position;
            Vector3f dir1 = getPosition(fn, position.x, position.z + step);
            dir1.sub(position);
            Vector3f dir2 = getPosition(fn, position.x + step, position.z);
            dir2.sub(position);

            vertice.normal = MathVector3f.normalize(MathVector3f.cross(dir1, dir2));
        }
    }

    static void doWithBindedArrays(final Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable){

        GL2 gl = drawable.getGL().getGL2();

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

        // Выполняем привязку vertex array и normal array
        //final int stride = 24;

        FloatBuffer normals = fillNormalsArray(vertices);
        FloatBuffer positions = fillPositionsArray(vertices);

        normals.flip();
        positions.flip();

        gl.glNormalPointer(GL2.GL_FLOAT, 0, normals);
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

    private static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }

        return positions;
    }

    private static FloatBuffer fillNormalsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            normals.put(vertice.normal.x);
            normals.put(vertice.normal.y);
            normals.put(vertice.normal.z);
        }

        return normals;
    }
}

class DottedMoebiusStrip {

    private Vector<SVertexP3N> vertices;
    private float DOT_SIZE = 5.f;
    private Function<Vector3f, Float> fn;

    DottedMoebiusStrip(final Function<Vector3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step){

        vertices.clear();

        for (float x = rangeX.x; x < rangeX.y; x += step){
            for (float z = rangeZ.x; z < rangeZ.y; z += step){

                vertices.add(new SVertexP3N(FunctionSurfaces.getPosition(fn, x, z)));
            }
        }

        FunctionSurfaces.calculateNormals(vertices, fn, step);
    }

    private void drawArrays(GL2 gl){

        gl.glDrawArrays(GL2.GL_POINTS, 0, vertices.size());
    }

    void draw(GLAutoDrawable drawable){

        final GL2 gl = drawable.getGL().getGL2();

        gl.glPointSize(DOT_SIZE);

        FunctionSurfaces.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {

                drawArrays(gl);
                return null;
            }
        });
    }
}

class SolidMoebiusStrip {

    private Vector<SVertexP3N> vertices;
    private Function<Vector3f, Float> fn;
    private IntBuffer indiciesBuffer;

    SolidMoebiusStrip(final Function<Vector3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step){

        final int columnCount = (int)((rangeX.y - rangeX.x) / step);
        final int rowCount = (int)((rangeZ.y - rangeZ.x) / step);

        indiciesBuffer = BufferUtil.newIntBuffer(3120);
        vertices.clear();

        for (int ci = 0; ci < columnCount; ci++) {
            final float x = rangeX.x + step * (float)ci;
            for (int ri = 0; ri < rowCount; ri++) {
                final float z = rangeZ.x + step * (float)ri;
                vertices.add(new SVertexP3N(FunctionSurfaces.getPosition(fn, x, z)));
            }
        }

        FunctionSurfaces.calculateNormals(vertices, fn, step);
        calculateTriangleStripIndicies(columnCount, rowCount);
    }

    private void calculateTriangleStripIndicies(int columnCount, int rowCount) {

        indiciesBuffer.clear();
        int x = 0;

        for (int ci = 0; ci < columnCount - 1; ci++)
        {
            if (ci % 2 == 0)
            {
                for (int ri = 0; ri < rowCount; ri++)
                {
                    System.out.println("ri1 " + ri);
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index + rowCount);
                    indiciesBuffer.put(index);
                    x++;x++;
                }
            }
            else
            {
                for (int ri = rowCount - 1; ri >= 0; ri--)
                {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index);
                    indiciesBuffer.put(index + rowCount);
                    x++;x++;
                }
            }
        }

        indiciesBuffer.flip();
        System.out.println("x " + x);
    }

   private void drawElements(GL2 gl){

        gl.glDrawElements(GL2.GL_TRIANGLE_FAN, indiciesBuffer.limit(), GL2.GL_INT, indiciesBuffer);
    }


    void draw(GLAutoDrawable drawable){

        final GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL2.GL_CULL_FACE);

        gl.glCullFace(GL2.GL_BACK);

        FunctionSurfaces.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });

        gl.glCullFace(GL2.GL_FRONT);

        FunctionSurfaces.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });
    }

}

