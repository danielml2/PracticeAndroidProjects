package me.danielml.schoolmemorygame.objects;

import java.util.Random;

import me.danielml.schoolmemorygame.activities.GameActivity;

public class GameManager {

    private int[][] cardGrid;
    private Player player1;
    private Player player2;
    private GameActivity gameScreen;


    public GameManager(Player player1, Player player2, GameActivity gameScreen) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameScreen = gameScreen;
        restart();

    }

    public void restart() {
        player1.resetCards();
        player2.resetCards();
        fillCardGrid();
    }

    public int getCard(int row, int col) {
        return cardGrid[row][col];
    }

    public void fillCardGrid() {
        this.cardGrid = new int[4][5];

        int[] cardIDs = {0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9};
        shuffle(cardIDs);

        int numIndex = 0;
        for(int i = 0; i < this.cardGrid.length; i++) {
            for(int j = 0; j < this.cardGrid[i].length; j++) {
                cardGrid[i][j] = cardIDs[numIndex];
                numIndex++;
            }
        }

    }

    public void shuffle(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }

}
