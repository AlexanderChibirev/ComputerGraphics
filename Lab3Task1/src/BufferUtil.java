import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
	public static FloatBuffer mDirection;
	public static  IntBuffer mDirectioni;
	
	public static FloatBuffer newFloatBuffer(final int value) {
		
		ByteBuffer buf= ByteBuffer.allocateDirect(value * 4);
		buf.order(ByteOrder.nativeOrder());
		mDirection = buf.asFloatBuffer();
		return mDirection;
	}
	
	
	public static IntBuffer newIntBuffer(final int value) {
		ByteBuffer buf= ByteBuffer.allocateDirect(value * 4);
		buf.order(ByteOrder.nativeOrder());
		mDirectioni = buf.asIntBuffer();
		return mDirectioni;
	}
}
