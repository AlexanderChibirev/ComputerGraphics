import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL2;

public class ShaderManager {
	private int mPositiveNormals;
	private int mNegativeNormals;
	private int mFragmentShader;
	private int mProgramNegativeNormals;
	private int mProgramPositiveNormals;
	
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
	
	private void getShaderiv(GL2 gl, int program) {
		IntBuffer compileStatus = BufferUtil.newIntBuffer(1);
		compileStatus.put(0);
		gl.glGetShaderiv(program, GL2.GL_COMPILE_STATUS, compileStatus);
	}
	
	private void checkWasError(IntBuffer intBuffer, GL2 gl) {
		if (intBuffer.get(0) != 1) {
			gl.glGetProgramiv(mProgramPositiveNormals, GL2.GL_INFO_LOG_LENGTH,intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size>0) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(mProgramPositiveNormals, size, intBuffer, byteBuffer);
				for (byte b:byteBuffer.array()){
					System.err.print((char)b);
				}
			} else {
				System.out.println("Unknown error");
			}
			System.exit(1);
		}
	}
	
	public void attachShadersPos(GL2 gl) throws Exception {
		mPositiveNormals = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		mFragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		mProgramPositiveNormals = gl.glCreateProgram();
		
		String[] vertexShaderSrc = loadShaderSrc("transf_with_positive_normals.vs");
		gl.glShaderSource(mPositiveNormals, 1, vertexShaderSrc, null, 0);
		gl.glCompileShader(mPositiveNormals);
		getShaderiv(gl, mProgramPositiveNormals);
		
		String[] checkersShaderSrc = loadShaderSrc("transformation.fs");
		gl.glShaderSource(mFragmentShader, 1, checkersShaderSrc, null, 0);
		gl.glCompileShader(mFragmentShader);
		getShaderiv(gl, mProgramPositiveNormals);
		
		gl.glAttachShader(mProgramPositiveNormals, mPositiveNormals);
		gl.glAttachShader(mProgramPositiveNormals, mFragmentShader);		
		gl.glLinkProgram(mProgramPositiveNormals);
		
		gl.glValidateProgram(mProgramPositiveNormals);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGetProgramiv(mProgramPositiveNormals, GL2.GL_LINK_STATUS, intBuffer);
		checkWasError(intBuffer, gl);
	}
	
	public void attachShadersNeg(GL2 gl) throws Exception {
		mNegativeNormals = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		mFragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		mProgramNegativeNormals = gl.glCreateProgram();
		
		String[] vertexShaderSrc = loadShaderSrc("transf_with_negative_normals.vs");
		gl.glShaderSource(mNegativeNormals, 1, vertexShaderSrc, null, 0);
		gl.glCompileShader(mNegativeNormals);
		getShaderiv(gl, mProgramNegativeNormals);
		
		String[] checkersShaderSrc = loadShaderSrc("transformation.fs");
		gl.glShaderSource(mFragmentShader, 1, checkersShaderSrc, null, 0);
		gl.glCompileShader(mFragmentShader);
		getShaderiv(gl, mProgramNegativeNormals);
		
		gl.glAttachShader(mProgramNegativeNormals, mNegativeNormals);
		gl.glAttachShader(mProgramNegativeNormals, mFragmentShader);		
		gl.glLinkProgram(mProgramNegativeNormals);
		
		gl.glValidateProgram(mProgramNegativeNormals);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGetProgramiv(mProgramNegativeNormals, GL2.GL_LINK_STATUS, intBuffer);
		checkWasError(intBuffer, gl);
	}
	
	public void updateUniformNegativeNormals(GL2 gl, float cur) {
		int mandel_iterations = gl.glGetUniformLocation(mProgramNegativeNormals, "time");
		assert(mandel_iterations != -1);
		gl.glUniform1f(mandel_iterations, cur);
	}
	
	public void updateUniformPositiveNormals(GL2 gl, float cur) {
		int mandel_iterations = gl.glGetUniformLocation(mProgramPositiveNormals, "time");
		assert(mandel_iterations != -1);
		gl.glUniform1f(mandel_iterations, cur);
		
	}
	
	public void startNegativeNormals(GL2 gl) {
		gl.glUseProgram(mProgramNegativeNormals);
	}
	
	public void stopNegativeNormals(GL2 gl) {
		gl.glUseProgram(0);
	}
	
	public void startPositiveNormals(GL2 gl) {
		gl.glUseProgram(mProgramPositiveNormals);
	}
	
	public void stopPositiveNormals(GL2 gl) {
		gl.glUseProgram(0);
	}
	
}
