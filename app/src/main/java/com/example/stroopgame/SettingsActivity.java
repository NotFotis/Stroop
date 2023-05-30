package com.example.stroopgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar durationSeekBar;
    private SeekBar durationSeekBar2;
    private TextView timerTextView;
    private TextView timerTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        durationSeekBar = findViewById(R.id.durationSeekBar);
        durationSeekBar2 = findViewById(R.id.durationSeekBar2);
        timerTextView = findViewById(R.id.timerTextView);
        timerTextView2 = findViewById(R.id.timerTextView2);

        // Set the default duration values
        int defaultDuration1 = 5; // Default duration in seconds for durationSeekBar
        int defaultDuration2 = 5; // Default duration in minutes for durationSeekBar2

        // Set the progress and text for the SeekBars and TextViews
        durationSeekBar.setProgress(defaultDuration1);
        timerTextView.setText(formatDuration(defaultDuration1 * 60000)); // Assuming 1 unit on SeekBar represents 30,000 milliseconds
        durationSeekBar2.setProgress(defaultDuration2);
        timerTextView2.setText(formatDuration(defaultDuration2 * 1000)); // Assuming 1 unit on SeekBar2 represents 60,000 milliseconds

        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int duration = progress * 60000;
                String timerValue = formatDuration(duration);
                timerTextView.setText(timerValue);
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        });

        durationSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int duration = progress * 1000;
                String timerValue = formatDuration(duration);
                timerTextView2.setText(timerValue);
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No action needed
            }
        });

        // Set the result and finish the activity when the user is done with the settings
        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration1 = durationSeekBar.getProgress() * 60000;
                int duration2 = durationSeekBar2.getProgress() * 1000;

                // Store the selected duration values in SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("duration1", duration1);
                editor.putInt("duration2", duration2);
                editor.apply();

                finish();
            }
        });

    }

    private String formatDuration(int duration) {
        // Format the duration value as a string in the format "mm:ss"
        int minutes = duration / 60000;
        int seconds = (duration % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
