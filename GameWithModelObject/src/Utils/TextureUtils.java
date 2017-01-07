package Utils;

import java.io.File;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureUtils {
	static public Texture loadTexture(String fnm, GL2 gl)
	  {
	    String fileName = "images/" + fnm;
	    Texture tex = null;
	    try {
	      tex = TextureIO.newTexture( new File(fileName), false);
	      tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
	      tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
	    }
	    catch(Exception e)
	    { System.out.println("Error loading texture " + fileName);  }

	    return tex;
	  }  // end of loadTexture()
}
