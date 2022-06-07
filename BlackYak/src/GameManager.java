import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

public class GameManager {

    Shader defaultShader = new Shader("shaders/vertexShader.glsl",
                                    "shaders/fragmentShader.glsl");
    Renderer renderer = new Renderer(defaultShader);
    Sprite testSprite = new Sprite(renderer,
                                   new Transform(new Vector2f(0, 0), new Vector2f(100, 100), 0),
                                   new Vector4f(0, 0, 1, 1));

    Sprite testSprite1 = new Sprite(renderer,
                                    new Transform(new Vector2f(200, 0), new Vector2f(100, 100), 0),
                                    new Vector4f(1, 0, 1, 1));

    public GameManager(){
    }

    void init()
    {
        Matrix4f projectionMat = new Matrix4f();
        projectionMat.ortho(-640, 640, -360, 360, 0, 100);
        defaultShader.uploadMat4f("uProj", projectionMat);

        // bind view matrix to the shader
        Matrix4f viewMat = new Matrix4f();
        Vector3f position = new Vector3f(0, 0, 20);
        Vector3f target = new Vector3f(0, 0, -1);
        Vector3f up = new Vector3f(0, 1, 0);
        viewMat.lookAt(position, target.add(position.x, position.y, 0), up);
        defaultShader.uploadMat4f("uView", viewMat);
    }


    void update(float deltaTime)
    {
        testSprite1.getTransform().rotation += 0.02f;
    }

    void render()
    {

        testSprite.render();
        testSprite1.render();
    }
}
