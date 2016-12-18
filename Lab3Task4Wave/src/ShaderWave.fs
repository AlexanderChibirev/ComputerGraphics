uniform vec2 center; // Screen resolution
uniform float time; // time in seconds
uniform sampler2D tex0; // scene buffer
uniform sampler2D tex1; // scene buffer

void main(void)
{
	vec2 tc = gl_TexCoord[0].xy ;
	vec2 p =   2 *(  center - tc) ;
	float len = length(p);
	vec2 uv = tc + (p/(len*12.0-time*4.0))*sin(len*12.0-time*4.0)*0.05;
	vec3 col;
	if (length(-1.0 + 2.0 * (center - uv)) <  len*12.0-time*4.0)
	{

		gl_FragColor = vec4(texture2D(tex0,uv).xyz,1.0);  
	}
	else
	{
		float alpha;
		if (time > 5.f)
		{
			alpha = 1.f;
		}
		else
		{
			alpha = time / 5.f;
		}
		vec4 c1 = texture(tex0, uv);
		vec4 c2 = texture(tex1, uv);
		gl_FragColor = mix(c1,c2, alpha);
	}
 
}