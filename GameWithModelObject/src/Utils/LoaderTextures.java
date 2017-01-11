package Utils;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class LoaderTextures {
	public static Vector<Integer> sTexturesID = new Vector<Integer>();
	public static Vector<File> sTextures = new Vector<File>();
	
	static public Texture loadTexture(String fnm, GL2 gl) {
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
	
	static public void loadTextures(GL2 gl) {
		initializeTexturesName();
		initTexture(gl);
	}  // end of loadTexture()
	
	private static void initTexture(GL2 gl) {
		for(File name : sTextures) {
			try{
				Texture texture = TextureIO.newTexture(name,true);
				sTexturesID.add(texture.getTextureObject(gl));
			}
			catch(IOException e){
				e.printStackTrace();
			}	
		}
	}
	
	private static void initializeTexturesName() {
		sTextures.add(new File("images/background.jpg"));
		sTextures.add(new File("images/boxPlatform.jpg"));
		sTextures.add(new File("images/block1.jpg"));
		sTextures.add(new File("images/block2.jpg"));
		sTextures.add(new File("images/block3.jpg"));
		sTextures.add(new File("images/movingPlatform.jpg"));
		sTextures.add(new File("images/block22.jpg"));
		sTextures.add(new File("images/stars.jpg"));
	}
}
