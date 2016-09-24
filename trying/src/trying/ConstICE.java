package trying;

public enum ConstICE {
	UPPER_SCREEN_PISTOL_THRESHOLD(0f),
	LOWER_SCREEN_PISTOL_THRESHOLD(-.190f),
	PISTOL_DELTA_Y(.0019f),
	SPEED_ROTATION_CRANKSHAGT(1.8f), 
	SPEED_ROD(140f),
	SPEED_VALVESPRING(0.013f),//(0.00073f);
	TIME(0.001f);
	
	private final float value;
	
	ConstICE(float value) {
        this.value = value;
    }
	
	public float getValue()   { return value; }
}
