package me.danielml.schoolmemorygame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import me.danielml.schoolmemorygame.R;
import me.danielml.schoolmemorygame.objects.GameManager;
import me.danielml.schoolmemorygame.objects.Player;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView player1NameTv;
    private TextView player2NameTv;

    private TextView player1Score, player2Score;

    private GameManager gameManager;

    private ImageButton[][] cardGridButtons;
    private final int[] imageIDs = {R.drawable.deadsmall, R.drawable.emoji1, R.drawable.emoji2, R.drawable.emoji3, R.drawable.emoji4, R.drawable.emoji5,
                                R.drawable.goofy, R.drawable.nerdsmall, R.drawable.rock1, R.drawable.rockeyebrow};

    private Button exitGameBtn, restartBtn, showCardsBtn;

    private boolean firstClick = true;
    private int firstRow = 0, firstCol = 0;

    private String player1Name = "Player 1", player2Name = "Player 2";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.player1NameTv = findViewById(R.id.player1NameTv);
        this.player2NameTv =  findViewById(R.id.player2NameTv);
        this.exitGameBtn = findViewById(R.id.exitGameBtn);
        this.restartBtn = findViewById(R.id.restartBtn);
        this.showCardsBtn = findViewById(R.id.cardsButton);
        this.player1Score = findViewById(R.id.player1ScoreTv);
        this.player2Score = findViewById(R.id.playerScore2Tv);

        Intent intent = getIntent();

        exitGameBtn.setOnClickListener(this);
        restartBtn.setOnClickListener(this);
        showCardsBtn.setOnClickListener(this);


        if(intent != null) {
            Bundle extras = intent.getExtras();
            player1Name = extras.getString("player1Name");
            player2Name = extras.getString("player2Name");
        }

        player1NameTv.setText(player1Name);
        player2NameTv.setText(player2Name);

        this.gameManager = new GameManager(new Player(player1Name), new Player(player2Name), this);
        this.cardGridButtons = new ImageButton[4][5];
        for(int i = 0; i < cardGridButtons.length; i++) {
            for(int j = 0; j < cardGridButtons[i].length; j++) {
                String stringID = "img" + i + j;
                int resID = getResources().getIdentifier(stringID, "id", getPackageName());
                cardGridButtons[i][j] = findViewById(resID);
                cardGridButtons[i][j].setOnClickListener(this);
            }
        }
        updateViews();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == exitGameBtn.getId()) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

        } else if(v.getId() == restartBtn.getId()){
            gameManager.restart();
            for(int row = 0; row < cardGridButtons.length; row++) {
                for(int col = 0; col < cardGridButtons[row].length; col++) {
                    cardGridButtons[row][col].setImageResource(R.drawable.susdrip);
                    cardGridButtons[row][col].setVisibility(View.VISIBLE);
                }
            }
            updateViews();
        }else if(v.getId() == showCardsBtn.getId())
            initShowCardsDialog();

        String stringID = getResources().getResourceEntryName(v.getId());
        if(stringID.contains("img")) {
                String rowColStr = stringID.split("img")[1];
                int row = Integer.parseInt(String.valueOf(rowColStr.toCharArray()[0]));
                int col = Integer.parseInt(String.valueOf(rowColStr.toCharArray()[1]));

                cardGridButtons[row][col].setImageResource(imageIDs[gameManager.getCard(row, col)]);
                cardGridButtons[row][col].setClickable(false);
            Toast.makeText(getApplicationContext(), row + "," + col, Toast.LENGTH_LONG).show();
                if(firstClick) {
                    firstRow = row;
                    firstCol = col;
                    firstClick = false;
                } else {
                    boolean turnClickableBack = true;
                    if(gameManager.areSameCards(firstRow, firstCol, row, col)) {
                        cardGridButtons[firstRow][firstCol].setVisibility(View.INVISIBLE);
                        cardGridButtons[row][col].setVisibility(View.INVISIBLE);
                        turnClickableBack = false;
                        gameManager.scoreCard(row, col);
                    } else {
                        Handler handler = new Handler();
                        setButtonsState(false);
                        handler.postDelayed(() -> {
                            cardGridButtons[firstRow][firstCol].setImageResource(R.drawable.susdrip);
                            cardGridButtons[row][col].setImageResource(R.drawable.susdrip);
                            setButtonsState(true);
                        }, 1500);
                    }
                    gameManager.nextTurn();
                    firstClick = true;
                    updateViews();
            }

        }
    }

    public void updateViews() {
        String player1Text = "" + gameManager.getPlayer1Score();
        String player2Text = "" + gameManager.getPlayer2Score();
        player1Score.setText(player1Text);
        player2Score.setText(player2Text);

        if(gameManager.getTurn() == 0)
        {
            player1NameTv.setBackgroundColor(Color.GREEN);
            player2NameTv.setBackgroundColor(Color.TRANSPARENT);
        } else {
            player2NameTv.setBackgroundColor(Color.GREEN);
            player1NameTv.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setButtonsState(boolean clickEnabled) {
        for(int row = 0; row < cardGridButtons.length; row++) {
            for(int col = 0; col < cardGridButtons[row].length; col++) {
                cardGridButtons[row][col].setClickable(clickEnabled);
            }
        }
    }

    public void initShowCardsDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.player_hand_layout);
        dialog.setCancelable(true);

        Player currentPlayer = gameManager.currentPlayer();
        dialog.setTitle("Your Card's Hand");

        TextView dialogPlayerName = dialog.findViewById(R.id.dialogPlayerName);
        dialogPlayerName.setText(currentPlayer.getName());

        Button exitDialog = dialog.findViewById(R.id.dialogBackBtn);
        exitDialog.setOnClickListener((v) -> dialog.cancel());

        ImageButton[] cardButtons = new ImageButton[10];
        for(int i = 0; i < cardButtons.length; i++) {
            String stringID = "imgCard" + i ;
            int resID = getResources().getIdentifier(stringID, "id", getPackageName());
            cardButtons[i] = dialog.findViewById(resID);
            cardButtons[i].setVisibility(View.INVISIBLE);
        }

        for(int i = 0; i < currentPlayer.getCards().size(); i++) {
            cardButtons[i].setImageResource(imageIDs[currentPlayer.getCards().get(i)]);
            cardButtons[i].setVisibility(View.VISIBLE);
        }

        dialog.show();


    }


}