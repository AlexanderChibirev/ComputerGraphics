import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;

public interface ILightSource {
	 void setup(GL2 gl);
	 
	 void setAmbient(final float[] color);
	 void setDiffuse(final float[] color);
	 void setSpecular(final float[] color);
	 
	 float[] getAmbient();
	 float[] getDiffuse();
	 float[] getSpecular();
}
