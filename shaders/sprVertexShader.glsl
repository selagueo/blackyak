#version 330 core

layout (location=0) in vec3 aPos;
layout (location=1) in vec2 aTex;

uniform mat4 uProj;
uniform mat4 uView;
uniform mat4 uWorld;
uniform vec4 uColor;

out vec4 fColor;
out vec2 fTexCoords;
out vec3 fNorm;

void main()
{
    fColor = uColor;
    fTexCoords = aTex;
    fNorm = mat3(transpose(inverse(uWorld))) * vec3(0, 0, 1);
    gl_Position = uProj * uView * uWorld * vec4(aPos, 1.0f);
}
