uniform float TWIST;

void main()
{
	float ANGLE = gl_Vertex.x;

	float radius = (4.f + sin(ANGLE))
					* (1.f + 0.9f * cos(8.f * ANGLE))
					* (1.f + 0.1f * cos(24.f * ANGLE))
					* (0.5f + 0.05f * cos(140.f * ANGLE));

	float x = radius * cos(ANGLE);
	float y = radius * sin(ANGLE);
	float z = 0.f;
    vec4 twistedCoord = vec4(
		gl_Vertex.x + x * TWIST,
        gl_Vertex.y + y * TWIST,
        gl_Vertex.z,
        gl_Vertex.w
    );

    vec4 position = gl_ModelViewProjectionMatrix * twistedCoord;
    // Transform twisted coordinate
    gl_Position = position;
    gl_FrontColor = (position + vec4(1.0)) * 1;
}