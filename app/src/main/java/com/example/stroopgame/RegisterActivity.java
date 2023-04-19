package com.example.stroopgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText ageEditText;
    Button registerButton;
    DatabaseHelper databaseHelper;


    private SQLiteDatabase database;
    private String formatDuration(int duration) {
        // Format the duration value as a string in the format "mm:ss"
        int minutes = duration / 60000;
        int seconds = (duration % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        ageEditText = findViewById(R.id.age);

        registerButton = findViewById(R.id.register);
        databaseHelper = new DatabaseHelper(this);

        database = openOrCreateDatabase("strooptest.db", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Stroop (username VARCHAR, date DATE, total_correct INTEGER,total_incorrect INTEGER,congruent_correct INTEGER,congruent_incorrect INTEGER,noncongruent_correct INTEGER,noncongruent_incorrect INTEGER, red_percent DOUBLE, blue_percent DOUBLE, green_percent DOUBLE, yellow_percent DOUBLE, red_correct_percent DOUBLE, blue_correct_percent DOUBLE, green_correct_percent DOUBLE, yellow_correct_percent DOUBLE, red_incorrect_percent DOUBLE, blue_incorrect_percent DOUBLE, green_incorrect_percent DOUBLE, yellow_incorrect_percent DOUBLE, total_response_time DOUBLE, congruent_response_time DOUBLE, non_congruent_response_time DOUBLE, stroop_effect DOUBLE, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES users(ID))");

        SeekBar durationSeekBar = findViewById(R.id.durationSeekBar);
        SeekBar durationSeekBar2 = findViewById(R.id.durationSeekBar2);
        TextView timerTextView = findViewById(R.id.timerTextView);
        TextView timerTextView2 = findViewById(R.id.timerTextView2);
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // No action needed
                int duration = progress * 30000;
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
                // Get the selected duration value and start the timer activity


            }
        });
        durationSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // No action needed
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
                // Get the selected duration value and start the timer activity


            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();


                if (username.isEmpty() ) {
                    Toast.makeText(RegisterActivity.this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                } else {

                    String ageString = ageEditText.getText().toString();
                    addUserToDatabase(username,ageString);
                    int age = Integer.parseInt(ageString);
                    int duration = durationSeekBar.getProgress() * 30000;
                    int duration2 = durationSeekBar2.getProgress() * 1000;
                    Intent intent = new Intent(RegisterActivity.this, StroopActivity.class);
                    intent.putExtra("age",age);
                    intent.putExtra("duration", duration);
                    intent.putExtra("duration2", duration2);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });

    }

    private void addUserToDatabase(String username, String ageString) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_AGE, ageString);

        long newRowId = database.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error while registering user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Επιτυχημένη είσοδος", Toast.LENGTH_SHORT).show();
        }
    }
}
