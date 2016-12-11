package utils;

import javax.vecmath.Vector2f;

public class VecUtils {

	public static Vector2f sum(Vector2f v1, Vector2f v2){

		return new Vector2f(v1.x + v2.x, v1.y + v2.y);
	}
}