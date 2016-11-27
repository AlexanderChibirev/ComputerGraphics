import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL2;

public class ShaderManager {
	private int mVertexCopeTexture;
	private int mFragmentCheckers;
	private int mFragmentCheckersAndTriangle;
	private int mShaderprogram;
	
	private String[] loadShaderSrc(String fileName) {
		StringBuilder sb = new StringBuilder();
		try{
			InputStream is = getClass().getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine())!=null){
				sb.append(line);
				sb.append('\n');
			}
			is.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return new String[]{sb.toString()};
	}
	
	
	public void attachShaders(GL2 gl) throws Exception {
		mShaderprogram = gl.glCreateProgram();
		mVertexCopeTexture = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		mFragmentCheckersAndTriangle = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		//mFragmentCheckers = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

		String[] vertexShaderSrc = loadShaderSrc("copytexture.vs");
		gl.glShaderSource(mVertexCopeTexture, 1, vertexShaderSrc, null, 0);
		gl.glCompileShader(mVertexCopeTexture);

		String[] pictureShaderSrc = loadShaderSrc("checkers-and-triangle.fs");
		gl.glShaderSource(mFragmentCheckersAndTriangle, 1, pictureShaderSrc, null, 0);
		gl.glCompileShader(mFragmentCheckersAndTriangle);		
		//String[] checkersShaderSrc = loadShaderSrc("checkers.fs");
		//gl.glShaderSource(mFragmentCheckers, 1, checkersShaderSrc, null, 0);
		//gl.glCompileShader(mFragmentCheckers);
		//gl.glAttachShader(mShaderprogram, mFragmentCheckersAndTriangle);
		
		
		gl.glAttachShader(mShaderprogram, mVertexCopeTexture);
		gl.glAttachShader(mShaderprogram, mFragmentCheckers);
		gl.glLinkProgram(mShaderprogram);
		
		gl.glValidateProgram(mShaderprogram);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGetProgramiv(mShaderprogram, GL2.GL_LINK_STATUS, intBuffer);
		if (intBuffer.get(0) != 1) {
			gl.glGetProgramiv(mShaderprogram, GL2.GL_INFO_LOG_LENGTH,intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size>0) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(mShaderprogram, size, intBuffer, byteBuffer);
				for (byte b:byteBuffer.array()){
					System.err.print((char)b);
				}
			} else {
				System.out.println("Unknown error");
			}
			System.exit(1);
		}
	
	}
	
	public void start(GL2 gl) {
		gl.glUseProgram(mShaderprogram);
	}
	
	public void stop(GL2 gl) {
		gl.glUseProgram(0);
	}
	
}
