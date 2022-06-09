import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
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

    public Renderer(Shader shader)
    {
        this.shader = shader;
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);

        // create an array with four vertices
        float[] vertices =  {
                 0.5f,  0.5f,    1.0f, 1.0f,
                 0.5f, -0.5f,    1.0f, 0.0f,
                -0.5f,  0.5f,    0.0f, 0.0f,
                 0.5f, -0.5f,    0.0f, 0.0f,
                -0.5f, -0.5f,    0.0f, 1.0f,
                -0.5f,  0.5f,    1.0f, 1.0f
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
