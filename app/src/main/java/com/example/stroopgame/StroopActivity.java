package com.example.stroopgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private ResultsContentValues dbHandler;

    // Declare words and colors arrays
    private String[] words = {"ΚΟΚΚΙΝΟ", "ΜΠΛΕ", "ΠΡΑΣΙΝΟ", "ΚΙΤΡΙΝΟ"};
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroop);
        dbHandler = new ResultsContentValues(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
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
                timeTextView.setText("ΧΡΟΝΟΣ: " + timeLeft);
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
                onButtonClick("ΚΟΚΚΙΝΟ", Color.RED);
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("ΜΠΛΕ", Color.BLUE);
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("ΠΡΑΣΙΝΟ", Color.GREEN);
            }
        });

        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick("ΚΙΤΡΙΝΟ", Color.YELLOW);
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
        int status = 0;

        if (wordTextView.getCurrentTextColor() == color) {
            // User chose the correct color
            correctCount++;
            resultTextView.setText("ΣΩΣΤΑ: " + correctCount + " ΛΑΘΟΣ: " + incorrectCount + " Timeout: " + timeoutCount);
            status = 1;
        } else if (wordTextView.getCurrentTextColor() != color) {
            // User chose the wrong color
            incorrectCount++;
            resultTextView.setText("ΣΩΣΤΑ: " + correctCount + " ΛΑΘΟΣ: " + incorrectCount + " Timeout: " + timeoutCount);
            status = 2;
        }
        else if(responseTime>2000){
            timeoutCount++;
            resultTextView.setText("ΣΩΣΤΑ: " + correctCount + " ΛΑΘΟΣ: " + incorrectCount + " Timeout: " + timeoutCount);
            status= 3;
        }



        Trial trial = trialList.get(trialNumber - 1);
        trial.setResponseTime(responseTime);
        trial.setStatus(status);


            setRandomWordAndColor();

    }

    private String getColorName(int color) {
        switch (color) {
            case Color.RED:
                return "ΚΟΚΚΙΝΟ";
            case Color.BLUE:
                return "ΜΠΛΕ";
            case Color.GREEN:
                return "ΠΡΑΣΙΝΟ";
            case Color.YELLOW:
                return "ΚΙΤΡΙΝΟ";
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
        double redPercentage=0;
        double bluePercentage=0;
        double greenPercentage=0;
        double yellowPercentage=0;
        double redCorrectPer=0;
        double blueCorrectPer=0;
        double greenCorrectPer=0;
        double yellowCorrectPer=0;
        double redIncorrectPer=0;
        double blueIncorrectPer=0;
        double greenIncorrectPer=0;
        double yellowIncorrectPer=0;
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
                case "ΚΟΚΚΙΝΟ":
                    redCount++;
                    if (trial.getStatus() == 1) {
                        redCorrect++;
                    } else {
                        redIncorrect++;
                    }
                    break;
                case "ΜΠΛΕ":
                    blueCount++;
                    if (trial.getStatus() == 1) {
                        blueCorrect++;
                    } else {
                        blueIncorrect++;
                    }
                    break;
                case "ΠΡΑΣΙΝΟ":
                    greenCount++;
                    if (trial.getStatus() == 1) {
                        greenCorrect++;
                    } else {
                        greenIncorrect++;
                    }
                    break;
                case "ΚΙΤΡΙΝΟ":
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
//        totalCorrect= 100 * (redCorrect+blueCorrect+greenCorrect+yellowCorrect)/trialList.size();
//        totalIncorrect =100 * (redIncorrect+blueIncorrect+greenIncorrect+yellowIncorrect)/trialList.size();
  String message = String.format("Total percent: %.2f%% \n", totalPercent);
//        String message0 = String.format("Total correct: %.2f%% \n", totalCorrect);
 //       String message01 = String.format("Total incorrect: %.2f%% \n", totalIncorrect);
        redPercentage = 100.0 * redCount / trialList.size();
        bluePercentage = 100.0 * blueCount / trialList.size();
        greenPercentage = 100.0 * greenCount / trialList.size();
        yellowPercentage = 100.0 * yellowCount / trialList.size();
        String message1 = String.format("Percentage of stimuli for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
                redPercentage, bluePercentage,greenPercentage, yellowPercentage);

        redCorrectPer =  100.0 * redCorrect / redCount;
        blueCorrectPer = 100.0 * blueCorrect / blueCount;
        greenCorrectPer = 100.0 * greenCorrect / greenCount;
        yellowCorrectPer = 100.0 * yellowCorrect / yellowCount;
        String message2 = String.format("Percentage of correct responses for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
                redCorrectPer,blueCorrectPer,greenCorrectPer,yellowCorrectPer);

        redIncorrectPer =  100.0 * redIncorrect / redCount;
        blueIncorrectPer = 100.0 * blueIncorrect / blueCount;
        greenIncorrectPer = 100.0 * greenIncorrect / greenCount;
        yellowIncorrectPer = 100.0 * yellowIncorrect / yellowCount;
        String message3 = String.format("Percentage of incorrect responses for each color: Red: %.2f%%, Blue: %.2f%%, Green: %.2f%%, Yellow: %.2f%% \n",
              redIncorrectPer,blueIncorrectPer,greenIncorrectPer,yellowIncorrectPer);

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
        builder.setTitle("ΑΠΟΤΕΛΕΣΜΑΤΑ");
        builder.setMessage(message+message1+message2+message3+message4+message5+message6+message7);


// Add a button to the dialog and set its listener

        Button restartButton = new Button(StroopActivity.this);
        restartButton.setText("ΕΠΑΝΕΚΚΙΝΗΣΗ");


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


// insert the results into the database

        dbHandler.createContentValues(username,totalPercent,redPercentage,bluePercentage,greenPercentage,yellowPercentage,redCorrectPer,blueCorrectPer,greenCorrectPer,yellowCorrectPer,
                redIncorrectPer,blueIncorrectPer,greenIncorrectPer,yellowIncorrectPer,
        totalAvgResponseTime,congruentAvgResponseTime,nonCongruentAvgResponseTime,stroopEffect);

    }
}




