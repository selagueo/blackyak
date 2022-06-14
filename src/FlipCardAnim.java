import org.joml.Math;
import org.joml.Vector2f;

public class FlipCardAnim extends Animation {
    public FlipCardAnim(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void update(float deltaTime) {
        if (isPlaying) {
            float newRotationY = Transform.lerp(180.0f, 0.0f, timer);
            sprite.getTransform().rotationY = Math.toRadians(newRotationY);
            timer += deltaTime * 4;
            if (timer >= 1.0f) {
                // end animation
                isPlaying = true;
                timer = 1.0f;
                isDone = true;
            }
        }
    }

    @Override
    public void play() {
        isPlaying = true;
        this.timer = 0.0f;
        sprite.getTransform().rotationY = Math.toRadians(180.0f);
    }
}
