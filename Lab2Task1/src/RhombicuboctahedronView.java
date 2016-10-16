import com.jogamp.opengl.GL2;

public class RhombicuboctahedronView {
	private Rhombicuboctahedron rhombicuboctahedron = new Rhombicuboctahedron();
	
	private void disableBlending(GL2 gl) {
		gl.glDepthMask(true);
		gl.glDisable(GL2.GL_BLEND);
	}
	
	private void enableBlending(GL2 gl) {
		gl.glDepthMask(false);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void drawRhombicuboctahedron(GL2 gl) {
		enableBlending(gl);
		rhombicuboctahedron.setAlpha(0.8f);
		rhombicuboctahedron.draw(gl);
		disableBlending(gl);
	}
}
