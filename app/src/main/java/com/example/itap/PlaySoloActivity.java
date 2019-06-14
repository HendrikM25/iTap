package com.example.itap;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlaySoloActivity extends AppCompatActivity {

    ImageView tapTarget;
    TextView score, time;
    int currentScore;
    int timeLeft;
    boolean gameStarted;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_solo);

        //initialize elements
        gameStarted = false;

        tapTarget = findViewById(R.id.tapTarget);
        tapTarget.setOnClickListener(tapTargetListener);

        score = findViewById(R.id.score);

        time = findViewById(R.id.timeLeft);
        timeLeft = 10;

        //reset elements
        setScore(0);
        setTime(timeLeft);
    }

    private View.OnClickListener tapTargetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentScore += 1;
            setScore(currentScore);

            if(!gameStarted) {
                startTimer(timeLeft * 1000, 1000);
                gameStarted = true;
            }
        }
    };

    //TODO: implement CountDownTimer
    private void startTimer(int time, int interval) {
        timer = new CountDownTimer(time, interval) {

            public void onTick(long millisUntilFinished) {
                setTime((int) millisUntilFinished/1000);
            }

            public void onFinish() {
                setTime(0);
                tapTarget.setOnClickListener(null);
                Toast.makeText(getApplicationContext(), "Game ended!", Toast.LENGTH_SHORT).show();

                //TODO: check for a new highscore and add it to highscore data
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        finish();
    }

    private void setScore(int newScore) {
        currentScore = newScore;
        String newText = String.valueOf(newScore);
        score.setText(newText);
    }

    private void setTime(int timeLeft) {
        this.timeLeft = timeLeft;
        String newText = String.valueOf(timeLeft);
        time.setText(newText);
    }



}
