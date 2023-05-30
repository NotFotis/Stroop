package com.example.stroopgame;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText ageEditText;
    Button registerButton;
    DatabaseHelper databaseHelper;

    private ActivityResultLauncher<Intent> settingsLauncher;
    private SQLiteDatabase database;
    private int defaultDuration1 = 5; // Default duration in seconds
    private int defaultDuration2 = 5; // Default duration in minutes


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

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                } else {
                    String ageString = ageEditText.getText().toString();
                    addUserToDatabase(username, ageString);
                    int age = Integer.parseInt(ageString);

                    // Retrieve the selected duration values from SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    int duration1 = preferences.getInt("duration1", defaultDuration1);
                    int duration2 = preferences.getInt("duration2", defaultDuration2);

                    Intent intent = new Intent(RegisterActivity.this, StroopActivity.class);
                    intent.putExtra("age", age);
                    intent.putExtra("duration1", duration1);
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
