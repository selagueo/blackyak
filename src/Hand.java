import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    ArrayList<Chip> chips;
    private int cash;
    private int aceCounter;
    private float timer = 0.0f;
    private int index = 0;

    public Hand()
    {
        this.hand = new ArrayList<Card>();
        this.chips = new ArrayList<Chip>();
        aceCounter = 0;
    }

    public void giveCash(int value) {
        this.cash += value;
    }

    public void setCash(int value) {
        this.cash = value;
    }

    private void addChip(Texture texture, Chip.Type type, int count) {
        for (int i = 0; i < count; i++) {
            this.chips.add(new Chip(type, texture));
        }
    }
    ArrayList<Chip> getChips() {
        this.chips.clear();
        int currentCash = this.cash;
        int chipCount100 = (currentCash / 100);
        currentCash = (currentCash % 100);
        int chipCount25 = (currentCash / 25);
        currentCash = (currentCash % 25);
        int chipCount10 = (currentCash / 10);
        currentCash = (currentCash % 10);
        int chipCount5 = (currentCash / 5);
        currentCash = (currentCash % 5);
        int chipCount1 = (currentCash / 1);
        currentCash = (currentCash % 1);

        addChip(AssetManager.getTexture("chipHundred"), Chip.Type._100, chipCount100);
        addChip(AssetManager.getTexture("chipTwentyFive"), Chip.Type._25, chipCount25);
        addChip(AssetManager.getTexture("chipTen"), Chip.Type._10, chipCount10);
        addChip(AssetManager.getTexture("chipFive"), Chip.Type._5, chipCount5);
        addChip(AssetManager.getTexture("chipOne"), Chip.Type._1, chipCount1);

        return chips;
    }

    public int getCash() {
        return  this.cash;
    }
    public void getCard (Card card, Animation animation) {
        card.setAnimation(animation);
        hand.add(card);
        timer = 0.0f;
    }
    public void clear() {
        this.hand.clear();
        this.timer = 0;
        this.index = 0;
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
            value -= 10;
            aceCounter--;
        }
        return value;
    }

    public void update(float deltaTime)
    {
        if(timer >= 2.0f)
        {
            timer = 0.0f;
        }

        if (index < hand.size() && hand.get(index).getAnimation() != null && timer == 0.0f) {
            if(!hand.get(index).getAnimation().isPlaying()) {
                hand.get(index).getAnimation().play();
                ++index;
            }
        }

        for(int i = 0; i < hand.size(); ++i)
        {
            timer += deltaTime;
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

    public Card getLastCard()
    {
        return hand.get(hand.size() - 1);
    }

    public void changeLastCardAnim(Animation animation) {
        Card lastCard = getLastCard();
        lastCard.setAnimation(animation);
        --index;
    }
}
