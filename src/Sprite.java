import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;

public class Sprite {
    private Renderer renderer;

    private Transform transform;
    private Texture texture;
    private Vector4f color;
    private Vector2f ratio;
    Vector2f spritesheetPos;

    public Sprite(Texture texture, float xPox, float yPos, float xScale, float yScale, float rotation)
    {
        this.transform = new Transform(new Vector2f(xPox, yPos), new Vector2f(xScale, yScale), rotation);
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.ratio = new Vector2f(0.0f, 0.0f);
        this.spritesheetPos = new Vector2f();
    }
    public Sprite(Texture texture, float xPox, float yPos, float xScale, float yScale, float rotation, float tileWidth, float tileHeight)
    {
        this.transform = new Transform(new Vector2f(xPox, yPos), new Vector2f(xScale, yScale), rotation);
        this.color = new Vector4f(1, 1, 1, 1);
        this.texture = texture;
        this.ratio = new Vector2f(0.0f, 0.0f);
        this.ratio.x = (float)tileWidth / (float)(texture.getWidth());
        this.ratio.y = (float)tileHeight / (float)texture.getHeight();
        this.spritesheetPos = new Vector2f();
    }

    public Transform getTransform()
    {

        return this.transform;
    }

    public Vector4f getColor()
    {
        return this.color;
    }

    public Texture getTexture()
    {
        return this.texture;
    }

    public Vector2f getRatio()
    {
        return this.ratio;
    }

    public void setSpritesheetPos(Vector2f spritesheetPos) {
        this.spritesheetPos = spritesheetPos;
    }

    public Vector2f getSpritesheetPos()
    {
        return this.spritesheetPos;
    }
}
