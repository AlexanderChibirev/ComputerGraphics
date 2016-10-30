import java.nio.ByteBuffer;
import java.util.Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class Function3D
{
	private FooFunctional m_fn;
	private boolean m_isEdgeOnly = true;
	private Vector<VertexP3N> m_vertices = new Vector<>();
	private Vector<Integer> m_indicies = new Vector<Integer>();
	private Vector2f m_range = new Vector2f();
	private boolean m_isColored = false;
	private final Vector3f MAX_COLOR = new Vector3f ( 0, 0.5f, 1);
	private final Vector3f MIN_COLOR =  new Vector3f ( 1, 0.5f, 0 );
	
	public Function3D(final FooFunctional fn) {
		this.m_fn = fn;
	}
	
	public void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step) {
		final int columnCount = (int) ((rangeX.y - rangeX.x) / step);
		final int rowCount = (int) ((rangeZ.y - rangeZ.x) / step);
		m_vertices.clear();		
		for (int ci = 0; ci < columnCount; ++ci)
		   {
		       final float x = rangeX.x + step * (float)ci;
		       for (int ri = 0; ri < rowCount; ++ri)
		       {
		    	   final float z = rangeZ.x + step * (float)ri;
		    	   m_vertices.add(new VertexP3N(m_fn.invoke(x, z)));
		    	   float yParam = m_vertices.get(m_vertices.size() - 1).getPosition().y;
		    	   m_range.y = Math.min(yParam, m_range.y);
		    	   m_range.x = Math.max(yParam, m_range.x);
		    	   calculateNormals(step, x, z);
		       }
		   }
		calculateTriangleStripIndicies(columnCount, rowCount);
	}
	
	private void calculateNormals(float step, float u, float v) {
		VertexP3N v1 = m_vertices.get(m_vertices.size() - 1);
		Vector3f normV1 = getDifference(m_fn.invoke(u, v + step), v1.getPosition());
		Vector3f normV2 = getDifference( m_fn.invoke(u + step, v ), v1.getPosition());
		m_vertices.get(m_vertices.size() - 1).setNormal(getCross(normV1, normV2));//.normalize());
	}
	
	private Vector3f getCross(Vector3f x, Vector3f y) {
		 		return new Vector3f(
		 				x.y * y.z - y.y * x.z,
		 				x.z * y.x - y.z * x.x,
		 				x.x * y.y - y.x * x.y);
		 	}
	
	private void calculateTriangleStripIndicies(int columnCount, int rowCount)
	{
		System.out.println(columnCount);
		m_indicies.clear();
	/*	for (int ci = 0; ci < columnCount - 1; ++ci)
		{
			if (ci % 2 == 0)
			{
				for (int ri = 0; ri < rowCount; ++ri)
				{
					int index = ci * rowCount + ri;
					//m_indicies.add(index + rowCount);
					//m_indicies.add(index);
				}
			}
			else
			{
				for (int ri = rowCount - 1; ri < rowCount; --ri)
				{
					int index = ci * rowCount + ri;
					//m_indicies.add(index);
					//m_indicies.add(index + rowCount);
				}
			}
		}*/
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
		m_isEdgeOnly = !m_isEdgeOnly;
	}
	
	/*private ByteBuffer toBuffer(Vector3f vertices) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);
		byteBuffer.putFloat(vertices.x);
		byteBuffer.putFloat(vertices.y);
		byteBuffer.putFloat(vertices.z);
		return byteBuffer;
	}
	
	private ByteBuffer toBuffer(Vector<Integer> vertices) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32);
		for(Integer value : vertices) {
			byteBuffer.putFloat(value);
		}
		return byteBuffer;
	}*/
	
	public void ChangeColorMode()
	{
		m_isColored = !m_isColored;
	}
	
	private float normalizeValue(float value, final Vector2f range)
	{
		final float length = (Math.abs(range.x - range.y));
		return Math.abs(range.y - value) / length;
	}
	
	private Vector3f getMultiplication(Vector3f v, float scalar) {
		return new Vector3f(
				v.x * scalar,
				v.y * scalar,
				v.z * scalar);	
	}
	
	private Vector3f getSum(Vector3f v1, Vector3f v2) {
		return new Vector3f(
				v1.x + v2.x,
				v1.y + v2.y,
				v1.z + v2.z);	
	}
	
	private Vector3f getDifference(Vector3f v1, Vector3f v2) {
		return new Vector3f(
				v1.x - v2.x,
				v1.y - v2.y,
				v1.z - v2.z);	
	}
	
	
	private void drawFunction(GL2 gl) {
		for (Integer it : m_indicies)
		{
			VertexP3N v = m_vertices.get(it);
			if (m_isColored)
			{
				float param = v.getPosition().y;
				final float normalizedY = normalizeValue(param, m_range);
				final Vector3f color = getSum(
						getMultiplication(MAX_COLOR, (1 - normalizedY)),
						getMultiplication(MIN_COLOR , normalizedY));
				gl.glColor3f(color.x, color.y, color.z);
			}
			else
			{
				gl.glColor3f(1, 1, 1);
			}
			gl.glVertex3f(v.getPosition().x, v.getPosition().y, v.getPosition().z);
			gl.glNormal3f(v.getNormal().x, v.getNormal().y, v.getNormal().z);

		}
	}
	
	public void draw(GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glCullFace(GL2.GL_FRONT);

		if (m_isEdgeOnly)
		{
			gl.glBegin(GL2.GL_LINE_STRIP);
			drawFunction(gl);
			gl.glEnd();
			gl.glCullFace(GL2.GL_BACK);
			gl.glBegin(GL2.GL_LINE_STRIP);
			drawFunction(gl);
			gl.glEnd();
		}		
		else
		{
			gl.glBegin(GL2.GL_TRIANGLE_STRIP);
			drawFunction(gl);
			gl.glEnd();
			gl.glCullFace(GL2.GL_BACK);
			gl.glBegin(GL2.GL_TRIANGLE_STRIP);
			drawFunction(gl);
			gl.glEnd();
		}
	}	
}