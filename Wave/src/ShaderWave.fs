uniform float posMouseX; // Screen resolution
uniform float posMouseY;
uniform float time; // time in seconds
uniform sampler2D tex0; // scene buffer
uniform sampler2D tex1; // scene buffer


void main(void)
{
	vec2 tc = gl_TexCoord[0].xy ;
	vec2 posMouse = vec2(posMouseX, posMouseY);
	vec2 p = 4 * (posMouse - tc); //конвентируем позицию мышки
	float len = length(p); //длина вектора 
	vec2 uv = tc + (p / (len * 12.0 - time * 4.0)) //uv- кординаты на текстуре
		 * sin(len * 12.0 - time *4.0)
		 * 0.1f; //0.1f - коэфициент силы волны
	if (length(-1.0f + 2.0f * (posMouse - uv)) <  len * 12.0f - time * 4.0f) //
	{
		gl_FragColor = vec4(texture2D(tex0, uv).xyz, 1.0f); 
		//gl_FragColor = texture(tex1, vec2(posMouseX, posMouseY)).rgba; //берет поцию цвета мыши
	}
	else
	{
		float coefInterpolate;
		if (time > 5.0f)
		{
			coefInterpolate = 1.0f;
		}
		else
		{
			coefInterpolate = time / 5.0f;
		}
		vec4 startInterpolate = texture(tex0, uv);
		vec4 endInterpolate = texture(tex1, uv);
		gl_FragColor = mix(startInterpolate, endInterpolate, coefInterpolate); //линейна€ интерпол€ци€
	}
 
}