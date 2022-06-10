import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;

public class Renderer {
    // usefull defines
    private final int POS_SIZE = 2;
    private final int TEX_COORDS_SIZE = 2;
    private final int POS_OFFSET = 0;
    private final int TEX_COORDS_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;

    private final int VERTEX_SIZE = 4;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    // buffers and current shader
    private int VAO;
    private Shader shader;

    public Renderer()
    {
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        // create an array with four vertices
        float[] vertices =  {
                 0.5f,  0.5f,    1.0f, 1.0f,
                 0.5f, -0.5f,    1.0f, 0.0f,
                -0.5f,  0.5f,    0.0f, 1.0f,

                 0.5f, -0.5f,    1.0f, 0.0f,
                -0.5f, -0.5f,    0.0f, 0.0f,
                -0.5f,  0.5f,    0.0f, 1.0f
        };

        int VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // Enable the buffer attribute pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void render(Sprite sprite)
    {
        Matrix4f projectionMat = new Matrix4f();
        projectionMat.ortho(0, 1280, 0, 720, 0, 1000);
        shader.uploadMat4f("uProj", projectionMat);

        // bind view matrix to the shader
        Matrix4f viewMat = new Matrix4f();
        Vector3f position = new Vector3f(0, 0, 100);
        Vector3f front = new Vector3f(position.x, position.y, -1);
        Vector3f up = new Vector3f(0, 1, 0);
        viewMat.lookAt(position, front, up);
        shader.uploadMat4f("uView", viewMat);

        Matrix4f worldMat = sprite.getTransform().getWorldMatrix();
        shader.uploadMat4f("uWorld", worldMat);
        shader.uploadVec4f("uColor", sprite.getColor());
        if(sprite.getTexture() != null) {
            glActiveTexture(GL_TEXTURE0);
            shader.uploadTexture("texture0", 0);
            sprite.getTexture().bind();

            if(sprite.getRatio().x > 0.0f || sprite.getRatio().y > 0.0f)
            {
                shader.uploadVec2f("tile", sprite.getSpritesheetPos());
                shader.uploadVec2f("ratio", sprite.getRatio());
            }
        }

        shader.use();
        ARBVertexArrayObject.glBindVertexArray(VAO);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        ARBVertexArrayObject.glBindVertexArray(0);
        shader.detach();
        if(sprite.getTexture() != null) {
            sprite.getTexture().detach();
        }
    }

    public int getVAO()
    {
        return VAO;
    }

    public Shader getShader()
    {
        return shader;
    }

    public void setShader(Shader shader)
    {
        this.shader = shader;
    }
}
