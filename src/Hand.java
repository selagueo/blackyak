import org.joml.Vector2f;

import java.awt.image.CropImageFilter;
import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private int aceCounter;
    private float timer = 0.0f;

    public Hand()
    {
        this.hand = new ArrayList<Card>();
        aceCounter = 0;
    }

    public void getCard (Card card, Animation animation) {
        //Card card = deck.getCard();
        Animation anim = new Animation(card.getSprite(), card.getSprite().getTransform().position, new Vector2f(1280.0f*0.5f, 720.0f*0.5f));
        card.setAnimation(animation);
        hand.add(card);
    }
    public void clear() {
        this.hand.clear();
        this.timer = 0;
    }

    public int getValue () {
        int value = 0;
        aceCounter = 0;
        for (Card card : this.hand) {
            switch (card.RANK) {
                case ACE -> value += getAceValue();
                case TWO -> value += 2;
                case THREE -> value += 3;
                case FOUR -> value += 4;
                case FIVE -> value += 5;
                case SIX -> value += 6;
                case SEVEN -> value += 7;
                case EIGHT -> value += 8;
                case NINE -> value += 9;
                case TEN, JACK, QUEEN, KING -> value += 10;
            }
        }
        while ((value > 21) && (aceCounter >= 1)) {
            value -= aceCounter;
            aceCounter--;
        }
        return value;
    }

    public void update(float deltaTime)
    {
        for(int i = 0; i < hand.size(); ++i)
        {
            if(hand.size() == 2)
            {
                if(timer < 1.0f) {
                    if (hand.get(0).getAnimation() != null) {
                        if(!hand.get(0).getAnimation().isPlaying()) {
                            hand.get(0).getAnimation().play();
                        }
                    }
                }
                else if(timer > 2.0f)
                {
                    if (hand.get(1).getAnimation() != null) {
                        if(!hand.get(1).getAnimation().isPlaying()) {
                            hand.get(1).getAnimation().play();
                        }
                    }
                }
                timer += deltaTime;
            }
            hand.get(i).update(deltaTime);
        }
    }

    public void render() {
        for (Card card : hand) {
            card.render();
        }
    };

    private int getAceValue() {
        aceCounter++;
        return  11;
    }

}
