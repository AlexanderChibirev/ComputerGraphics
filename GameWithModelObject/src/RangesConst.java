public enum RangesConst {
	RANGE_BEGIN_FOR_MOVING_PLATFORM(0),
	RANGE_END_FOR_MOVING_PLATFORM(1),
	RANGE_BEGIN_FOR_BOX(1),
	RANGE_END_FOR_BOX(5),
	RANGE_BEGIN_FOR_BLOCKS(6);
	
	private final int value;
	
	RangesConst(int value) {
        this.value = value;
    }
	
	public int getValue()   { return value; }
}
