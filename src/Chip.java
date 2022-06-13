import org.joml.Vector2f;

public class Chip {
    enum Type {
        _100(0), _25(1), _10(2), _5(3), _1(4);
        private int value;
        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private Type type;
    private Sprite sprite;
    public static final int scale = 50;

    public Chip(Type type, Texture texture) {
        this.type = type;
        this.sprite = new Sprite(texture, 0, 0, scale, scale, 0);
    }

    public Type getType() {
        return this.type;
    }

    public void render(int x, int y) {
        this.sprite.getTransform().position = new Vector2f(x, y);
        sprite.render();
    }
}
