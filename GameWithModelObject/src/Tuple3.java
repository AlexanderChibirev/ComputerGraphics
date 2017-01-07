class Tuple3 {
	private float x;
	private float y;
	private float z;
	/**
	 * Create a new 3 dimensional tuple
	 * 
	 * @param x The X element value for the new tuple
	 * @param y The Y element value for the new tuple
	 * @param z The Z element value for the new tuple
	 */
	public Tuple3(float x,float y,float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Get the X element value from this tuple
	 * 
	 * @return The X element value from this tuple
	 */
	public float getX() {
		return x;
	}

	/**
	 * Get the Y element value from this tuple
	 * 
	 * @return The Y element value from this tuple
	 */
	public float getY() {
		return y;
	}

	/**
	 * Get the Z element value from this tuple
	 * 
	 * @return The Z element value from this tuple
	 */
	public float getZ() {
		return z;
	}
}