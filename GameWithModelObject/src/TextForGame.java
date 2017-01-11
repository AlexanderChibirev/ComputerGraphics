import javax.vecmath.Vector2f;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class TextForGame {
	private final GLUT glut = new GLUT();
	private  Vector2f position;
	private  String text;
	
	public TextForGame(final Vector2f position) {
		this.position = position;
	}
	
	public void setText(GL2 gl, String text) {
		gl.glRasterPos2f(position.x,position.y);
		this.text = text;
		glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, this.text);
	}
}
