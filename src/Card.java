import org.joml.Math;
import org.joml.Vector2f;

public class Card {
    public enum Ranks {
        ACE(0), TWO(1), THREE(2), FOUR(3), FIVE(4), SIX(5), SEVEN(6), EIGHT(7), NINE(8), TEN(9), JACK(10), QUEEN(11), KING(12);
        final int value;

        Ranks(int value) {
            this.value = value;
        }

        int getValue() {
            return this.value;
        }
    }

    public enum Suits {
        CLUBS(0), DIAMONDS(1), HEARTS(2), SPADES(3);
        final int value;

        Suits(int value) {
            this.value = value;
        }

        int getValue() {
            return this.value;
        }
    }

    public final Ranks RANK;
    public final Suits SUIT;
    private boolean faceDown = true;
    private Sprite sprite;
    private Animation animation;

    public Card(Suits suit, Ranks rank) {
        this.RANK = rank;
        this.SUIT = suit;
        this.sprite = new Sprite(AssetManager.getTexture("deck"),
                1280.0f * 0.5f, 720.0f,
                22 * 4, 31 * 4,
                0,
                22, 31);
        sprite.setSpritesheetPos(new Vector2f(RANK.getValue(), SUIT.getValue()));
        sprite.getTransform().rotationZ = Math.toRadians(180.0f);
        sprite.getTransform().rotationY = Math.toRadians(180.0f);
        this.faceDown = true;
    }

    public void setFaceDown(boolean val) {
        this.faceDown = val;
    }

    public String toString() {
        return new String(RANK + " of " + SUIT);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setPosition(float x, float y) {
        sprite.getTransform().position = new Vector2f(x, y);
    }

    public void update(float deltaTime) {
        if (animation != null) {
            animation.update(deltaTime);
        }
    }

    public void render() {
        sprite.render();
    }

    ;

    public Animation getAnimation() {
        return this.animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
