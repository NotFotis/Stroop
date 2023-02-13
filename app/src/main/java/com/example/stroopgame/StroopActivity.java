package com.example.stroopgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stroopgame.R;
import com.example.stroopgame.Stroop.StroopData;
import com.example.stroopgame.Stroop.StroopTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class StroopActivity extends AppCompatActivity {
    private TextView wordDisplay;
    private Button redButton;
    private Button blueButton;
    private Button greenButton;
    private Button yellowButton;
    private ArrayList<StroopData> stimuli;
    private int currentStimulusIndex = 0;
    private ArrayList<StroopData> responses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroop);

        wordDisplay = findViewById(R.id.word_text_view);
        redButton = findViewById(R.id.red_button);
        blueButton = findViewById(R.id.blue_button);
        greenButton = findViewById(R.id.green_button);
        yellowButton = findViewById(R.id.yellow_button);

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResponseSelected("Red");
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResponseSelected("Blue");
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResponseSelected("Green");
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResponseSelected("Yellow");
            }
        });

        stimuli = new StroopTest().getStimuli();
        // shuffle the stimuli to present them in a random order
        Collections.shuffle(stimuli);
        // present the first stimulus
        presentStimulus(stimuli.get(currentStimulusIndex));
    }


    private void presentStimulus(StroopData stimulus) {
        wordDisplay.setText(stimulus.getWord());
        switch (stimulus.getDisplayColor()) {
            case "Red":
                wordDisplay.setTextColor(ContextCompat.getColor(this,R.color.red));
                break;
            case "Blue":
                wordDisplay.setTextColor(ContextCompat.getColor(this,R.color.blue));
                break;
            case "Green":
                wordDisplay.setTextColor(ContextCompat.getColor(this,R.color.green));
                break;
            case "Yellow":
                wordDisplay.setTextColor(ContextCompat.getColor(this,R.color.yellow));
                break;
        }
    }

    private void onResponseSelected(String color) {
        long responseTime = SystemClock.elapsedRealtime();
        StroopData currentStimulus = stimuli.get(currentStimulusIndex);
        currentStimulus.setResponseTime(responseTime);
        currentStimulus.setResponse(color);
        if (color.equals(currentStimulus.getDisplayColor())) {
            currentStimulus.setCorrect(true);
        } else {
            currentStimulus.setCorrect(false);
        }
        responses.add(currentStimulus);
        currentStimulusIndex++;
        if (currentStimulusIndex < stimuli.size()) {
// present the next stimulus
            presentStimulus(stimuli.get(currentStimulusIndex));
        } else {
// all stimuli have been presented, analyze the data
            //analyzeData();
            StroopData.averageResponseTimeToCongruentStimuli(responses);
        }
    }
    private void analyzeData() {
        int correctResponses = 0;
        int correctResponsesCongruent = 0;
        for (StroopData response : responses) {
            if (response.isMatch()) {
                correctResponses++;
                if (response.isCongruent()) {
                    correctResponsesCongruent++;
                }
            }
        }
        // calculate the accuracy
        float accuracy = (float) correctResponses / responses.size();
        // calculate the congruent accuracy
        float congruentAccuracy = (float) correctResponsesCongruent /
                StroopTest.getNumberOfCongruentStimuli();
        // show the results
        // ...
        showResults(accuracy,congruentAccuracy);
    }
    private void showResults(float accuracy, float congruentAccuracy) {
// create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(StroopActivity.this);
// set the title
        builder.setTitle("Results");
// set the message
        builder.setMessage("Accuracy: " + accuracy * 100 + "%\nCongruent Accuracy: " +
                congruentAccuracy * 100 + "%");
// set the positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
// show the dialog
        builder.show();
    }
}