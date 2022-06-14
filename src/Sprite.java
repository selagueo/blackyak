import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;

public class Sprite {
    private Renderer renderer;

    private Transform transform;
    private Texture texture;
    private Vector4f color;
    private Vector2f ratio;
    Vector2f spritesheetPos;

    public Sprite(Texture texture, float xPox, float yPos, float xScale, float yScale, float rotation) {
        this.transform = new Transform(new Vector2f(xPox, yPos), new Vector2f(xScale, yScale), rotation);
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.ratio = new Vector2f(0.0f, 0.0f);
        this.spritesheetPos = new Vector2f();
    }

    public Sprite(Texture texture, float xPox, float yPos, float xScale, float yScale, float rotation, float tileWidth, float tileHeight) {
        this.transform = new Transform(new Vector2f(xPox, yPos), new Vector2f(xScale, yScale), rotation);
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.ratio = new Vector2f(0.0f, 0.0f);
        this.ratio.x = (float) tileWidth / (float) (texture.getWidth());
        this.ratio.y = (float) tileHeight / (float) texture.getHeight();
        this.spritesheetPos = new Vector2f();
    }

    public void render() {
        Renderer renderer = Renderer.getInstance();
        Matrix4f projectionMat = new Matrix4f();
        projectionMat.ortho(0, 1280, 0, 720, 0, 1000);
        renderer.getShader().uploadMat4f("uProj", projectionMat);

        // bind view matrix to the shader
        Matrix4f viewMat = new Matrix4f();
        Vector3f position = new Vector3f(0, 0, 100);
        Vector3f front = new Vector3f(position.x, position.y, -1);
        Vector3f up = new Vector3f(0, 1, 0);
        viewMat.lookAt(position, front, up);
        renderer.getShader().uploadMat4f("uView", viewMat);

        Matrix4f worldMat = transform.getWorldMatrix();
        renderer.getShader().uploadMat4f("uWorld", worldMat);
        renderer.getShader().uploadVec4f("uColor", color);
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            renderer.getShader().uploadTexture("texture0", 0);
            texture.bind();

            if (ratio.x > 0.0f || ratio.y > 0.0f) {
                renderer.getShader().uploadVec2f("tile", spritesheetPos);
                renderer.getShader().uploadVec2f("ratio", ratio);
            }
        }

        renderer.getShader().use();
        ARBVertexArrayObject.glBindVertexArray(renderer.getVAO());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        ARBVertexArrayObject.glBindVertexArray(0);
        renderer.getShader().detach();
        if (texture != null) {
            texture.detach();
        }
    }

    public Transform getTransform() {

        return this.transform;
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f getRatio() {
        return this.ratio;
    }

    public void setSpritesheetPos(Vector2f spritesheetPos) {
        this.spritesheetPos = spritesheetPos;
    }

    public Vector2f getSpritesheetPos() {
        return this.spritesheetPos;
    }
}
