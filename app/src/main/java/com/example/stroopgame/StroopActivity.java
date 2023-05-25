package com.example.stroopgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
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
    private long timeout=0;
    private  int sequenceNumber=0;
    private List<Trial> trialList = new ArrayList<>();
    private ResultsContentValues dbHandler;
    Handler handler = new Handler();
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

        timeTextView = findViewById(R.id.timeTextView);
        redButton = findViewById(R.id.red_button);
        blueButton = findViewById(R.id.blue_button);
        greenButton = findViewById(R.id.green_button);
        yellowButton = findViewById(R.id.yellow_button);

        // Set a 5-minute timer to end the test
        int duration = getIntent().getIntExtra("duration", 60000); // Default duration of 10 seconds
        timer = new CountDownTimer(duration, 1000) {

                public void onTick ( long millisUntilFinished){
                    // Display the remaining time in minutes and seconds
                    int minutes = (int) millisUntilFinished / (60 * 1000);
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                    timeTextView.setText("ΧΡΟΝΟΣ: " + timeLeft);
                }

                public void onFinish () {
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
        int colorIndex = new Random().nextInt(colors.length);

        // Increment the sequence number
        sequenceNumber++;

        // Set the word text and color
        String wordText = words[index];
        wordTextView.setText(wordText);
        wordTextView.setTextColor(colors[colorIndex]);


        // Set the Stroop color match
        boolean isMatch = (words[index].equals(getColorName(colors[colorIndex])));
        trialNumber++;
        Trial trial = new Trial(trialNumber, words[index], getColorName(colors[colorIndex]), isMatch, 0, 0, 0);
        trial.setStartTime(System.currentTimeMillis());
        trialList.add(trial);
        int duration2 = getIntent().getIntExtra("duration2", 1000); // Default duration of 10 seconds
        // Start a new trial after the timeout
        timeout=duration2;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(trial.getStatus()==0) {
                    checkResponseTime();

                }
            }
        }, timeout);
    }

    private void onButtonClick(String colorName, int color) {
        long responseTime = System.currentTimeMillis() - trialList.get(trialNumber - 1).getStartTime();
        long responseTimeInSeconds = responseTime / 1000;

        int status = 0;
        Log.d("DEBUG", "Response time: " + responseTimeInSeconds);
        Log.d("DEBUG", "Current text color: " + wordTextView.getCurrentTextColor() + ", color: " + color);

        if (wordTextView.getCurrentTextColor() == color) {
            checkResponseTime();
            // User chose the correct color
            correctCount++;

            status = 1;
        } else if (wordTextView.getCurrentTextColor() != color) {
            checkResponseTime();
            // User chose the wrong color
            incorrectCount++;

            status = 2;
        }



        Trial trial = trialList.get(trialNumber - 1);
        trial.setResponseTime(System.currentTimeMillis() - trial.getStartTime());
        trial.setStatus(status);


        setRandomWordAndColor();

    }
    private void checkResponseTime() {
        int duration2 = getIntent().getIntExtra("duration2", 1000); // Default duration of 10 seconds
        long responseTime = System.currentTimeMillis() - trialList.get(trialNumber - 1).getStartTime();
        timeout=duration2;

        if (responseTime > timeout) {
            timeoutCount++;

            Trial trial = trialList.get(trialNumber - 1);
            trial.setResponseTime(responseTime);
            trial.setStatus(3);
            setRandomWordAndColor();
        }
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
        int totalTrials = trialList.size();
        int totalCorrect = 0;
        int totalIncorrect = 0;
        int congruentCorrect = 0;
        int congruentIncorrect = 0;
        int nonCongruentCorrect = 0;
        int nonCongruentIncorrect = 0;
        int totalTimeout=0;
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
        Date date = new Date(System.currentTimeMillis());

        for (Trial trial : trialList) {

            // Count correct and incorrect responses
            if (trial.getStatus() == 1) {
                totalCorrect++;
                if (trial.isColorMatch()) {
                    congruentCorrect++;
                } else {
                    nonCongruentCorrect++;
                }
            } else if (trial.getStatus()==2) {
                totalIncorrect++;
                if (trial.isColorMatch()) {
                    congruentIncorrect++;
                } else {
                    nonCongruentIncorrect++;
                }
            } else if (trial.getStatus()==3) {
                totalTimeout++;

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
  String message = String.format("Πλήθος σωστών αποκρίσεων: %d από %d αποκρίσεις \n\n", totalCorrect,trialList.size());
  String message0 = String.format("Πλήθος λανθασμένων αποκρίσεων: %d από %d αποκρίσεις \" \n\n", totalIncorrect,trialList.size());
        String message01 = String.format("πλήθος σωστών αποκρίσεων στο σύνολο των congruent ερεθισμάτων: %d \n\n", congruentCorrect);
        String message02 = String.format("πλήθος λανθασμένων αποκρίσεων στο σύνολο των congruent ερεθισμάτων: %d \n\n", congruentIncorrect);
        String message03 = String.format("πλήθος σωστών αποκρίσεων στο σύνολο των non-congruent ερεθισμάτων: %d \n\n", nonCongruentCorrect);
        String message04 = String.format("πλήθος λανθασμένων αποκρίσεων στο σύνολο των non-congruent ερεθισμάτων: %d \n\n", nonCongruentIncorrect);
        redPercentage = 100.0 * redCount / trialList.size();
        bluePercentage = 100.0 * blueCount / trialList.size();
        greenPercentage = 100.0 * greenCount / trialList.size();
        yellowPercentage = 100.0 * yellowCount / trialList.size();
        String message1 = String.format("ποσοστό ερεθισμάτων για κάθε χρώμα: Κόκκινο: %.2f%%, Μπλε: %.2f%%, Πράσινο: %.2f%%, Κίτρινο: %.2f%% \n\n",
                redPercentage, bluePercentage,greenPercentage, yellowPercentage);

        redCorrectPer =  100.0 * redCorrect / redCount;
        blueCorrectPer = 100.0 * blueCorrect / blueCount;
        greenCorrectPer = 100.0 * greenCorrect / greenCount;
        yellowCorrectPer = 100.0 * yellowCorrect / yellowCount;
        String message2 = String.format("ποσοστό σωστών αποκρίσεων για κάθε χρώμα: Κόκκινο: %.2f%%, Μπλε: %.2f%%, Πράσινο: %.2f%%, Κίτρινο: %.2f%% \n\n",
                redCorrectPer,blueCorrectPer,greenCorrectPer,yellowCorrectPer);

        redIncorrectPer =  100.0 * redIncorrect / redCount;
        blueIncorrectPer = 100.0 * blueIncorrect / blueCount;
        greenIncorrectPer = 100.0 * greenIncorrect / greenCount;
        yellowIncorrectPer = 100.0 * yellowIncorrect / yellowCount;
        String message3 = String.format("ποσοστό λανθασμένων αποκρίσεων για κάθε χρώμα: Κόκκινο: %.2f%%, Μπλε: %.2f%%, Πράσινο: %.2f%%, Κίτρινο: %.2f%% \n\n",
              redIncorrectPer,blueIncorrectPer,greenIncorrectPer,yellowIncorrectPer);

        // Calculate average response times
        double totalAvgResponseTime = (double) totalResponseTime / trialList.size();
        double congruentAvgResponseTime = (double) congruentResponseTime / (double) congruentCount;
        double nonCongruentAvgResponseTime = (double) nonCongruentResponseTime/ (double) nonCongruentCount ;
        String message4 = String.format("average response time στο σύνολο των ερεθισμάτων: %.2f ms \n\n", totalAvgResponseTime);
        String message5 = String.format("average response time στο σύνολο των congruent ερεθισμάτων: %.2f ms \n\n", congruentAvgResponseTime);
        String message6 = String.format("average response time στο σύνολο των non-congruent ερεθισμάτων: %.2f ms \n\n", nonCongruentAvgResponseTime);

        // Calculate Stroop effect
        double stroopEffect = nonCongruentAvgResponseTime - congruentAvgResponseTime;
        String message7 = String.format("Stroop effect: %.2f ms\n\n", stroopEffect);


        // First, create a dialog builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the title and message of the dialog
        builder.setTitle("ΑΠΟΤΕΛΕΣΜΑΤΑ");

        builder.setMessage(message+message0+message01+message02+message03+message04+message1+message2+message3+message4+message5+message6+message7);


// Add a button to the dialog and set its listener

        builder.setPositiveButton("Αρχική Σελίδα", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Restart the application
                Intent intent = new Intent(StroopActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();

        // create a new Result object to store the trial results
        String username = getIntent().getStringExtra("username");


// insert the results into the database

        dbHandler.createContentValues(username,date,totalCorrect,totalIncorrect,congruentCorrect,congruentIncorrect,nonCongruentCorrect,nonCongruentIncorrect,redPercentage,bluePercentage,greenPercentage,yellowPercentage,redCorrectPer,blueCorrectPer,greenCorrectPer,yellowCorrectPer,
                redIncorrectPer,blueIncorrectPer,greenIncorrectPer,yellowIncorrectPer,totalAvgResponseTime,congruentAvgResponseTime,nonCongruentAvgResponseTime,stroopEffect);

    }
}




