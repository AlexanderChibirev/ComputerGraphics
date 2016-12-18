import com.jogamp.opengl.GL2;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Vector;

public class ShaderProgram {
	private int mProgramId = 0;
	private Vector<Integer> mShaders;
	
	ShaderProgram(GL2 gl) {
		mShaders = new Vector<>();
		mProgramId = gl.glCreateProgram();
	}

	ShaderProgram(GL2 gl, int a) {
		mProgramId = 0;
	}

	private void freeShaders(GL2 gl) {
		for (int shaderId : mShaders)
		{
			gl.glDetachShader(mProgramId, shaderId);
			gl.glDeleteShader(shaderId);
		}
		mShaders.clear();
	}
	
	private void checkStatus(String logError, IntBuffer status, GL2 gl) {
		if (status.get(0) == GL2.GL_FALSE) {
			gl.glGetProgramiv(mProgramId, GL2.GL_INFO_LOG_LENGTH, status);
			int size = status.get(0);
			System.err.print("Program ");
			System.err.print(logError);
			System.err.println(" Status error: ");
			if (size > 0) {
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(mProgramId, size, status, byteBuffer);
				for (byte b:byteBuffer.array()) {
					System.err.print((char)b);
				}
			} else {
				System.out.println("Unknown error");
			}
			System.exit(1);
		}

	}


	void compileShader(GL2 gl, String[] source, ShaderType type) {
		final IntBuffer pSourceLengths = BufferUtil.newIntBuffer(1);
		pSourceLengths.put(source.length);

		ShaderRaii shader = new ShaderRaii(gl, type);
		gl.glShaderSource(shader.getId(), 1, source, null);
		gl.glCompileShader(shader.getId());

		IntBuffer compileStatus = BufferUtil.newIntBuffer(1);
		gl.glGetShaderiv(shader.getId(), GL2.GL_COMPILE_STATUS, compileStatus);
		checkStatus("compile", compileStatus, gl);
		mShaders.add(shader.release());
		gl.glAttachShader(mProgramId, mShaders.lastElement());

	}

	void link(GL2 gl) {
		gl.glLinkProgram(mProgramId);
		IntBuffer linkStatus =  IntBuffer.allocate(1);

		gl.glGetProgramiv(mProgramId, GL2.GL_LINK_STATUS, linkStatus);
		checkStatus("link", linkStatus, gl);
		freeShaders(gl);
	}

	void use(GL2 gl) {
		gl.glUseProgram(mProgramId);
	}

	public void updateUniformVars(GL2 gl, int time, float value) {
		assert(time != -1);
		gl.glUniform1f(time, value);
	}
	
	final int findUniform(GL2 gl, String name) throws Exception {
		int location = gl.glGetUniformLocation(mProgramId, name);
		if (location == -1) {
			throw new Exception("Wrong shader variable name: " + name);
		}
		return location;
	}
	
	void dispose(GL2 gl) {

		freeShaders(gl);
		gl.glDeleteProgram(mProgramId);
	}
}
