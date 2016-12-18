import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class DialDisplay implements GLEventListener  {
	private Line3D mLineObj = new Line3D(0, 2.f * (float)Math.PI,  (float)Math.PI / 1000.f);
	private TwistValueController mTwistController;
	private ShaderProgram mShaderProgram;
	
	public DialDisplay(TwistValueController inputHandler) {
		mTwistController = inputHandler;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor (0, 0, 0, 0);
	    gl.glLoadIdentity();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		mShaderProgram.use(gl);
			int mRZ = 2;
			float x = -0.15f;
			gl.glTranslated(x, 0, 0);
			gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
			try {
				mShaderProgram.updateUniformVars(gl, mShaderProgram.findUniform(gl, "TWIST"), mTwistController.getCurrentValue() );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//updateUniformVars(gl, mTwistController.getCurrentValue());
	        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
	        mLineObj.draw(drawable);
	        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        
	}
	
	private void initShaderProgram(GL2 gl) {
        String[] vertexShader = loadShaderFileFromSrc("vertex.vs");

        mShaderProgram = new ShaderProgram(gl);
        mShaderProgram.compileShader(gl, vertexShader, ShaderType.Vertex);
      
        mShaderProgram.link(gl);
    }
	
	private String[] loadShaderFileFromSrc( String fileName) {
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

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
        gl.setSwapInterval(1);
        initShaderProgram(gl);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) { 
            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

}
