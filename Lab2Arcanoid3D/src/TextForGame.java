import javax.vecmath.Vector2f;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class TextForGame {
	private final GLUT mGlut = new GLUT();
	private  Vector2f mPosition;
	private  String mText;
	
	public TextForGame(final Vector2f position) {
		this.mPosition = position;
	}
	public void setText(GL2 gl, String text) {
	    gl.glMatrixMode (GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
		gl.glRasterPos2f(mPosition.x,mPosition.y); // set position
		mText = text;
		mGlut.glutBitmapString(2, mText);
		//mPosition.y += 20;
	}
}
