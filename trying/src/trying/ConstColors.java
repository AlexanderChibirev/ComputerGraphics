package trying;

public enum ConstColors {
	GRAY(0.85f, 0.85f, 0.85f),
	DARK_GRAY(0.5f, 0.5f, 0.5f),
	BLACK(0,0,0),
	YELLOW(1, 1, 0),
	BROWN(0.84f, 0.61f, 0.04f);
	
	private final float R;
	private final float G;
	private final float B;
	
	ConstColors(float R, float G, float B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }
	
	public float R() {
		return R; 
	}
	public float G() {
		return G; 
	}
	public float B() {
		return B; 
	}
}
