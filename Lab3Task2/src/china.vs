void main()
{
    // gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex
    gl_Position = ftransform();
    // Copy texture coordinates from gl_MultiTexCoord0 vertex attribute
    // to gl_TexCoord[0] varying variable
    gl_TexCoord[0] = gl_MultiTexCoord0;
}

struct VS_OUTPUT
{
	float4 Pos  : POSITION;
	float4 Color: COLOR0;
};
 
void vertex_shader(float3 Pos  : POSITION, float4 Color: COLOR0, out VS_OUTPUT Out)
{
	Out.Pos = float4(Pos, 1);
	Out.Color = Color;
}