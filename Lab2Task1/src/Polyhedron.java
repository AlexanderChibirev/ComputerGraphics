import java.util.Vector;

import javax.vecmath.Vector3f;

public class Polyhedron {
	enum TypeShapes {
		DODECAHEDRON, PENTAKIS, STAR
	}
	
	private Vector<Vector3f> m_verticies = new Vector<>();
	private Vector<Vector3f> m_sourceVerticies = new Vector<>();
	private int[] m_pentakisFaces = new int[100];
	private TypeShapes m_type = getType().DODECAHEDRON ;
	private final int[] PENTAKIS_EDGES = {
			0, 12, 1, 18, 16, 
			0, 12, 14, 4, 8,
			0, 8, 10, 2, 16,
			0, 12,
			1, 9, 5, 14, 12, 
			1, 18, 3, 11, 9,
			5, 19, 17, 4, 14, 5,
			9, 11, 7, 19, 5, 19, 7,
			11, 3, 13, 15, 7, 15,
			6, 17, 6, 10, 2, 13
	};
	private final float[] TYPE_COEFFICIENT = { 1, 1.226f, 2.2f };
	private final float ANIMATION_SPEED = 0.01f;
	private final Vector3f WHITE = new Vector3f( 1f, 1f, 1f);
	private final float FI = 1.618f;
	
	private final Vector3f[] PENTAKIS_VERTICIES = {
			new Vector3f(1, 1, 1 ),
			new Vector3f(1, 1, -1 ),
			new Vector3f(1, -1, 1 ),
			new Vector3f(1, -1, -1 ),
			new Vector3f(-1, 1, 1 ),
			new Vector3f(-1, 1, 1 ),
			new Vector3f(-1, -1, -1 ),
			new Vector3f(-1, -1, -1 ),
			
			new Vector3f( 0, 1 / FI, FI),
			new Vector3f(0, 1 / FI, -FI  ),
			new Vector3f( 0, -1 / FI, FI ),
			new Vector3f( 0, -1 / FI, -FI  ),
			
			new Vector3f(1 / FI, FI, 0 ),
			new Vector3f(1 / FI, -FI,  0 ),
			new Vector3f(-1 / FI, FI, 0 ),
			new Vector3f(-1 / FI, -FI, 0  ),
			
			new Vector3f(FI, 0, 1 / FI ),
			new Vector3f(-FI, 0, 1 / FI ),
			new Vector3f(FI, 0, -1 / FI ),
			new Vector3f(-FI, 0, -1 / FI ),
	};
	
	public void update(float deltaTime) {
		
	}
	
	public void nextType() {
		
	}
	
	final public Vector<Vector3f> getVerticies() {
		return m_verticies;
	}
	
	final public int[] getFaces() {
		return m_pentakisFaces;
	}
	
	final public int[] getEdgesFaces() {
		if (m_type == TypeShapes.DODECAHEDRON)
		{
			return PENTAKIS_EDGES;
		}
		else
		{
			return m_pentakisFaces;
		}
	}
	
	final public TypeShapes  getType()  {
		return m_type;
	}
}
