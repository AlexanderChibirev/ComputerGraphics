import java.util.Vector;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;


public class Polyhedron {
	enum TypeShapes {
		DODECAHEDRON, PENTAKIS, STAR
	}
	Polyhedron() {
		InitPentakis();
	}
	

	private float m_currentPickLvl = 1;
	private boolean typeWasChanged = true;
	private float m_animSpeed = 0;
	private Vector3f[] m_verticies = new  Vector3f[100];
	private Vector<Vector3f> m_sourceVerticies = new Vector<>();
	private int[] m_pentakisFaces = new int[100];
	private TypeShapes m_type = TypeShapes.DODECAHEDRON ;
	private final Vector4f[]  COLORS = {
			new Vector4f( 1, 1, 1, 0.8f),
			new Vector4f( 1, 1, 0, 0.8f),
			new Vector4f( 1, 0, 1, 0.8f),
			new Vector4f( 1, 0, 0, 0.8f),
			new Vector4f( 0, 1, 1, 0.8f),
			new Vector4f( 0, 1, 0, 0.8f),
			new Vector4f( 0, 1, 1, 0.8f)
		};
	
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
	
	private final int[][] POLIGONS_VERTICIES = {
			{0, 16, 18, 1, 12},
			{0, 12, 14, 4, 8},
			{0, 8, 10, 2, 16},
			{12, 1, 9, 5, 14},
			{5, 19, 17, 4, 14},
			{17, 19, 7, 15, 6},
			{15, 13, 2, 10, 6},
			{2, 13, 3, 18, 16},
			{18, 3, 11, 9, 1},
			{9, 11, 7, 19, 5},
			{11, 3, 13, 15, 7},
			{4, 17, 6, 10, 8}
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
		setPickLvl();
		if (typeWasChanged)
		{
			checkType();
		}
	}
	
	private void setPickLvl()
	{
		if (m_currentPickLvl == TYPE_COEFFICIENT[m_type.ordinal()])
			return;
		m_currentPickLvl += m_animSpeed;
		if (m_animSpeed < 0 && m_currentPickLvl < TYPE_COEFFICIENT[m_type.ordinal()] || m_animSpeed > 0 && m_currentPickLvl > TYPE_COEFFICIENT[m_type.ordinal()])
		{
			m_currentPickLvl = TYPE_COEFFICIENT[m_type.ordinal()];
			typeWasChanged = false;

		}
	}

	
	private void checkType()
	{
		for (long i = 20; i < m_verticies.length; ++i)
		{
			/*auto normVec = glm::normalize(m_sourceVerticies[i]);
			m_verticies[i] = normVec;
			auto dist = glm::distance(m_sourceVerticies[i], { 0, 0, 0 });
			float k = 0;
			k = m_currentPickLvl * dist;

			m_verticies[i].x = m_verticies[i].x * k;
			m_verticies[i].y = m_verticies[i].y * k;
			m_verticies[i].z = m_verticies[i].z * k;*/
		}
	}
	
	private void setSpeed()
	{
		int sign = TYPE_COEFFICIENT[m_type.ordinal()] - m_currentPickLvl < 0 ? -1 : 1;
		m_animSpeed = ANIMATION_SPEED * sign;
	}

	public void nextType() {
		typeWasChanged = true;
		switch (m_type)
		{
		case DODECAHEDRON:
			m_type = TypeShapes.PENTAKIS;
			break;
		case PENTAKIS:
			m_type = TypeShapes.STAR;
			break;
		case STAR:
			m_type = TypeShapes.DODECAHEDRON;
			break;
		default:
			break;
		}
		setSpeed();
	}
	
	/*private getColoroid() 
	{
		long count = 0;
		long current = 0;
		long typeCounter = 3;
		if (m_type == TypeShapes.DODECAHEDRON)
		{
			typeCounter *= 5;
		}
		auto func = [count, current, typeCounter]() mutable
		{
			auto color = COLORS[current];
			count++;
			if (count >= typeCounter)
			{
				current = (current + 1) % COLORS.size();
				count = 0;
			}
			return color;
		};
		return func;
	}*/
	
	final public Vector3f[] getVerticies() {
		return m_verticies;
	}
	
	final public int[] getFaces() {
		return m_pentakisFaces;
	}
	

	private void InitPentakis()
	{
		m_verticies = PENTAKIS_VERTICIES;
		for (int[] it : POLIGONS_VERTICIES)
		{
			writeIntoVerticles(it, m_verticies);
			//writeIntoFaces(m_pentakisFaces, it, m_verticies.length - 1);
		}
		//m_sourceVerticies = m_verticies;
	}
	
	private Vector3f getCenterOfPoints(final Vector3f p1,final Vector3f p2)
	{
		return new Vector3f((p1.x + p2.x) / 2, (p1.y + p2.y) / 2, (p1.z + p2.z) / 2);
	}
	
	private void writeIntoVerticles(final int[] poligon, Vector3f[] verticles)
	{
		//Vector3f point = verticles[poligon[3]];
		//float length = glm.distance(getCenterOfPoints(verticles[poligon[0]], verticles[poligon[1]]), verticles[poligon[3]]);
		//float radius = glm::distance(verticles[poligon[0]], verticles[poligon[1]]) / (2 * std::sin(0.2 * M_PI));
		//Vector3f vec = getCenterOfPoints(verticles[poligon[0]], verticles[poligon[1]]) - point;
	//	float k = radius / length;
	//	point.x = point.x + vec.x * k;
	//	point.y = point.y + vec.y * k;
	//	point.z = point.z + vec.z * k;

		verticles[verticles.length] = (point);
	}
	
	
	private void writeIntoFaces(final int[] faces,final int[] poligon, long vertexNum)
	{
		for (long i = 0; i < poligon.length; ++i)
		{
			faces[faces.length] = (poligon[(int) i]);
			if (i + 1 != poligon.length)
			{
				faces[faces.length] = (poligon[(int) (i + 1)]);
			}
			else
			{
				faces[faces.length] = (poligon[0]);
			}
			faces[faces.length] = (int) (vertexNum);
		}
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
