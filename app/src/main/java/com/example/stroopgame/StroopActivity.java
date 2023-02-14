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
import java.util.Map;

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
            analyzeData();
            StroopData.averageResponseTimeToCongruentStimuli(responses);
        }
    }
    public void analyzeData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StroopActivity.this);
        int totalCorrectResponses = StroopData.getNumberOfCorrectResponses(responses);
        int totalIncorrectResponses = StroopData.getNumberOfIncorrectResponses(responses);
        int totalCorrectResponsesCongruent = StroopData.getNumberOfCorrectResponsesCongruent(responses);
        int totalIncorrectResponsesCongruent = StroopData.getNumberOfIncorrectResponsesCongruent(responses);
        int totalCorrectResponsesNonCongruent = StroopData.getNumberOfCorrectResponsesNonCongruent(responses);
        int totalIncorrectResponsesNonCongruent = StroopData.getNumberOfIncorrectResponsesNonCongruent(responses);
        Map<String,Float> stimuliPercentagesPerColor = StroopData.calculatePercentageOfStimuliPerColor(responses);
        Map<String,Float> correctResponsesPercentagesPerColor = StroopData.calculatePercentageOfCorrectResponsesPerColor(responses);
        builder.setMessage("Total correct Responses:" + totalCorrectResponses
                +"Total incorrect Responses:"  + totalIncorrectResponses
                + "Total congruent correct Responses:" + totalCorrectResponsesCongruent
        + "Total congruent incorrect Responses:" + totalIncorrectResponsesCongruent
        + "Total Noncongruent correct Responses:" + totalCorrectResponsesNonCongruent
        + "Total Noncongruent incorrect Responses:" + totalIncorrectResponsesNonCongruent
        + "Stimuli percentage per color:" + stimuliPercentagesPerColor
        + "Correct responses percentage per color:" + correctResponsesPercentagesPerColor);

        // set the positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
// show the dialog
        builder.show();
        // Add any other processing of the data here
    }


}