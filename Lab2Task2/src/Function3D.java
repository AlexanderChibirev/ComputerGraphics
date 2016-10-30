import java.util.Vector;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;

public class Function3D {
	private FooFunctional m_fn;
	private Vector<VertexP3N> m_vertices = new Vector<>();
	private Vector<Integer> m_indicies = new Vector<Integer>();
	private Vector2f m_range = new Vector2f();
	
	public Function3D(final FooFunctional fn) {
		this.m_fn = fn;
		tesselate(new Vector2f(0.f, (float)(2 * Math.PI)), new Vector2f(-1, 1), 0.025f);
	}
			
	private void tesselate(final Vector2f rangeX, final Vector2f rangeZ, float step) {
		final int columnCount = (int) ((rangeX.y - rangeX.x) / step);
		final int rowCount = (int) ((rangeZ.y - rangeZ.x) / step);
		m_vertices.clear();		
		for (int ci = 0; ci < columnCount; ++ci) {
		       final float x = rangeX.x + step * (float)ci;
		       for (int ri = 0; ri < rowCount; ++ri) {
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
		m_vertices.get(m_vertices.size() - 1).setNormal(getCross(normV1, normV2));
	}
	
	private Vector3f getCross(Vector3f x, Vector3f y) {
		 		return new Vector3f(
		 				x.y * y.z - y.y * x.z,
		 				x.z * y.x - y.z * x.x,
		 				x.x * y.y - y.x * x.y);
	}
	
	
		
	private Vector3f getDifference(Vector3f v1, Vector3f v2) {
		return new Vector3f(
				v1.x - v2.x,
				v1.y - v2.y,
				v1.z - v2.z);	
	}
	
	
	private void drawFunction(GL2 gl) {
		for (Integer it : m_indicies) {
			VertexP3N v = m_vertices.get(it);
			gl.glColor3f(0.1f, 0.85f, 1);
			gl.glVertex3f(v.getPosition().x, v.getPosition().y, v.getPosition().z);
			gl.glNormal3f(v.getNormal().x, v.getNormal().y, v.getNormal().z);
		}
	}
	
	public void draw(GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glCullFace(GL2.GL_FRONT);
		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		drawFunction(gl);
		gl.glEnd();
		gl.glCullFace(GL2.GL_BACK);
		gl.glBegin(GL2.GL_TRIANGLE_STRIP);
		drawFunction(gl);
		gl.glEnd();
	}	
	
	private void calculateTriangleStripIndicies(int columnCount, int rowCount) {
	    for (int ci = 0; ci < columnCount - 1; ++ci) {
	        if (ci % 2 == 0) {
	            for (int ri = 0; ri < rowCount; ++ri) {
	            	int index = ci * rowCount + ri;
	                m_indicies.add((index + rowCount));
	                m_indicies.add(index);
	            }
	        }
	        else {
	            for (int ri = rowCount - 1; ri < rowCount; --ri) {
	            	int index = ci * rowCount + ri;
	            	m_indicies.add(index);
	            	m_indicies.add(index + rowCount);
	            	if(ri == 0) {
	            		break;
	            	}
	            }
	        }
	    }
	    int ci = columnCount - 1;
		if (ci % 2 == 0) {
			for (int ri = 0; ri < rowCount; ++ri) {
				int index = ci * rowCount + ri;
				m_indicies.add(rowCount - 1 - ri);
				m_indicies.add(index);
			}
		}
		else {
			for (int ri = rowCount - 1; ri > rowCount; ++ri) {
				int index = ci * rowCount + ri;
				m_indicies.add(index);
				m_indicies.add((rowCount - ri - 1));
			}
		}
	}
}