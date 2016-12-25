#version 120 
#extension GL_EXT_geometry_shader4 : enable

void main(void)
{
   //increment variable
   int i;

   //Pass-thru!
   for(i=0; i< gl_VerticesIn; i++){
      gl_Position = gl_PositionIn[i];
      EmitVertex();
   }
   EndPrimitive();

   //New piece of geometry!  We just swizzle the x and y terms
   for(i=0; i< gl_VerticesIn; i++){
      gl_Position = gl_PositionIn[i];
      gl_Position.xy = gl_Position.yx;
      EmitVertex();
   }
   EndPrimitive();
}