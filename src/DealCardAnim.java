import org.joml.Math;
import org.joml.Vector2f;

public class DealCardAnim extends Animation {


    public DealCardAnim(Sprite sprite, Vector2f start, Vector2f target) {
        super(sprite, start, target);
    }

    @Override
    public void update(float deltaTime)
    {
        if(isPlaying)
        {
            if(timer <= 0.5f) {
                float zRotationTimer = Transform.reMap(0.0f, 0.5f, 0.0f, 1.0f, timer);
                float newRotationZ = Transform.lerp(180.0f, 0.0f, zRotationTimer);
                sprite.getTransform().rotationZ = Math.toRadians(newRotationZ);
            }
            if(timer >= 0.5f){
                float yRotationTimer = Transform.reMap(0.5f, 1.0f, 0.0f, 1.0f, timer);
                float newRotationY = Transform.lerp(180.0f, 0.0f, yRotationTimer);
                sprite.getTransform().rotationY = Math.toRadians(newRotationY);
            }

            Vector2f newPosition = Transform.lerpV2(start, target, timer);
            sprite.getTransform().position = newPosition;
            timer += deltaTime;
            if(timer >= 1.0f)
            {
                sprite.getTransform().position = target;
                sprite.getTransform().rotationZ = 0.0f;
                sprite.getTransform().rotationY = 0.0f;
                isPlaying = true;
                isDone = true;
            }
        }
    }
    @Override
    public void play()
    {
        isPlaying = true;
        this.timer = 0.0f;
        sprite.getTransform().rotationZ = Math.toRadians(180.0f);
        sprite.getTransform().rotationY = Math.toRadians(180.0f);
    }
}
