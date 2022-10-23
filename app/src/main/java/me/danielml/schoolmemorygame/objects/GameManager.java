package me.danielml.schoolmemorygame.objects;

import android.util.Log;

import java.util.Random;

import me.danielml.schoolmemorygame.activities.GameActivity;

public class GameManager {

    private int[][] cardGrid;
    private Player player1;
    private Player player2;
    private GameActivity gameScreen;

    private int playerTurn = 0;
    private int gameTurns = 0;

    public GameManager(Player player1, Player player2, GameActivity gameScreen) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameScreen = gameScreen;
        restart();
    }

    public void restart() {
        player1.resetCards();
        player2.resetCards();
        this.playerTurn = new Random().nextInt(2);
        this.gameTurns = 0;
        fillCardGrid();
    }

    public void showCardsMatrix() {
        String logText = "";
        for(int row = 0; row < cardGrid.length; row++) {
            for(int col = 0; col < cardGrid[row].length; col++) {
                logText += col + " ";
            }
            logText += "\n";
        }
        Log.d("deeznuts", logText);
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

    public void scoreCard(int cardRow, int cardCol) {
        Player player = playerTurn == 0 ? player1 : player2;
        player.addCard(getCard(cardRow, cardCol));
    }

    public Player currentPlayer() {
        return playerTurn == 0 ? player1 : player2;
    }

    public void nextTurn() {
        playerTurn = (playerTurn + 1) % 2;
        gameTurns++;
    }

    public int getPlayer1Score() {
        return player1.getScore();
    }

    public int getPlayer2Score() {
        return player2.getScore();
    }

    public int getTurn() {
        return playerTurn;
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

    public boolean areSameCards(int firstRow, int firstCol, int secondRow, int secondCol) {
        return this.cardGrid[firstRow][firstCol] == this.cardGrid[secondRow][secondCol];
    }

}
