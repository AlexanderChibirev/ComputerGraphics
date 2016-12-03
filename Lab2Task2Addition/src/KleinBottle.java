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

    static void doWithBindedArrays(Vector<SVertexP3N> vertices, GLAutoDrawable drawable, Callable callable){

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

    private static FloatBuffer fillPositionsArray(final Vector<SVertexP3N> vertices){

        FloatBuffer positions = BufferUtil.newFloatBuffer(vertices.size() * 3);

        for (SVertexP3N vertice : vertices) {

            positions.put(vertice.position.x);
            positions.put(vertice.position.y);
            positions.put(vertice.position.z);
        }

        return positions;
    }

    private static FloatBuffer fillNormalsArray(Vector<SVertexP3N> vertices){

        FloatBuffer normals = BufferUtil.newFloatBuffer(vertices.size() * 3 + 1);

        int x = 0;
        for (SVertexP3N vertice : vertices) {

            normals.put(vertice.normal.x);
            normals.put(vertice.normal.y);
            normals.put(vertice.normal.z);
        }

        return normals;
    }
}

class DottedKleinBottle {

    private Vector<SVertexP3N> vertices;
    private float DOT_SIZE = 8.f;
    private Function<Vector3f, Float> fn;

    DottedKleinBottle(final Function<Vector3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step){

        vertices.clear();

        for (float x = rangeX.x; x < rangeX.y; x += step){
            for (float z = rangeZ.x; z < rangeZ.y; z += step){

                vertices.add(new SVertexP3N(FunctionSurfaces.getPosition(fn, x, z)));
                FunctionSurfaces.calculateNormal(vertices.lastElement(), fn, step);
            }
        }
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

class SolidMoebiusStrips {

    private Vector<SVertexP3N> vertices;
    private Function<Vector3f, Float> fn;
    private IntBuffer indiciesBuffer;
    //private FloatBuffer verticiesBuffer;

    SolidMoebiusStrips(final Function<Vector3f, Float> fn){

        vertices = new Vector<>();
        this.fn = fn;
    }

    private void calculateTriangleStripIndicies(int columnCount, int rowCount) {

        indiciesBuffer.clear();

        // вычисляем индексы вершин.
        for (int ci = 0; ci < columnCount - 1; ci++)
        {
            if (ci % 2 == 0)
            {
                for (int ri = 0; ri < rowCount; ri++)
                {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index + rowCount);
                    indiciesBuffer.put(index);
                }
            }
            else
            {
                for (int ri = rowCount - 1; ri >= 0; ri--)
                {
                    int index = ci * rowCount + ri;
                    indiciesBuffer.put(index);
                    indiciesBuffer.put(index + rowCount);
                }
            }
        }

        indiciesBuffer.rewind();
    }

    void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step){

        final int columnCount = (int)((rangeX.y - rangeX.x) / step);
        final int rowCount = (int)((rangeZ.y - rangeZ.x) / step);

        indiciesBuffer = BufferUtil.newIntBuffer((columnCount - 1) * rowCount * 2);
        vertices.clear();

        for (int ci = 0; ci < columnCount; ci++) {
            final float x = rangeX.x + step * (float)ci;
            for (int ri = 0; ri < rowCount; ri++) {
                final float z = rangeZ.x + step * (float)ri;
                vertices.add(new SVertexP3N(FunctionSurfaces.getPosition(fn, x, z)));

                FunctionSurfaces.calculateNormal(vertices.lastElement(), fn, step);
            }
        }

        calculateTriangleStripIndicies(columnCount, rowCount);
    }

    private void drawElements(GL2 gl){

        gl.glDrawElements(GL2.GL_TRIANGLE_STRIP, indiciesBuffer.limit(), GL2.GL_UNSIGNED_INT, indiciesBuffer);
    }

    void draw(GLAutoDrawable drawable){
        final GL2 gl = drawable.getGL().getGL2();

        gl.glCullFace(GL2.GL_BACK);

       //gl.glFrontFace(GL2.GL_CW);

        FunctionSurfaces.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });

        gl.glCullFace(GL2.GL_FRONT);
        //gl.glFrontFace(GL2.GL_CCW);

        FunctionSurfaces.doWithBindedArrays(vertices, drawable, new Callable() {
            @Override
            public Object call() throws Exception {
                drawElements(gl);
                return null;
            }
        });
    }
}

