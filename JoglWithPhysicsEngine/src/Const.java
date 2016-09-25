
public enum Const {
	SCALE(45.0),
	NANO_TO_BASE(1.0e9),
	SPEED_ROTATION_CANNON(0.05f),
	GUN_TURRET_TRANSLATE_X(0),
	GUN_TURRET_TRANSLATE_Y(3),
	RANGE_BEGIN_FOR_BASE_PLATFORM(0),
	RANGE_END_FOR_BASE_PLATFORM(9),
	RANGE_BEGIN_FOR_CANNON(9),
	RANGE_END_FOR_CANNON(11);
	
	private final double value;
	
	Const(double value) {
        this.value = value;
    }
	
	public double getValue()   { return value; }
}
