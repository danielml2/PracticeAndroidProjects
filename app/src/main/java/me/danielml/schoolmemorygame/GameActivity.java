package me.danielml.schoolmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gridLayout;
    private TextView player1NameTv;
    private TextView player2NameTv;

    private Button exitGameBtn;

    private String player1Name = "Player 1", player2Name = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.player1NameTv = findViewById(R.id.player1NameTv);
        this.player2NameTv =  findViewById(R.id.player2NameTv);
        this.exitGameBtn = findViewById(R.id.exitGameBtn);

        Intent intent = getIntent();

        exitGameBtn.setOnClickListener(this);


        if(intent != null) {
            Bundle extras = intent.getExtras();
            player1Name = extras.getString("player1Name");
            player2Name = extras.getString("player2Name");
        }

        player1NameTv.setText(player1Name);
        player2NameTv.setText(player2Name);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == exitGameBtn.getId()) {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }

    }
}