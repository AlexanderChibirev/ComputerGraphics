
public enum WorldConsts {
	ID_FLOOR(8),
	SIZE_SKYBOX(75),
	POSSITION_BALL(5),
	POSSITION_MOVING_PLATFORM(0),
	NANO_TO_BASE(1.0e9),
	SCALE(45),
	ID_BACKGROUND(0);
	
	private final double value;
	
	WorldConsts(double value) {
        this.value = value;
    }
	
	public double getValue()   { return value; }
}
