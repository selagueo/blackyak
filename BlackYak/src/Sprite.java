import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;

public class Sprite {
    private Renderer renderer;

    private Transform transform;
    //private Texture terture;
    private Vector4f color;

    public Sprite(Renderer renderer, Transform transform, Vector4f color)
    {
        this.renderer = renderer;
        this.transform = transform;
        this.color = color;
    }

    public void render()
    {
        Matrix4f worldMat = transform.getWorldMatrix();
        renderer.getShader().uploadMat4f("uWorld", worldMat);
        renderer.getShader().uploadVec4f("uColor", color);
        renderer.getShader().use();
        glBindVertexArray(renderer.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        renderer.getShader().detach();
    }

    public Transform getTransform()
    {
        return this.transform;
    }
}
