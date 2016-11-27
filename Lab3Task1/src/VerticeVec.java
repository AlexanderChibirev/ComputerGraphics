import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class VerticeVec {
    int x;
    int y;
    int z;
    Vector4f color;

    VerticeVec(int x, int y, int z, Vector4f color) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    static Vector3f normalize(Vector3f v) {
        float length_of_v = (float)Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
        return new Vector3f(v.x / length_of_v, v.y / length_of_v, v.z / length_of_v);
    }

    static Vector3f cross(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x)
        );
    }

    static Vector3f difference(Vector3f v1, Vector3f v2) {

        return new Vector3f(
                v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z
        );
    }

	public static Vector3f divideByInt(Vector3f vec, int i) {
		 return new Vector3f(
				 vec.x / i,
				 vec.y / i,
				 vec.z / i
	     );
	}
}
