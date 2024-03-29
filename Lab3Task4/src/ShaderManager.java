import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;

public class ShaderManager {
	private int vertexShaderProgram;
	private int fragmentShaderProgram;
	private int shaderprogram;
	private final MandelbrotSetting setting = new MandelbrotSetting();
	
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
	
	public void updateUniformVars(GL2 gl) {
        // get memory address of uniform shader variables
		int mandel_x = gl.glGetUniformLocation(shaderprogram, "mandel_x");
		int mandel_y = gl.glGetUniformLocation(shaderprogram, "mandel_y");
		int mandel_width = gl.glGetUniformLocation(shaderprogram, "mandel_width");
		int mandel_height = gl.glGetUniformLocation(shaderprogram, "mandel_height");
		int mandel_iterations = gl.glGetUniformLocation(shaderprogram, "mandel_iterations");
		assert(mandel_x != -1);
		assert(mandel_y != -1);
		assert(mandel_width != -1);
		assert(mandel_height != -1);
		assert(mandel_iterations!=-1);
        // set uniform shader variables
		gl.glUniform1f(mandel_x, setting.getX());
		gl.glUniform1f(mandel_y, setting.getY());
		gl.glUniform1f(mandel_width, setting.getWidth());
		gl.glUniform1f(mandel_height, setting.getHeight());
		gl.glUniform1f(mandel_iterations, setting.getIterations());

	}
	
	public void attachShaders(GL2 gl) throws Exception {
		vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		fragmentShaderProgram = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

		String[] vsrc = loadShaderSrc("vertex.vs");
		gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
		gl.glCompileShader(vertexShaderProgram);

		String[] fsrc = loadShaderSrc("fragment.fs");
		gl.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
		gl.glCompileShader(fragmentShaderProgram);

		shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, vertexShaderProgram);
		gl.glAttachShader(shaderprogram, fragmentShaderProgram);
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
	
	public void interpolate(Vector2f size, Vector2f pos, int iterations) {
		setting.setHeight(size.x);
		setting.setWidth(size.y);
		setting.setX(pos.x);
		setting.setY(pos.y);
		setting.setIterations(iterations);
	}
	
	public void start(GL2 gl) {
		gl.glUseProgram(shaderprogram);
	}
	
	public void stop(GL2 gl) {
		gl.glUseProgram(0);
	}
	
}
