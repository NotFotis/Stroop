package com.example.stroopgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class StroopActivity extends AppCompatActivity {

    // Declare and initialize variables
    private TextView wordTextView;
    private TextView resultTextView;
    private TextView timeTextView;
    private Button redButton;
    private Button blueButton;
    private Button greenButton;
    private Button yellowButton;
    private CountDownTimer timer;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int timeoutCount = 0;
    private int trialNumber = 0;
    private List<Trial> trialList = new ArrayList<>();

    // Declare words and colors arrays
    private String[] words = {"RED", "BLUE", "GREEN", "YELLOW"};
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroop);

        // Get references to UI elements
        wordTextView = findViewById(R.id.word_text_view);
        resultTextView = findViewById(R.id.resultTextView);
        timeTextView = findViewById(R.id.timeTextView);
        redButton = findViewById(R.id.red_button);
        blueButton = findViewById(R.id.blue_button);
        greenButton = findViewById(R.id.green_button);
        yellowButton = findViewById(R.id.yellow_button);

        // Set a 5-minute timer to end the test
        timer = new CountDownTimer(1 * 10 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Display the remaining time in minutes and seconds
                int minutes = (int) millisUntilFinished / (60 * 1000);
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timeTextView.setText("Time left: " + timeLeft);
            }

            public void onFinish() {
                // Test has ended, do something here
                computeTestResults();

            }
        }.start();

        // Set the first word and color
        setRandomWordAndColor();

        // Set listeners for the buttons
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("RED", Color.RED);
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("BLUE", Color.BLUE);
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("GREEN", Color.GREEN);
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("YELLOW", Color.YELLOW);
            }
        });
    }

    private void setRandomWordAndColor() {
        // Get a random index for the words and colors arrays
        int index = new Random().nextInt(words.length);
        int Colorindex = new Random().nextInt(colors.length);
        // Set the word text and color
        wordTextView.setText(words[index]);
        wordTextView.setTextColor(colors[Colorindex]);

        // Set the Stroop color match
        boolean isMatch = (words[index].equals(getColorName(colors[index])));
        trialNumber++;
        Trial trial = new Trial(trialNumber, words[index], getColorName(colors[index]), isMatch, 0, 0, 0);
        trialList.add(trial);
    }

    private void onButtonClick(String colorName, int color) {
        long responseTime = System.currentTimeMillis() - trialList.get(trialNumber - 1).getStartTime();
        int status=0;

        if (wordTextView.getCurrentTextColor() == color) {
            // User chose the correct color
            correctCount++;
            resultTextView.setText("Correct: " + correctCount + " Incorrect: " + incorrectCount + " Timeout: " + timeoutCount);
            status = 1;
        } else if (wordTextView.getCurrentTextColor() != color){
            // User chose the wrong color
            incorrectCount++;
            resultTextView.setText("Correct: " + correctCount + " Incorrect: " + incorrectCount + " Timeout: " + timeoutCount);
            status = 2;
        }

        // Check if the user has timed out (2 seconds have passed without answer)
        else if (responseTime >= 2000) {
            timeoutCount++;
            resultTextView.setText("Correct: " + correctCount + " Incorrect: " + incorrectCount + " Timeout: " + timeoutCount);
            status = 3;
            // Generate a new word and color for the next test
            setRandomWordAndColor();
        }
        // Update the trial with the response time and status
        trialList.get(trialNumber - 1).setResponseTime(responseTime);
        trialList.get(trialNumber - 1).setStatus(status);

        // Generate a new word and color for the next test
        setRandomWordAndColor();
    }


    private String getColorName(int color) {
        switch (color) {
            case Color.RED:
                return "RED";
            case Color.BLUE:
                return "BLUE";
            case Color.GREEN:
                return "GREEN";
            case Color.YELLOW:
                return "YELLOW";
            default:
                return "UNKNOWN";
        }
    }
    private void computeTestResults() {
        int totalCorrect = 0;
        int totalIncorrect = 0;
        int congruentCorrect = 0;
        int congruentIncorrect = 0;
        int nonCongruentCorrect = 0;
        int nonCongruentIncorrect = 0;
        int redCount = 0;
        int blueCount = 0;
        int greenCount = 0;
        int yellowCount = 0;
        int redCorrect = 0;
        int blueCorrect = 0;
        int greenCorrect = 0;
        int yellowCorrect = 0;
        int redIncorrect = 0;
        int blueIncorrect = 0;
        int greenIncorrect = 0;
        int yellowIncorrect = 0;
        long totalResponseTime = 0;
        long congruentResponseTime = 0;
        long nonCongruentResponseTime = 0;
        int congruentCount = 0;
        int nonCongruentCount = 0;

        for (Trial trial : trialList) {
            // Count correct and incorrect responses
            if (trial.getStatus() == 1) {
                totalCorrect++;
                if (trial.isColorMatch()) {
                    congruentCorrect++;
                } else {
                    nonCongruentCorrect++;
                }
            } else {
                totalIncorrect++;
                if (trial.isColorMatch()) {
                    congruentIncorrect++;
                } else {
                    nonCongruentIncorrect++;
                }
            }

            // Count responses by color
            switch (trial.getWord()) {
                case "RED":
                    redCount++;
                    if (trial.getStatus() == 1) {
                        redCorrect++;
                    } else {
                        redIncorrect++;
                    }
                    break;
                case "BLUE":
                    blueCount++;
                    if (trial.getStatus() == 1) {
                        blueCorrect++;
                    } else {
                        blueIncorrect++;
                    }
                    break;
                case "GREEN":
                    greenCount++;
                    if (trial.getStatus() == 1) {
                        greenCorrect++;
                    } else {
                        greenIncorrect++;
                    }
                    break;
                case "YELLOW":
                    yellowCount++;
                    if (trial.getStatus() == 1) {
                        yellowCorrect++;
                    } else {
                        yellowIncorrect++;
                    }
                    break;
            }

            // Calculate response times
            long responseTime = trial.getResponseTime();
            totalResponseTime += responseTime;
            if (trial.isColorMatch()) {
                congruentResponseTime += responseTime;
                congruentCount++;
            } else {
                nonCongruentResponseTime += responseTime;
                nonCongruentCount++;
            }
        }

        // Calculate percentages and averages
        double totalPercent = 100.0 * trialList.size() / (redCount+blueCount+greenCount+yellowCount);
        String message = String.format("Total percent: %.2f%% \n", totalPercent);

        String message1 = String.format("Percentage of stimuli for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
                100.0 * redCount / trialList.size(), 100.0 * blueCount / trialList.size(),
                100.0 * greenCount / trialList.size(), 100.0 * yellowCount / trialList.size());

        String message2 = String.format("Percentage of correct responses for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
                100.0 * redCorrect / redCount, 100.0 * blueCorrect / blueCount,
                100.0 * greenCorrect / greenCount, 100.0 * yellowCorrect / yellowCount);

        String message3 = String.format("Percentage of incorrect responses for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
                100.0 * redIncorrect / redCount, 100.0 * blueIncorrect / blueCount,
                100.0 * greenIncorrect / greenCount, 100.0 * yellowIncorrect / yellowCount);

        // Calculate average response times
        double totalAvgResponseTime = (double) totalResponseTime / trialList.size();
        double congruentAvgResponseTime = (double) congruentResponseTime / congruentCount;
        double nonCongruentAvgResponseTime = (double) nonCongruentResponseTime / nonCongruentCount;
        String message4 = String.format("Average response time to all stimuli: %.2f ms \n", totalAvgResponseTime);
        String message5 = String.format("Average response time to congruent stimuli: %.2f ms \n", congruentAvgResponseTime);
        String message6 = String.format("Average response time to non-congruent stimuli: %.2f ms \n", nonCongruentAvgResponseTime);

        // Calculate Stroop effect
        double stroopEffect = nonCongruentAvgResponseTime - congruentAvgResponseTime;
        String message7 = String.format("Stroop effect: %.2f ms\n", stroopEffect);


        // First, create a dialog builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the title and message of the dialog
        builder.setTitle("Results");
        builder.setMessage(message+message1+message2+message3+message4+message5+message6+message7);

// Add a button to the dialog and set its listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog when the button is clicked
                dialog.dismiss();
            }
        });
        Button restartButton = new Button(StroopActivity.this);
        restartButton.setText("Restart");


        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the application
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setView(restartButton);
        builder.setCancelable(false);
        builder.show();

        // create a new Result object to store the trial results
        String username = getIntent().getStringExtra("username");
       Result result = new Result(username,  totalPercent, 100.0 * redCount / trialList.size(), 100.0 * blueCount / trialList.size(),
                100.0 * greenCount / trialList.size(), 100.0 * yellowCount / trialList.size(), 100.0 * redCorrect / redCount, 100.0 * blueCorrect / blueCount,
                100.0 * greenCorrect / greenCount, 100.0 * yellowCorrect / yellowCount, 100.0 * redIncorrect / redCount, 100.0 * blueIncorrect / blueCount,
                100.0 * greenIncorrect / greenCount, 100.0 * yellowIncorrect / yellowCount, totalAvgResponseTime, congruentAvgResponseTime, nonCongruentAvgResponseTime, stroopEffect);

// insert the results into the database
      
        ResultsContentValues.createContentValues(result);

    }
}




