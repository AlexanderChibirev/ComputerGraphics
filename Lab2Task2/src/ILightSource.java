import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;

public interface ILightSource {
	 void setup(GL2 gl);
	 
	 void setAmbient(final FloatBuffer color);
	 void setDiffuse(final FloatBuffer color);
	 void setSpecular(final FloatBuffer color);
	 
	 FloatBuffer getAmbient();
	 FloatBuffer getDiffuse();
	 FloatBuffer getSpecular();
}
