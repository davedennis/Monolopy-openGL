#version 120

uniform sampler2D texture1;
uniform vec4 inputColor;

void main()
{
    vec4 newVertColor =  texture2D(texture1, gl_TexCoord[0].st);

    gl_FragColor = inputColor * newVertColor;
}