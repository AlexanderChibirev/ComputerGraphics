
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.vecmath.Vector4f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class DialDisplay implements GLEventListener  {

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		includeMechanisms3DWorld(gl);
		setMaterial(gl);
	    setLight(gl);
		
	}

	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glFrontFace(GL2.GL_CCW);
		gl.glCullFace(GL2.GL_BACK);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	}
	
	private void setLight(GL2 gl){

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        final float[] AMBIENT = { 0.1f, 0.1f, 0.1f, 0.1f }; //0.1 * white
        final float[] DIFFUSE = { 1, 1, 1, 1 };             //white
        final float[] SPECULAR = { 1, 1, 1, 1 };            //white
        final float[] LIGHT_POSITION = {-1.f, 0.2f, 0.7f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LIGHT_POSITION, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, DIFFUSE, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, AMBIENT, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, SPECULAR, 0);
    }

    private void setMaterial(GL2 gl){

        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, toBuffer(new Vector4f(1, 1, 0, 1)));
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, toBuffer(new Vector4f(1, 1, 0, 1)));
       //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, toBuffer(new Vector4f(0.1f, 0.1f, 0.1f, 1.f)));
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 30f);
    }
	
    private FloatBuffer toBuffer(Vector4f v) {
    	ByteBuffer buf= ByteBuffer.allocateDirect(4 * 4);
		buf.order(ByteOrder.nativeOrder());
		FloatBuffer floatBuf;
		floatBuf = buf.asFloatBuffer();
        FloatBuffer buffer = floatBuf;
        buffer.put(v.x);
        buffer.put(v.y);
        buffer.put(v.z);
        buffer.put(v.w);
        return buffer;
    }
    
	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
	}

}
