import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL2;

public class ShaderManager {
	private int vertexShaderProgram;
	private int shaderprogram;
	
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
		vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);

		String[] vsrc = loadShaderSrc("vertex.vs");
		gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
		gl.glCompileShader(vertexShaderProgram);

		shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, vertexShaderProgram);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS,intBuffer);
		if (intBuffer.get(0) != 1) {
			gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH,intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size>0) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);
				for (byte b:byteBuffer.array()){
					System.err.print((char)b);
				}
			} else {
				System.out.println("Unknown error");
			}
			System.exit(1);
		}
	
	}
	
	public void updateUniformVars(GL2 gl, float cur) {
		int mandel_iterations = gl.glGetUniformLocation(shaderprogram, "TWIST");
		assert(mandel_iterations != -1);
		gl.glUniform1f(mandel_iterations, cur);
		
	}
	
	public void start(GL2 gl) {
		gl.glUseProgram(shaderprogram);
	}
	
	public void stop(GL2 gl) {
		gl.glUseProgram(0);
	}
	
}
