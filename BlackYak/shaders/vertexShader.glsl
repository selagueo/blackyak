#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec2 aTex;

uniform mat4 uProj;
uniform mat4 uView;
uniform mat4 uWorld;
uniform vec4 uColor;

out vec4 fColor;
out vec2 fTexCoords;

void main()
{
    fColor = uColor;
    fTexCoords = aTex;
    gl_Position = uProj * uView * uWorld * vec4(aPos, 1.0f);
}