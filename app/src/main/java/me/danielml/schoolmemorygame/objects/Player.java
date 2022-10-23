package me.danielml.schoolmemorygame.objects;

import java.util.ArrayList;

public class Player {

    private String name;
    private int score;
    private ArrayList<Integer> cards;

    public Player(String name) {
        this.score = 0;
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void addCard(int cardID) {
        cards.add(cardID);
        score++;
    }

    public void resetCards() {
        this.cards.clear();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }
}
