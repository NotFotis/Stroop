package com.example.stroopgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        database = openOrCreateDatabase("users", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS users (username VARCHAR, password VARCHAR)");
        database.execSQL("CREATE TABLE IF NOT EXISTS results (username VARCHAR, date DATE, total_percent DOUBLE, red_percent DOUBLE, blue_percent DOUBLE, green_percent DOUBLE, yellow_percent DOUBLE, red_correct DOUBLE, blue_correct DOUBLE, green_correct DOUBLE, yellow_correct DOUBLE, red_incorrect DOUBLE, blue_incorrect DOUBLE, green_incorrect DOUBLE, yellow_incorrect DOUBLE, total_avg_response_time DOUBLE, congruent_avg_response_time DOUBLE, non_congruent_avg_response_time DOUBLE, stroop_effect DOUBLE)");

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                authenticateUser(username, password);
            }
        });
    }

    private void authenticateUser(String username, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, StroopActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
