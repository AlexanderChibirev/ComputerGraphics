
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class DialDisplay implements GLEventListener  {
	
	private TransformationController mTransformationController;
	private SolidMoebiusStrips mMobiusBand = new SolidMoebiusStrips();
	private ShaderProgram mProgramPositiveNormals;
	private ShaderProgram mProgramNegativeNormals;
	
	public DialDisplay(TransformationController inputHandler) {
		mTransformationController = inputHandler;
	}
	
	private void setUniformForPositiveNormals(GL2 gl) {
		try {
			mProgramPositiveNormals.updateUniformVars(gl,
					mProgramPositiveNormals.findUniform(gl, "time"),
					mTransformationController.getCurrentValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setUniformForNegativeNormals(GL2 gl) {
		try {
			mProgramNegativeNormals.updateUniformVars(gl,
					mProgramNegativeNormals.findUniform(gl, "time"),
					mTransformationController.getCurrentValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		int mRZ = 12;
		float x = 0.15f;
		float y = 0.15f;
		gl.glTranslated(x, y, 0);
		gl.glOrtho (-10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ, -10-mRZ, 10+mRZ);
		
		gl.glTranslatef(0.0f, 0.0f, -10.0f);
		
		mProgramPositiveNormals.use(gl);
		setUniformForPositiveNormals(gl);
		gl.glCullFace(GL2.GL_BACK);
		mMobiusBand.draw(gLDrawable);		
		
		mProgramNegativeNormals.use(gl);
		setUniformForNegativeNormals(gl);		
		gl.glCullFace(GL2.GL_FRONT);
		mMobiusBand.draw(gLDrawable);
	}
	
	private void includeMechanisms3DWorld(GL2 gl) {
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glFrontFace(GL2.GL_CW);
		gl.glColorMaterial(GL2.GL_FRONT,GL2.GL_AMBIENT_AND_DIFFUSE);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	    gl.glLoadIdentity();
	}
	
	private void initLight(GL2 gl){

        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);

        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glLightModeli( GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE );

        gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_FALSE);
        gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_FALSE);

        final float[] AMBIENT = {0.2f, -1.f, 0.7f}; //0.1 * white
        final float[] DIFFUSE = { 1, 1, 1, 1 };             //white
        final float[] SPECULAR = { 1, 1, 1, 1 };            //white
        final float[] LIGHT_POSITION = {10.f, 5.0f, 1.9f };

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, LIGHT_POSITION, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, DIFFUSE, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, AMBIENT, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, SPECULAR, 0);
    }
	
	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
	}
	
	private void initShaderProgram(GL2 gl) {
        String[] positiveNormals = loadFileAsString("transf_with_positive_normals.vs");
        String[] fragmentShader = loadFileAsString("transformation.fs");

        mProgramPositiveNormals = new ShaderProgram(gl);        
        mProgramPositiveNormals.compileShader(gl, positiveNormals, ShaderType.Vertex);
        mProgramPositiveNormals.compileShader(gl, fragmentShader, ShaderType.Fragment);     
        mProgramPositiveNormals.link(gl);
        
        String[] negativeNormals = loadFileAsString("transf_with_negative_normals.vs");
        
        mProgramNegativeNormals = new ShaderProgram(gl);        
        mProgramNegativeNormals.compileShader(gl, negativeNormals, ShaderType.Vertex);
        mProgramNegativeNormals.compileShader(gl, fragmentShader, ShaderType.Fragment);     
        mProgramNegativeNormals.link(gl);
    }
	
	private String[] loadFileAsString( String fileName) {
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
	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
        gl.setSwapInterval(1);
        includeMechanisms3DWorld(gl);
        initShaderProgram(gl);
		initLight(gl);
		mMobiusBand.tesselate(800,800);
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		final GL2 gl = gLDrawable.getGL().getGL2();
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
