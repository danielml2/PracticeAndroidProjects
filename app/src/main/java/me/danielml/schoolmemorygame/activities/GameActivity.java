package me.danielml.schoolmemorygame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import me.danielml.schoolmemorygame.R;
import me.danielml.schoolmemorygame.objects.GameManager;
import me.danielml.schoolmemorygame.objects.Player;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView player1NameTv;
    private TextView player2NameTv;

    private GameManager gameManager;

    private ImageButton[][] cardGridButtons;
    private final int[] imageIDs = {R.drawable.deadsmall, R.drawable.emoji1, R.drawable.emoji2, R.drawable.emoji3, R.drawable.emoji4, R.drawable.emoji5,
                                R.drawable.goofy, R.drawable.nerdsmall, R.drawable.rock1, R.drawable.rockeyebrow};

    private Button exitGameBtn, restartBtn;

    private String player1Name = "Player 1", player2Name = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.player1NameTv = findViewById(R.id.player1NameTv);
        this.player2NameTv =  findViewById(R.id.player2NameTv);
        this.exitGameBtn = findViewById(R.id.exitGameBtn);
        this.restartBtn = findViewById(R.id.restartBtn);

        Intent intent = getIntent();

        exitGameBtn.setOnClickListener(this);
        restartBtn.setOnClickListener(this);


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
                }
            }
        }
        else {
            String stringID = getResources().getResourceEntryName(v.getId());

            String rowColStr = stringID.split("img")[1];
            int row = Integer.parseInt(String.valueOf(rowColStr.toCharArray()[0]));
            int col = Integer.parseInt(String.valueOf(rowColStr.toCharArray()[1]));

            cardGridButtons[row][col].setImageResource(imageIDs[gameManager.getCard(row, col)]);

            Toast.makeText(getApplicationContext(), row + "," + col, Toast.LENGTH_LONG).show();
        }





    }
}