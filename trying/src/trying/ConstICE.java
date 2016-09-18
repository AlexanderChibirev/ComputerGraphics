package trying;

public enum ConstICE {
	UPPER_SCREEN_PISTOL_THRESHOLD(.03f),
	LOWER_SCREEN_PISTOL_THRESHOLD(-.01f),
	PISTOL_DELTA_Y(.001f);
	
	private final float value;
	ConstICE(float value) {
        this.value = value;
    }
	
	public float getValue()   { return value; }
}
