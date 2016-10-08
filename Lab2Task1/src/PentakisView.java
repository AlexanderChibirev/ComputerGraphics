
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;
import javax.vecmath.Vector3f;

public class PentakisView {
	private Polyhedron polyhedron = new Polyhedron();
	public void drawPentakis(GL2 gl) {
		drawEdges(gl);
		drawTriangles(gl);//поменять название 
	}
	
	private void drawEdges(GL2 gl) {
		Vector3f d = new Vector3f(1f,3f,3f);
		gl.glBegin(GL2.GL_LINE_STRIP);
		d.x = 0;
		gl.glColor4f(0, 0, 0, 1);
		/*for (int i : polyhedron.GetEdgesFaces())
		{
			const  Vector3f v = polyhedron.GetVerticies()[i];
			gl.glVertex3f(v.x, v.y, v.z);
		}*/
		gl.glEnd();
	}
	
	private void drawTriangles(GL2 gl) {

	}
}
