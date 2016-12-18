import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.Callable;

import javax.vecmath.Vector2f;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class Surface {

	private enum ChangingImage
	{
		None,
		Normal,
		Revert,
	};	
	private final float MAX_TEX_COORD = 1.f;
	private final float WAVE_SPEED = 3.f;
	private Vector<SVertexP2T2> mVertices;
	private IntBuffer mIndicies;
	private ChangingImage mMode = ChangingImage.None;
	private float mAnimationTime;
	private Vector2f mCenterWave = new Vector2f(0,0);
	private Boolean mIsChanging = false;
	private ShaderProgram mShaderProgram;
	private int mTextureBear;
	private int mTextureSpace;
	private Texture textureForest;
	private Texture textureBilberry;
	
	Surface(Vector2f leftTop, Vector2f size, GL2 gl) {
		
		SVertexP2T2 vLeftTop = new SVertexP2T2(leftTop, new Vector2f(0, 0));
		SVertexP2T2 vRightTop = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(size.x, 0.f)), new Vector2f(MAX_TEX_COORD, 0));
		SVertexP2T2 vLeftBottom = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(0.f, size.y)), new Vector2f(0, MAX_TEX_COORD));
		SVertexP2T2 vRightBottom = new SVertexP2T2(VerticeVec.sum(leftTop, new Vector2f(size.x, size.y)), new Vector2f(MAX_TEX_COORD, MAX_TEX_COORD));

		mVertices = new Vector<>(Arrays.asList(vLeftTop, vRightTop, vLeftBottom, vRightBottom));
		int[] indArray = {0, 1, 2, 1, 3, 2};
		mIndicies = BufferUtil.newIntBuffer(6);
		mIndicies.put(indArray);
		mIndicies.rewind();
		initShaderProgram(gl);
		initTextue(gl);
	}
	
	private void initTextue(GL2 gl) {		
		try{
			textureBilberry = TextureIO.newTexture(new File("img/bilberry.jpg"), true);
			//mTextureBear = (textureBear.getTextureObject(gl));
			textureForest = TextureIO.newTexture(new File("img/forest.jpg"), true);
			//mTextureSpace = (textureSpace.getTextureObject(gl));
			
		}
		catch(IOException e){
			e.printStackTrace();
		}		
	}

	private void initShaderProgram(GL2 gl) {
		String[] vertexShader = loadShaderFileFromSrc("ShaderWave.vs");
        String[] fragmentShader = loadShaderFileFromSrc("ShaderWave.fs");

        mShaderProgram = new ShaderProgram(gl);
        mShaderProgram.compileShader(gl, vertexShader, ShaderType.Vertex);
        mShaderProgram.compileShader(gl, fragmentShader, ShaderType.Fragment);
      
        mShaderProgram.link(gl);
    }
	
	private String[] loadShaderFileFromSrc( String fileName) {
		StringBuilder sb = new StringBuilder();
		try{
			InputStream is = getClass().getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine())!=null) {
				sb.append(line);
				sb.append('\n');
			}
			is.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new String[]{sb.toString()};
	}

	public void draw(GLAutoDrawable drawable) throws Exception {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glActiveTexture(GL2.GL_TEXTURE1);
		textureForest.bind(gl);
		
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		textureBilberry.bind(gl);
		
		mShaderProgram.use(gl);
		switch(mMode) {
			
			case Normal:
				mShaderProgram.findUniform(gl, "tex0",0);
				mShaderProgram.findUniform(gl, "tex1", 1);
				break;
			case Revert:
				mShaderProgram.findUniform(gl, "tex0", GL2.GL_TEXTURE1);
				mShaderProgram.findUniform(gl, "tex1", GL2.GL_TEXTURE0);
				break;
		}
		mShaderProgram.findUniform(gl, "time", mAnimationTime);
		mShaderProgram.findUniform(gl, "center", mCenterWave);
		
		BindedUtils.doWithBindedArrays(mVertices, drawable, new Callable<Object>() {
	            @Override
	            public Object call() throws Exception {
	            	gl.glDrawElements(GL2.GL_TRIANGLES, mIndicies.limit(),
	            		GL2.GL_UNSIGNED_INT, mIndicies);
	                return null;
	            }
	        });
	}
	
	public void update(double elapsedTime) {
		mAnimationTime += elapsedTime * WAVE_SPEED;
		if (mIsChanging) {
			if (mAnimationTime >= 5.f) {
				mIsChanging = false;
			}
		}
	}
	
	public void setWaveCenter(final Vector2f pos)
	{
		if (!mIsChanging)
		{
			System.out.println(pos);
			mCenterWave = pos;
			mMode = mMode == ChangingImage.Normal ? ChangingImage.Revert : ChangingImage.Normal;
			mIsChanging = true;
			mAnimationTime = 0.f;
		}
	}
}
