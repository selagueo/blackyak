#version 330 core

in vec4 fColor;
in vec2 fTexCoords;

uniform sampler2D texture0;

out vec4 color;

void main()
{
    color = texture(texture0, fTexCoords) * fColor;
}