import com.jogamp.opengl.GL2;

public class Light {
	private final float[] WHITE_LIGHT = {1, 1, 1, 1};
	private final float[] WHITE_LIGHT1 = {0.3f, 0.3f, 0.8f, 0.8f};
	private final float[] SUNLIGHT_DIRECTION = { -1.f, 0.2f, 0.7f };
	private DirectedLightSource mSunlight = new DirectedLightSource(GL2.GL_LIGHT0);

	public void setLight(GL2 gl) {
		mSunlight.setDirection(SUNLIGHT_DIRECTION);
		mSunlight.setDiffuse(WHITE_LIGHT);
		mSunlight.setAmbient(WHITE_LIGHT1);
		mSunlight.setSpecular(WHITE_LIGHT);
		mSunlight.setup(gl);
	}
	
/*	float ambient[] ={ 0.0f, 0.0f, 0.0f, 1.0f };
    float diffuse[] ={ 1.0f, 1.0f, 1.0f, 1.0f };
    float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    float position[] ={ 0.0f, 3.0f, 2.0f, 0.0f };
    float lmodel_ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };
    float local_view[] = { 0.0f };

    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);

    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
    gl.glLightfv(GL2., GL2.GL_POSITION, position, 0);
    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);
    gl.glLightModelfv(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view, 0);

    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
*/
}
