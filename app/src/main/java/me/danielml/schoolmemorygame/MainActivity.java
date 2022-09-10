package me.danielml.schoolmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;
    private Button playBtn;

    private EditText player1NameInput;
    private EditText player2NameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = (Button) findViewById(R.id.backBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);

        player1NameInput = (EditText) findViewById(R.id.playerName1Input);
        player2NameInput = (EditText) findViewById(R.id.playerName2Input);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == playBtn.getId()) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("player1Name", player1NameInput.getText());
            intent.putExtra("player2Name", player2NameInput.getText());

            startActivity(intent);
        }
    }
}