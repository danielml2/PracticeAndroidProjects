package me.danielml.schoolmemorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;
    private Button playBtn;

    private EditText player1NameInput;
    private EditText player2NameInput;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = (Button) findViewById(R.id.backBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(this);

        player1NameInput = (EditText) findViewById(R.id.playerName1Input);
        player2NameInput = (EditText) findViewById(R.id.playerName2Input);

        this.tts = new TextToSpeech(getApplicationContext(), (i) -> {
            if(i == TextToSpeech.SUCCESS)
                tts.setLanguage(Locale.ENGLISH);
        });

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == playBtn.getId()) {
            String name1Input = player1NameInput.getText().toString().trim();
            String name2Input = player2NameInput.getText().toString().trim();

            if(name1Input.length() <= 0 || name2Input.length() <= 0 || name1Input.equals(name2Input)) {
                new AlertDialog.Builder(this)
                        .setMessage("Invalid names input! Names can't be equal to one another or 0 characters long")
                        .setNeutralButton("OK", (dialog, which) -> { })
                        .setTitle("Error!")
                        .setIcon(R.drawable.error)
                        .show();
            } else {
                Intent intent = new Intent(this, GameActivity.class);

                tts.speak("Enjoy and Good Luck! " + name1Input + " and " + name2Input, TextToSpeech.QUEUE_FLUSH, null);

                intent.putExtra("player1Name", player1NameInput.getText().toString());
                intent.putExtra("player2Name", player2NameInput.getText().toString());

                startActivity(intent);
            }

        }
    }
}