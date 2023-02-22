package com.example.stroopgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultsActivity extends AppCompatActivity {
    private Button totalCorrect;
    private Button totalIncorrect;
    private Button congruentCorrect;
    private Button congruentIncorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
//        totalCorrect = findViewById(R.id.totalCorrect);
//        totalIncorrect = findViewById(R.id.totalIncorrect);
//        congruentCorrect = findViewById(R.id.congruentCorrect);
//        congruentIncorrect = findViewById(R.id.congruentIncorrect);
//        nonCongruentCorrect = findViewById(R.id.blue_button);
//        nonCongruentIncorrect = findViewById(R.id.green_button);
//        totalResponseTime = findViewById(R.id.yellow_button);
//        congruentResponseTime = findViewById(R.id.yellow_button);
//        nonCongruentResponseTime= findViewById(R.id.yellow_button);
        Button showAnswersButton = findViewById(R.id.showAnswersButton);
        showAnswersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int redCorrect = getIntent().getIntExtra("redCorrect", 0);
                int blueCorrect = getIntent().getIntExtra("blueCorrect", 0);
                int greenCorrect = getIntent().getIntExtra("greenCorrect", 0);
                int yellowCorrect = getIntent().getIntExtra("yellowCorrect", 0);
                int redIncorrect = getIntent().getIntExtra("redIncorrect", 0);
                int blueIncorrect = getIntent().getIntExtra("blueIncorrect", 0);
                int greenIncorrect = getIntent().getIntExtra("greenIncorrect", 0);
                int yellowIncorrect = getIntent().getIntExtra("yellowIncorrect", 0);
                // Determine which button was pressed based on its id
                switch (v.getId()) {
                    case R.id.showRedAnswersButton:
                        // Set the corresponding TextViews with the red answers
                        totalCorrect.setText("Total Correct: " + redCorrect);
                        totalIncorrect.setText("Total Incorrect: " + redIncorrect);
//                        congruentCorrect.setText("Congruent Correct: " + redCongruentCorrect);
//                        congruentIncorrect.setText("Congruent Incorrect: " + redCongruentIncorrect);
                        break;
                    case R.id.showBlueAnswersButton:
                        // Set the corresponding TextViews with the blue answers
                        totalCorrect.setText("Total Correct: " + blueCorrect);
                        totalIncorrect.setText("Total Incorrect: " + blueIncorrect);
//                        congruentCorrect.setText("Congruent Correct: " + blueCongruentCorrect);
//                        congruentIncorrect.setText("Congruent Incorrect: " + blueCongruentIncorrect);
                        break;
                    case R.id.showGreenAnswersButton:
                        // Set the corresponding TextViews with the green answers
                        totalCorrect.setText("Total Correct: " + greenCorrect);
                        totalIncorrect.setText("Total Incorrect: " + greenIncorrect);
//                        congruentCorrect.setText("Congruent Correct: " + greenCongruentCorrect);
//                        congruentIncorrect.setText("Congruent Incorrect: " + greenCongruentIncorrect);
                        break;
                    case R.id.showYellowAnswersButton:
                        // Set the corresponding TextViews with the yellow answers
                        totalCorrect.setText("Total Correct: " + yellowCorrect);
                        totalIncorrect.setText("Total Incorrect: " + yellowIncorrect);
//                        congruentCorrect.setText("Congruent Correct: " + yellowCongruentCorrect);
//                        congruentIncorrect.setText("Congruent Incorrect: " + yellowCongruentIncorrect);
                        break;
                    default:
                        break;
                }
            }
        });
    }


}