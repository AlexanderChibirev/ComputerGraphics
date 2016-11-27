import java.nio.FloatBuffer;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

class SVertexP3N{

    Vector3f position;
    Vector3f normal;

    SVertexP3N() {};

    SVertexP3N(Vector3f position) {

        this.position = position;
    }
}

class MathVector3f {

    static Vector3f normalize(Vector3f v) {
        float length_of_v = (float) Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
        return new Vector3f(v.x / length_of_v, v.y / length_of_v, v.z / length_of_v);
    }

    static Vector3f cross(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                (v1.y * v2.z) - (v1.z * v2.y),
                (v1.z * v2.x) - (v1.x * v2.z),
                (v1.x * v2.y) - (v1.y * v2.x)
        );
    }

    static Vector3f divideByInt(Vector3f v, int divider) {

        return new Vector3f(v.x / divider, v.y / divider, v.z / divider);
    }
}

class MathVec4f {

    static FloatBuffer toBuffer(Vector4f v) {

        FloatBuffer buffer = BufferUtil.newFloatBuffer(4);
        buffer.put(v.x);
        buffer.put(v.y);
        buffer.put(v.z);
        buffer.put(v.w);

        return buffer;
    }
}