import com.jogamp.opengl.GL2;

public class RhombicuboctahedronView {
	private Rhombicuboctahedron rhombicuboctahedron = new Rhombicuboctahedron();
	public void drawRhombicuboctahedron(GL2 gl) {
		drawEdges(gl);
		drawTriangles(gl);//поменять название 
	}
	
	private void drawEdges(GL2 gl) {
		rhombicuboctahedron.setAlpha(0.7f);
		rhombicuboctahedron.draw(gl);
	}
	
	private void drawTriangles(GL2 gl) {

	}
}
