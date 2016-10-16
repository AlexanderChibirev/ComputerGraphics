import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;


public class Rhombicuboctahedron {
	private float m_alpha = 1;
	final Vector3f PINK = new Vector3f(1.f, 0.3f, 0.3f);
	final Vector3f GREEN = new Vector3f(0.f, 1.f, 0.f);
	final Vector3f BLUE = new Vector3f(0.f, 0.f, 1.f);
	final Vector3f PURPLE = new Vector3f(1.f, 0.f, 1.f);
	final Vector3f CYAN = new Vector3f(0.f, 0.5f, 0.7f);
	private final Vector3f m_colors[] = { PINK , GREEN, BLUE, PURPLE, CYAN };
	private final Vector3f[] CUBE_VERTICIES = {
			new Vector3f(-0.5f, -1.2f, 0.5f),
			new Vector3f(0.5f, -1.2f, 0.5f), 
			new Vector3f(0.5f, -1.2f, -0.5f),
			new Vector3f(-0.5f, -1.2f, -0.5f),
			new Vector3f( -0.5f, -0.5f, 1.2f ),
			new Vector3f( 0.5f, -0.5f, 1.2f ),
			new Vector3f( 1.2f, -0.5f, 0.5f ),
			new Vector3f( 1.2f, -0.5f, -0.5f),
			new Vector3f( 0.5f, -0.5f, -1.2f ),
			new Vector3f( -0.5f, -0.5f, -1.2f ),
			new Vector3f( -1.2f, -0.5f, -0.5f),
			new Vector3f( -1.2f, -0.5f, 0.5f ),

			new Vector3f( -0.5f, 0.5f, 1.2f ),
			new Vector3f( 0.5f, 0.5f, 1.2f ),
			new Vector3f( 1.2f, 0.5f, 0.5f ),
			new Vector3f( 1.2f, 0.5f, -0.5f ),
			new Vector3f( 0.5f, 0.5f, -1.2f ),
			new Vector3f( -0.5f, 0.5f, -1.2f ),
			new Vector3f( -1.2f, 0.5f, -0.5f ),
			new Vector3f( -1.2f, 0.5f, 0.5f ),

			new Vector3f( -0.5f, 1.2f, 0.5f ),
			new Vector3f( 0.5f, 1.2f, 0.5f),
			new Vector3f( 0.5f, 1.2f, -0.5f ),
			new Vector3f( -0.5f, 1.2f, -0.5f )
	};
	private final TriangleFace[] CUBE_FACES = {
		new TriangleFace((short)0, (short)3, (short)2, (short)ColorsShape.Pink.ordinal()),
		new TriangleFace((short)0,  (short)2, (short)1,  (short)ColorsShape.Pink.ordinal()), 

		new TriangleFace((short)4, (short)11, (short)0, (short)ColorsShape.Cyan.ordinal()), 
		new TriangleFace((short)5, (short)4, (short)0, (short)ColorsShape.Green.ordinal()),
		new TriangleFace((short)5, (short)0, (short)1, (short)ColorsShape.Green.ordinal()),
		new TriangleFace((short)6, (short)5, (short)1, (short)ColorsShape.Purple.ordinal()), 
		new TriangleFace((short)7, (short)6, (short)1, (short)ColorsShape.Pink.ordinal()), 
		new TriangleFace((short)7, (short)1, (short)2, (short)ColorsShape.Pink.ordinal()), 
		new TriangleFace((short)8, (short)7, (short)2, (short)ColorsShape.Purple.ordinal()),
		new TriangleFace((short)9, (short)8, (short)2, (short)ColorsShape.Cyan.ordinal()), 
		new TriangleFace((short)9, (short)2, (short)3, (short)ColorsShape.Cyan.ordinal()),
		new TriangleFace((short)10, (short)9, (short)3, (short)ColorsShape.Blue.ordinal()), 
		new TriangleFace((short)11, (short)10, (short)3, (short)ColorsShape.Purple.ordinal()),
		new TriangleFace((short)11, (short)3, (short)0, (short)ColorsShape.Purple.ordinal()), 

		new TriangleFace((short)12, (short)4, (short)5,(short)ColorsShape.Purple.ordinal()),
		new TriangleFace((short)13, (short)12, (short)5, (short)ColorsShape.Purple.ordinal()),
		new TriangleFace((short)13, (short)5, (short)6, (short)ColorsShape.Pink.ordinal()),
		new TriangleFace((short)14, (short)13, (short)6, (short)ColorsShape.Pink.ordinal()) ,
		new TriangleFace((short)14, (short)6, (short)7, (short)ColorsShape.Cyan.ordinal()) ,
		new TriangleFace((short)15, (short)14, (short)7, (short)ColorsShape.Cyan.ordinal()),
		new TriangleFace((short)15, (short)7, (short)8,(short)ColorsShape.Blue.ordinal()),
		new TriangleFace((short)16, (short)15, (short)8, (short)ColorsShape.Blue.ordinal()),
		new TriangleFace((short)16, (short)8, (short)9, (short)ColorsShape.Green.ordinal()),
		new TriangleFace((short)17, (short)16, (short)9, (short)ColorsShape.Green.ordinal()) ,
		new TriangleFace((short)17, (short)9, (short)10, (short)ColorsShape.Pink.ordinal()) ,
		new TriangleFace((short)18, (short)17, (short)10, (short)ColorsShape.Pink.ordinal()),
		new TriangleFace((short)18, (short)10, (short)11, (short)ColorsShape.Cyan.ordinal()),
		new TriangleFace((short)19, (short)18, (short)11, (short)ColorsShape.Cyan.ordinal()),
		new TriangleFace((short)19, (short)11, (short)4, (short)ColorsShape.Blue.ordinal()) ,
		new TriangleFace((short)12, (short)19, (short)4, (short)ColorsShape.Blue.ordinal()) ,

		new TriangleFace((short)19, (short)12, (short)20, (short)ColorsShape.Cyan.ordinal()),
		new TriangleFace((short)12, (short)13, (short)20, (short)ColorsShape.Green.ordinal()) ,
		new TriangleFace((short)13, (short)21, (short)20, (short)ColorsShape.Green.ordinal()) ,
		new TriangleFace((short)13, (short)14, (short)21, (short)ColorsShape.Purple.ordinal()) ,
		new TriangleFace((short)14,(short) 15, (short)21, (short)ColorsShape.Pink.ordinal()) ,
		new TriangleFace((short)15, (short)22, (short)21, (short)ColorsShape.Pink.ordinal()) ,
		new TriangleFace((short)15,(short) 16, (short)22, (short)ColorsShape.Purple.ordinal()) ,
		new TriangleFace((short)16, (short)17, (short)22, (short)ColorsShape.Cyan.ordinal()) ,
		new TriangleFace((short)17, (short)23, (short)22, (short)ColorsShape.Cyan.ordinal()) ,
		new TriangleFace((short)17, (short)18, (short)23, (short)ColorsShape.Blue.ordinal()) ,
		new TriangleFace((short)18, (short)19, (short)23, (short)ColorsShape.Purple.ordinal()) ,
		new TriangleFace((short)19, (short)20, (short)23, (short)ColorsShape.Purple.ordinal()) ,

		new TriangleFace((short)20, (short)21, (short)22, (short)ColorsShape.Pink.ordinal()) ,
		new TriangleFace((short)20, (short)22, (short)23, (short)ColorsShape.Pink.ordinal()) 
	};
	
	public void draw(GL2 gl) {
		if (m_alpha < 0.99f)
		{
			gl.glFrontFace(GL2.GL_CW);
			outputFaces(gl);
			gl.glFrontFace(GL2.GL_CCW);
		}
		outputFaces(gl);
	}
	
	public void setAlpha(float alpha) {
		m_alpha = alpha;
	}
	
	public void outputFaces(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLES);
		for (final TriangleFace face : CUBE_FACES)
		{
			final Vector3f v1 = CUBE_VERTICIES[face.vertexIndex1];
			final Vector3f v2 = CUBE_VERTICIES[face.vertexIndex2];
			final Vector3f v3 = CUBE_VERTICIES[face.vertexIndex3];
			Vector3f color = m_colors[face.colorIndex];
			gl.glColor4f(color.x, color.y, color.z, m_alpha);
			gl.glVertex3f(v1.x, v1.y, v1.z);
			gl.glVertex3f(v2.x, v2.y, v2.z);
			gl.glVertex3f(v3.x, v3.y, v3.z);
		}
		gl.glEnd();
	}
}
