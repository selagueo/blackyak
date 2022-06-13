import org.joml.Math;
import org.joml.Vector2f;

public class DealCardFaceDownAnim extends Animation {
    public DealCardFaceDownAnim(Sprite sprite, Vector2f start, Vector2f target) {
        super(sprite, start, target);
    }
    @Override
    public void update(float deltaTime) {
        if(isPlaying)
        {
            Vector2f newPosition = Transform.lerpV2(start, target, timer);
            sprite.getTransform().position = newPosition;
            float newRotationZ = Transform.lerp(180.0f, 0.0f, timer);
            sprite.getTransform().rotationZ = Math.toRadians(newRotationZ);
            timer += deltaTime;
            if(timer >= 1.0f) {
                // end animation
                sprite.getTransform().position = target;
                timer = 1.0f;
                isPlaying = true;
                isDone = true;
            }
        }
    }

    @Override
    public void play() {
        isPlaying = true;
        this.timer = 0.0f;
        sprite.getTransform().rotationZ = Math.toRadians(180.0f);
        sprite.getTransform().rotationY = Math.toRadians(180.0f);
    }
}
