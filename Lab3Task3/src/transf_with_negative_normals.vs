varying vec3 n;
varying vec3 v;
uniform float time;


vec2 GetTransformPointMobius(vec2 p)
{
	float M_PI = 3.1415926535897932384626433832795;
	vec2 transformed = p;
	transformed.x = 2 * M_PI * p.x ;
	transformed.y = 2 * p.y - 1.0 ;
	return transformed;
}


vec2 GetTransformPointKlein(vec2 p)
{
	float M_PI = 3.1415926535897932384626433832795;
	vec2 transformed = p;
	transformed.x = 2 * M_PI * p.x ;
	transformed.y = 2 * M_PI * p.y ;
	return transformed;
}

vec3 KleinBottle(vec2 p)
{
	float M_PI = 3.1415926535897932384626433832795;
	vec3 pos = vec3(GetTransformPointKlein(p),0);
	vec3 klein = pos;
	float r =  (1 - cos(pos.x) / 2.0);
	if (pos.x <= M_PI)
	{
		klein.x = 3.0 / 2.0 * cos(pos.x) * (1 + sin(pos.x)) + r * cos(pos.x) * cos(pos.y);
		klein.y = 4.0 * sin(pos.x) + r * sin(pos.x) * cos(pos.y);

	}
	else if ( pos.x > M_PI)
	{
		klein.x = 3.0 / 2.0 * cos(pos.x) * (1 + sin(pos.x)) + r * (cos(pos.y + M_PI));
		klein.y = 4.0 * sin(pos.x);
	}
	klein.z = r * sin(pos.y);
	return klein;
}

vec3 MobiusBand(vec2 p)
{
	vec3 pos = vec3(GetTransformPointMobius(p),0);
	vec3 mobius = pos;
	mobius.x =  (1 + pos.y / 2.0 * cos(pos.x / 2.0)) * cos(pos.x) * 2.0;
	mobius.y =  (1 + pos.y / 2.0 * cos(pos.x / 2.0)) * sin(pos.x) * 2.0;
	mobius.z = pos.y  * sin(pos.x / 2.0);
	return mobius; 
}


vec3 GetPosition(vec2 p)
{
	float alpha = smoothstep(0.0, 1.0, time);
	vec3 pos = ( MobiusBand(p) * alpha) + (KleinBottle(p) * (1 - alpha));
	return pos;
}

void main()
{
	vec3 pos = GetPosition(gl_Vertex);
	v = vec3(gl_ModelViewMatrix * vec4(pos,1));
	vec3 p0 = GetPosition(vec2(gl_Vertex.x - 0.05f, gl_Vertex.y)) - pos;
	vec3 p1 = GetPosition(vec2(gl_Vertex.x, gl_Vertex.y - 0.05f)) - pos;
    vec3 n1 = normalize(gl_NormalMatrix * cross(p0, p1));
	n = - n1;
    vec4 position = gl_ModelViewProjectionMatrix * vec4(pos,1);
    gl_Position = position;


}
