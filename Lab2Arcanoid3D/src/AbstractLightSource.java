import com.jogamp.opengl.GL2;

/*
 * const method does not exist in java. 
 * final and const have different semantics,
 * except when applied to a variable of a primitive type.
 * The java solution typically involves creating immutable classes -
 * where objects are initialized in construction and provide
 * no accessors allowing change.
*/
public abstract class AbstractLightSource implements ILightSource {
	AbstractLightSource(int index) {
		this.mIndex = index;
	}
	
	private float[] mAmbient;
	private float[] mDiffuse;
	private float[] mSpecular;
	private int mIndex;
	
	protected void setupImpl(GL2 gl) {
		gl.glEnable(mIndex);
		gl.glLightfv(mIndex, GL2.GL_AMBIENT, mAmbient, 0);
		gl.glLightfv(mIndex, GL2.GL_DIFFUSE, mDiffuse, 0);
		gl.glLightfv(mIndex, GL2.GL_SPECULAR, mSpecular, 0);
	}
	protected int getIndex() {
		return mIndex;	
	}
	 
	public final void setAmbient(final float[] color) {
		mAmbient = color;
	}
	
	public final void setDiffuse(final float[] color) {
		mDiffuse = color;
	}
	
	public final void setSpecular(final float[] color) {
		mSpecular = color;
	}
	 
	public final float[] getAmbient() {
		return mAmbient;
	}
	
	public final float[] getDiffuse() {
		return mDiffuse;
	}
	
	public final float[] getSpecular() {
		return mSpecular;
	}
}
