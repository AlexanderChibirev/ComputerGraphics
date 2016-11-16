import javax.vecmath.Vector3f;

import com.jogamp.opengl.GL2;


class Vertex
{
    public Vertex(Vector3f vec, Vector3f colors) {
    	this.pos = vec;
    	this.color = colors;
	}
	public Vector3f pos;
    public Vector3f color;
};

public class CubeMalova {
	final byte CUBE_INDICIES[] = {
		    0, 1, 2,
		    0, 2, 3,
		    2, 1, 5,
		    2, 5, 6,
		    3, 2, 6,
		    3, 6, 7,
		    0, 3, 7,
		    0, 7, 4,
		    1, 0, 4,
		    1, 4, 5,
		    6, 5, 4,
		    6, 4, 7,
		};
	final Vector3f DARK_GREEN = new Vector3f(0.05f, 0.45f, 0.1f);
	final Vector3f LIGHT_GREEN = new Vector3f(0.1f, 0.8f, 0.15f);
	final Vertex CUBE_VERTICIES[] = {
			new Vertex(new Vector3f(-1, +1, -1), DARK_GREEN),
			new Vertex(new Vector3f(+1, +1, -1), DARK_GREEN),
			new Vertex(new Vector3f(+1, +1, -1), DARK_GREEN),
			new Vertex(new Vector3f(-1, -1, -1), DARK_GREEN),
			new Vertex(new Vector3f(-1, +1, +1), LIGHT_GREEN),
			new Vertex(new Vector3f(+1, +1, +1), LIGHT_GREEN),
			new Vertex(new Vector3f(+1, -1, +1), LIGHT_GREEN),
			new Vertex(new Vector3f(-1, -1, +1), LIGHT_GREEN)
	};
	
	public void draw(GL2 gl)
	{
		gl.glBegin(GL2.GL_TRIANGLES);
	    for (byte i : CUBE_INDICIES)
	    {
	        final Vertex v = CUBE_VERTICIES[i];
	        gl.glColor3f(v.color.x, v.color.y, v.color.z);
	        gl.glVertex3f(v.pos.x, v.pos.y, v.pos.z);
	    }
	    gl.glEnd();
	}
}
