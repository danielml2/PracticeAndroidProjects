package me.danielml.schoolmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.GridLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TextView player1NameTv;
    private TextView player2NameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.player1NameTv = (TextView) findViewById(R.id.player1NameTv);
    }
}