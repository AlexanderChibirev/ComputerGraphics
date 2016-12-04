

mat2 rotate2d(float angle)
{
    return mat2( cos(angle), -sin(angle),
                sin(angle), cos(angle));
}


bool PointIsOnTheLeft(vec2 p0, vec2 p1, vec2 p)
{
    vec2 p0p1 = p1 - p0;
    // find the orthogonal vector to p0p1
    vec2 n = vec2(-p0p1.y, p0p1.x);
    // Find the dot product between n and (p - p0)
    return dot(p - p0, n) > 0.0;
}

bool PointIsInsideTriangle(vec2 p0, vec2 p1, vec2 p2, vec2 p)
{
    return PointIsOnTheLeft(p0, p1, p) &&
           PointIsOnTheLeft(p1, p2, p) &&
           PointIsOnTheLeft(p2, p0, p);
}

bool PointIsInsideStart(vec2 center, float longSide, float shortSide, vec2 p, float angle)
{
	p -= center;
	p *= rotate2d(angle);
	p += center;
	float M_PI = 3.1415926535897932384626433832795;
	float step = M_PI * 36.0 / 180.0;
	vec2 p0;
	vec2 p1;
	for (int i = 0; i < 10; i++)
	{
		if (i % 2 == 0)
		{
			p0 = vec2(center.x + longSide * sin(i * step), center.y + longSide * cos(i * step));
			p1 = vec2(center.x + shortSide * sin(((i + 1) % 10) * step), center.y + shortSide * cos(((i + 1) % 10) * step));

		}
		else
		{
			p0 = vec2(center.x + shortSide * sin(i * step), center.y + shortSide * cos(i * step));
			p1 = vec2(center.x + longSide * sin(((i + 1) % 10) * step), center.y + longSide * cos(((i + 1) % 10) * step));
			
		}
		if (PointIsInsideTriangle(center, p1, p0, p))
		{
			return true;
		}

	}
	return false;
}

void main()
{
	float M_PI = 3.1415926535897932384626433832795;
    vec2 pos = gl_TexCoord[0].xy;
	vec2 p0 = vec2(1, 2);
	vec2 p1 = vec2(1.8, 1.3);
	vec2 p2 = vec2(2.1, 1.8);
	vec2 p3 = vec2(2.1, 2.3);
	vec2 p4 = vec2(1.8, 2.8);
	float longSideBig = 0.5;
	float shortSideBig = 0.2;
	float longSideSmall = 0.18;
	float shortSideSmall = 0.06;

	if (PointIsInsideStart(p0, longSideBig, shortSideBig, pos, 0) || 
		PointIsInsideStart(p1, longSideSmall, shortSideSmall, pos, M_PI * 20.0 / 180.0) ||
		PointIsInsideStart(p2, longSideSmall, shortSideSmall, pos, 0.0) ||
		PointIsInsideStart(p3, longSideSmall, shortSideSmall, pos, M_PI * -20.0 / 180.0) ||
		PointIsInsideStart(p4, longSideSmall, shortSideSmall, pos, M_PI * 20.0 / 180.0))
    {
        gl_FragColor = vec4(1.0, 1.0, 0.0, 1.0);
    }
	else
	{
		gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	}
}