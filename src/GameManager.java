import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class GameManager {
    private Sprite background;
    private Sprite card;
    private Sprite chipOne;
    private Sprite chipTwo;
    private Sprite chipFive;
    private Sprite chipTen;
    private Sprite chipTwentyFive;
    private Sprite chipHundred;

    private Animation cardAnim;

    private void loadAssets()
    {
        AssetManager.addShader("default", "shaders/vertexShader.glsl", "shaders/fragmentShader.glsl");
        AssetManager.addShader("spritesheet", "shaders/sprVertexShader.glsl", "shaders/sprFragShader.glsl");
        AssetManager.addTexture("table", "images/table.png");
        AssetManager.addTexture("chipOne", "images/chip_one.png");
        AssetManager.addTexture("chipFive", "images/chip_five.png");
        AssetManager.addTexture("chipTen", "images/chip_ten.png");
        AssetManager.addTexture("chipTwentyFive", "images/chip_twentyfive.png");
        AssetManager.addTexture("chipHundred", "images/chip_hundred.png");
        AssetManager.addTexture("deck", "images/deck.png");
    }

    public void init()
    {
        loadAssets();

        background = new Sprite(AssetManager.getTexture("table"),
                          1280*0.5f, 720*0.5f,
                          1280, 720,
                          0);
        card = new Sprite(AssetManager.getTexture("deck"),
                    1280.0f*0.5f, 720.0f,
                   22*4, 31*4,
                  0,
                 22, 31);
        card.setSpritesheetPos(new Vector2f(0, 0));


        chipOne = new Sprite(AssetManager.getTexture("chipOne"),
                             200, 400,
                             22 * 2.5f, 24 * 2.5f,
                             0);
        chipTwo = new Sprite(AssetManager.getTexture("chipOne"),
                200, 405f,
                22 * 2.5f, 24 * 2.5f,
                0);
        chipFive = new Sprite(AssetManager.getTexture("chipFive"),
                250, 400,
                22 * 2.5f, 24 * 2.5f,
                0);
        chipTen = new Sprite(AssetManager.getTexture("chipTen"),
                300, 400,
                22 * 2.5f, 24 * 2.5f,
                0);
        chipTwentyFive = new Sprite(AssetManager.getTexture("chipTwentyFive"),
                250, 350,
                22 * 2.5f, 24 * 2.5f,
                0);
        chipHundred = new Sprite(AssetManager.getTexture("chipHundred"),
                200, 350,
                22 * 2.5f, 24 * 2.5f,
                0);

        card.getTransform().rotationZ = Math.toRadians(180.0f);
        card.getTransform().rotationY = Math.toRadians(180.0f);

        Vector2f target = new Vector2f(1280.0f * 0.25f,  720.0f * 0.25f);
        Vector2f start = new Vector2f(1280.0f*0.5f, 720.0f);
        cardAnim = new Animation(card, start, target);

    }

    public void update(float deltaTime) {
        Input input = Input.getInstance();
        if (input.getKeyDown(GLFW_KEY_SPACE)) {
            cardAnim.play();
        }
        cardAnim.update(deltaTime);
    }

    public void render()
    {
        Renderer renderer = Renderer.getInstance();
        renderer.setShader(AssetManager.getShader("default"));
        background.render();
        chipOne.render();
        chipTwo.render();
        chipFive.render();
        chipTen.render();
        chipTwentyFive.render();
        chipHundred.render();
        renderer.setShader(AssetManager.getShader("spritesheet"));
        card.render();
    }
}
