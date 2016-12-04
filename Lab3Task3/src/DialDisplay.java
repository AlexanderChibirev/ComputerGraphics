
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.common.util.Function;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class DialDisplay implements GLEventListener  {
	private ShaderManager mShaderManager = new ShaderManager();
	private TransformationController mTransformationController;
	private Function<Vector3f, Float> getMoebiusPoint = new Function<Vector3f, Float>() {
			@Override
			public Vector3f eval(Float... floats) {
			    return getPoint(floats[0], floats[1]);
		}
	};
	private Vector3f getPoint(float u, float v) {

        final float r = 1f;

        Vector3f point = new Vector3f();

        if (u >= 0 && u <= Math.PI) {
            point.x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(u) * (float) Math.cos(v);
            point.y = 16 * (float) Math.sin(u) + 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(u) * (float) Math.cos(v);
            point.z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
        }
        else{

            point.x = 6 * (float) Math.cos(u) * (1 + (float) Math.sin(u)) - 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.cos(v);
            point.y = 16 * (float) Math.sin(u);
            point.z = 4 * r * (1 - (float) Math.cos(u) / 2) * (float) Math.sin(v);
        }

        return MathVector3f.divideByInt(point, 10);
   }
	private SolidMoebiusStrips m_mobiusBand = new SolidMoebiusStrips(getMoebiusPoint);
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		mShaderManager.startPositiveNormals(gl);
			//mShaderManager.updateUniformPositiveNormals(gl, mTransformationController.getCurrentValue());
			gl.glCullFace(GL2.GL_BACK); 
			m_mobiusBand.draw(gLDrawable);
		
		mShaderManager.startNegativeNormals(gl);
			//mShaderManager.updateUniformNegativeNormals(gl, mTransformationController.getCurrentValue());
			gl.glCullFace(GL2.GL_FRONT);
			m_mobiusBand.draw(gLDrawable);
	}
	
	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glFrontFace(GL2.GL_CW);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	}
	
	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
        gl.setSwapInterval(1);
		gl.glShadeModel(GL2.GL_FLAT);
		try {
			mShaderManager.attachShadersPos(gl);
			//mShaderManager.attachShadersNeg(gl);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		m_mobiusBand.tesselate(new Vector2f(-10.f, 10.f), new Vector2f(-10.f, 10.f), 0.07f);
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		final GL2 gl = gLDrawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) { 
            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

}
