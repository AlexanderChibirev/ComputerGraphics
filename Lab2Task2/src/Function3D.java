import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class Function3D
{
	private FooFunctional m_fnOnX;
	private FooFunctional m_fnOnY;
	private FooFunctional m_fnOnZ;
	private boolean m_isFrame = false;
	private Vector<VertexP3N> m_vertices = new Vector<>();
	private ArrayList<Integer> m_indicies = new ArrayList<>();
	
	public Function3D(final FooFunctional fnOnX, final FooFunctional fnOnY, final FooFunctional fnOnZ) {
		this.m_fnOnX = fnOnX;
		this.m_fnOnY = fnOnY;
		this.m_fnOnZ = fnOnZ;
	}
	
	
	
	public void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step) {
		final int columnCount = (int) ((rangeX.y - rangeX.x) / step);
		final int rowCount = (int) ((rangeZ.y - rangeZ.x) / step);
		m_vertices.clear();
		for (long ci = 0; ci < columnCount; ++ci)
		{
			final float x = rangeX.x + step * (float)ci;
			for (long ri = 0; ri < rowCount; ++ri)
			{
				final float z = rangeZ.x + step * (float)ri;
				m_vertices.add(new VertexP3N(new Vector3f(
						m_fnOnX.invoke(x, z),
						m_fnOnY.invoke(x, z),
						m_fnOnZ.invoke(x, z))));
			}
		}
		//calculateNormals(step);
		calculateTriangleStripIndicies(columnCount, rowCount);
	}
	
	private Vector3f getDifference(Vector3f v1, Vector3f v2) {
		return new Vector3f(
				v1.x - v2.x, 
				v1.y - v2.y,
				v1.z - v2.z);
	}
	
	private Vector3f getCross(Vector3f x, Vector3f y) {
		return new Vector3f(
				x.y * y.z - y.y * x.z,
				x.z * y.x - y.z * x.x,
				x.x * y.y - y.x * x.y);
	}
	
	private Vector3f getNormalize(Vector3f cross) {
		return new Vector3f(0,0,0);
	}
	
	private void calculateNormals(float step)
	{
		for (VertexP3N v : m_vertices)
		{
			final Vector3f position = v.getPosition();
			Vector3f dir1 = getDifference(new Vector3f(position.y, position.x, position.z + step), position);
			Vector3f dir2 = getDifference(new Vector3f(position.y, position.x + step, position.z), position);
			v.setNormal(getNormalize(getCross(dir1, dir2)));
		}
	}
	
	void calculateTriangleStripIndicies(int columnCount, int rowCount)
	{
		m_indicies.clear();
		m_indicies.ensureCapacity((columnCount - 1) * rowCount * 2);
		for (int ci = 0; ci < columnCount - 1; ++ci)
		{
			if (ci % 2 == 0)
			{
				for (int ri = 0; ri < rowCount; ++ri)
				{
					int index = ci * rowCount + ri;
					m_indicies.add(index + rowCount);
					m_indicies.add(index);
				}
			}
			else
			{
				for (int ri = rowCount - 1; ri < rowCount; --ri)
				{
					int index = ci * rowCount + ri;
					m_indicies.add(index);
					m_indicies.add(index + rowCount);
				}
			}
		}
		int ci = columnCount - 1;
		if (ci % 2 == 0)
		{
			for (int ri = 0; ri < rowCount; ++ri)
			{
				int index = ci * rowCount + ri;
				m_indicies.add(rowCount - 1 - ri);
				m_indicies.add(index);
			}
		}
		else
		{
			for (int ri = rowCount - 1; ri < rowCount; --ri)
			{
				int index = ci * rowCount + ri;
				m_indicies.add(index);
				m_indicies.add(rowCount - ri - 1);
			}
		}
	}
	
	
	public void changeMode() {
		m_isFrame = !m_isFrame;
	}
	
	private ByteBuffer toBuffer(Vector3f vertices) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);
		byteBuffer.putFloat(vertices.x);
		byteBuffer.putFloat(vertices.y);
		byteBuffer.putFloat(vertices.z);
		return byteBuffer;
	}
	
	private ByteBuffer toBuffer(ArrayList<Integer> vertices) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);
		for(Integer value : vertices) {
			byteBuffer.putFloat(value);
		}
		return byteBuffer;
	}
	
	private void doWithBindedArrays(GL2 gl) {
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);

		// Выполняем привязку vertex array и normal array
		final int stride = 24;
		gl.glNormalPointer(GL2.GL_FLOAT, stride, toBuffer(m_vertices.get(0).getNormal()));
		gl.glVertexPointer(3, GL2.GL_FLOAT, stride, toBuffer(m_vertices.get(0).getPosition()));

		// Выполняем внешнюю функцию.
		if (m_isFrame) {
			gl.glDrawElements(GL2.GL_LINE_STRIP, (m_indicies.size()), GL2.GL_UNSIGNED_INT, toBuffer(m_indicies));
		}
		else {
			gl.glDrawElements(GL2.GL_TRIANGLE_STRIP, (m_indicies.size()), GL2.GL_UNSIGNED_INT, toBuffer(m_indicies));
		}
	
		// Выключаем режим vertex array и normal array.
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL2.GL_NORMAL_ARRAY);
	}
	
	public void draw(GL2 gl) {
		gl.glFrontFace(GL2.GL_CW);
		doWithBindedArrays(gl);
		gl.glFrontFace(GL2.GL_CCW);
		doWithBindedArrays(gl);		
	}	
}