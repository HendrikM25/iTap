package com.example.itap;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    ImageView targetBlue, targetOrange, targetPurple, targetGreen;
    String currentTarget = "green";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initialize elements
        targetBlue = findViewById(R.id.targetBlue);
        targetOrange = findViewById(R.id.targetOrange);
        targetPurple = findViewById(R.id.targetPurple);
        targetGreen = findViewById(R.id.targetGreen);

        //adding onClickListeners
        targetBlue.setOnClickListener(targetBlueListener);
        targetOrange.setOnClickListener(targetOrangeListener);
        targetPurple.setOnClickListener(targetPurpleListener);
        targetGreen.setOnClickListener(targetGreenListener);

        //switch selection to current setting
        loadSettings();
        switchSelectionTo(currentTarget);

    }

    private View.OnClickListener targetBlueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!currentTarget.equals("blue")) {
                switchSelectionTo("blue");
            }
        }
    };

    private View.OnClickListener targetOrangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!currentTarget.equals("orange")) {
                switchSelectionTo("orange");
            }
        }
    };

    private View.OnClickListener targetPurpleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!currentTarget.equals("purple")) {
                switchSelectionTo("purple");
            }
        }
    };

    private View.OnClickListener targetGreenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!currentTarget.equals("green")) {
                switchSelectionTo("green");
            }
        }
    };

    private void switchSelectionTo(String newTargetImage) {
        switch (newTargetImage) {
            case "blue":
                targetBlue.setImageResource(R.drawable.tap_target_blue_selected);
                targetOrange.setImageResource(R.drawable.tap_target_orange);
                targetPurple.setImageResource(R.drawable.tap_target_purple);
                targetGreen.setImageResource(R.drawable.itap_target_android_prototype);
                currentTarget = "blue";
                break;
            case "orange":
                targetOrange.setImageResource(R.drawable.tap_target_orange_selected);
                targetPurple.setImageResource(R.drawable.tap_target_purple);
                targetGreen.setImageResource(R.drawable.itap_target_android_prototype);
                targetBlue.setImageResource(R.drawable.tap_target_blue);
                currentTarget = "orange";
                break;
            case "purple":
                targetPurple.setImageResource(R.drawable.tap_target_purple_selected);
                targetGreen.setImageResource(R.drawable.itap_target_android_prototype);
                targetBlue.setImageResource(R.drawable.tap_target_blue);
                targetOrange.setImageResource(R.drawable.tap_target_orange);
                currentTarget = "purple";
                break;
            case "green":
                targetGreen.setImageResource(R.drawable.tap_target_green_selected);
                targetBlue.setImageResource(R.drawable.tap_target_blue);
                targetOrange.setImageResource(R.drawable.tap_target_orange);
                targetPurple.setImageResource(R.drawable.tap_target_purple);
                currentTarget = "green";
                break;
        }
        saveSettings();
    }

    private void saveSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("targetImage", currentTarget);
        editor.apply();
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        currentTarget = sharedPreferences.getString("targetImage", "green");
    }
}
