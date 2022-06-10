import org.joml.Vector2f;

public class GameManager {
    private Renderer renderer;
    private Sprite background;
    private Sprite card;
    private Sprite chipOne;
    private Sprite chipTwo;
    private Sprite chipFive;
    private Sprite chipTen;
    private Sprite chipTwentyFive;
    private Sprite chipHundred;

    private void loadAssets()
    {
        AssetManager.addShader("default", "shaders/vertexShader.glsl", "shaders/fragmentShader.glsl");
        AssetManager.addShader("spritesheet", "shaders/sprVertexShader.glsl", "shaders/sprFragShader.glsl");
        AssetManager.addTexture("table", "images/table.png");
        AssetManager.addTexture("cardFront", "images/card_front.png");
        AssetManager.addTexture("cardBack", "images/card_back.png");
        AssetManager.addTexture("card", "images/card.png");
        AssetManager.addTexture("chipOne", "images/chip_one.png");
        AssetManager.addTexture("chipFive", "images/chip_five.png");
        AssetManager.addTexture("chipTen", "images/chip_ten.png");
        AssetManager.addTexture("chipTwentyFive", "images/chip_twentyfive.png");
        AssetManager.addTexture("chipHundred", "images/chip_hundred.png");
    }

    public void init()
    {
        loadAssets();
        renderer = new Renderer();

        background = new Sprite(AssetManager.getTexture("table"),
                          1280*0.5f, 720*0.5f,
                          1280, 720,
                          0);
        card = new Sprite(AssetManager.getTexture("card"),
                    700, 400,
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
    }


    public void update(float deltaTime)
    {
        card.getTransform().rotation += 0.02f;
    }

    public void render()
    {
        renderer.setShader(AssetManager.getShader("default"));
        renderer.render(background);
        renderer.render(chipOne);
        renderer.render(chipTwo);
        renderer.render(chipFive);
        renderer.render(chipTen);
        renderer.render(chipTwentyFive);
        renderer.render(chipHundred);
        renderer.setShader(AssetManager.getShader("spritesheet"));
        renderer.render(card);

    }
}
