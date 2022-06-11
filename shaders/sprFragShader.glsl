#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in vec3 fNorm;

uniform sampler2D texture0;
uniform vec2 tile; // row / colum
uniform vec2 ratio; // tileSize / textureSize

out vec4 color;

void main()
{
    vec2 finalTile = tile;
    float projection = dot(vec3(0, 0, 1), fNorm);
    if(projection < 0)
    {
        finalTile = vec2(1, 0);
    }
    vec2 texCoord = (fTexCoords * ratio) + (finalTile * ratio);
    color = texture(texture0, texCoord) * fColor;
}