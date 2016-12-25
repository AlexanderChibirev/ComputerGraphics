import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;

public class ShaderRaii {

	private int mId;

	private int MapShaderType(ShaderType type){
		switch (type){
			case Vertex:
				return GL2.GL_VERTEX_SHADER;
			case Fragment:
				return GL2.GL_FRAGMENT_SHADER;
			case Geometry:
				return GL2.GL_GEOMETRY_INPUT_TYPE_EXT;
		}
		return -1;
	}

	ShaderRaii(GL2 gl, ShaderType type){

		mId = gl.glCreateShader(MapShaderType(type));
	}

	int getId(){

		return mId;
	}

	int release(){

		int id = this.mId;
		this.mId = 0;

		return id;
	}

	void dispose(GL2 gl){

		gl.glDeleteShader(mId);
	}

}
