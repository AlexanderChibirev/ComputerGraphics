import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
	public static FloatBuffer mFloatBuf;
	public static  IntBuffer mIntBuf;
	
	public static FloatBuffer newFloatBuffer(final int value) {
		
		ByteBuffer buf= ByteBuffer.allocateDirect(value * 4);
		buf.order(ByteOrder.nativeOrder());
		mFloatBuf = buf.asFloatBuffer();
		return mFloatBuf;
	}
	
	
	public static IntBuffer newIntBuffer(final int value) {
		ByteBuffer buf= ByteBuffer.allocateDirect(value * 4);
		buf.order(ByteOrder.nativeOrder());
		mIntBuf = buf.asIntBuffer();
		return mIntBuf;
	}
}
