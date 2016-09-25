
public enum Const {
	SCALE(45.0),
	NANO_TO_BASE(1.0e9),
	SPEED_ROTATION_CANNON(0.05f),
	GUN_TURRET_TRANSLATE_X(0),
	GUN_TURRET_TRANSLATE_Y(3);
	private final double value;
	
	Const(double value) {
        this.value = value;
    }
	
	public double getValue()   { return value; }
}
