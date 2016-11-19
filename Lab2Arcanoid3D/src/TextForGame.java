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
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(-400, 400, -300, 300, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glRasterPos2f(mPosition.x,mPosition.y);
		mText = text;
		mGlut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, mText);
	}
}
