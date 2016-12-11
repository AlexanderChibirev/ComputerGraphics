import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;


import java.io.File;
import java.util.Scanner;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;

@SuppressWarnings("serial")
public class Canvas extends GLCanvas implements GLEventListener {

    private static final Vector2f QUAD_TOPLEFT  = new Vector2f(-300, -150);
    private static final Vector2f QUAD_SIZE   = new Vector2f(600, 300);

    private GLU glu;
    private ShaderProgram shaderProgram;
    private Quad function;

    Canvas() {
        this.addGLEventListener(this);
        this.setFocusable(true);
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();

        // ----- Your OpenGL initialization code here -----
		function = new Quad(QUAD_TOPLEFT, QUAD_SIZE);
        initShaderProgram(gl);
        initGLContext(gl);
    }

    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        Matrix4f m = new Matrix4f();

		m.m00 = 2 / (right - left);
		m.m11 = 2 / (top - bottom);
		m.m22 = -2 / (zFar - zNear);
		m.m30 = -(right + left) / (right - left);
		m.m31 = -(top + bottom) / (top - bottom);
		m.m32 = -(zFar + zNear) / (zFar - zNear);

		return m;
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL2 gl = drawable.getGL().getGL2();

        /*
        final float fieldOfView = 60.f;
        final float aspect = (float)width / height;
        final float zNear = 0.1f;
        final float zFar = 100.f;

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(fieldOfView, aspect, zNear, zFar);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        */

        final float halfWidth = width * 0.5f;
        final float halfHeight = height * 0.5f;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        //gl.glLoadMatrixf(ortho(-halfWidth, halfWidth, -halfHeight, halfHeight, 0.1f, 100.f).get(BufferUtil.newFloatBuffer(16)));
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        // ----- OpenGL rendering code -----
        gl.glTranslatef(0.0f, 0.0f, -10.0f);


        // ----- Drawing shape -----
        shaderProgram.use(gl);

        //gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        function.draw(drawable);
        //gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

        shaderProgram.dispose(drawable.getGL().getGL2());
    }

    private void initGLContext(GL2 gl){

        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1f);

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_CCW);
        gl.glEnable(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);
    }

    private void initShaderProgram(GL2 gl){

        String source = null;
        try {
             source = new Scanner(new File("src/source/glslFunction.vert")).useDelimiter("\\Z").next();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        shaderProgram = new ShaderProgram(gl);
        shaderProgram.compileShader(gl, source, ShaderType.Vertex);
        shaderProgram.link(gl);
    }


}
