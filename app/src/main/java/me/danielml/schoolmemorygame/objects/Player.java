package me.danielml.schoolmemorygame.objects;

public class Player {

    private String name;
    private int score;
    private int[] cards;
    private int cardsCollected;

    public Player(String name) {
        this.score = 0;
        this.name = name;
        this.cards = new int[10];
        this.cardsCollected = 0;
    }

    public void addCard(int cardID) {
        this.cards[cardsCollected] = cardID;
        score++;
        cardsCollected++;
    }

    public void resetCards() {
        this.cards = new int[10];
        this.cardsCollected = 0;
    }

    public int getScore() {
        return score;
    }
}
