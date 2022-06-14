import org.joml.Vector2f;

public abstract class Animation {
    protected Sprite sprite;
    protected Vector2f start;
    protected Vector2f target;
    protected float timer;
    protected boolean isPlaying;
    protected boolean isDone;

    public Animation(Sprite sprite) {
        this.sprite = sprite;
        this.start = new Vector2f();
        this.target = new Vector2f();
        this.timer = 0.0f;
        this.isPlaying = false;
        this.isDone = false;
    }

    public Animation(Sprite sprite, Vector2f start, Vector2f target) {
        this.sprite = sprite;
        this.start = start;
        this.target = target;
        this.timer = 0.0f;
        this.isPlaying = false;
        this.isDone = false;
    }

    public abstract void update(float deltaTime);

    public abstract void play();

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isDone() {
        return this.isDone;
    }
}
