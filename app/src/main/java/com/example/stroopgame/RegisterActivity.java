package com.example.stroopgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button registerButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addUserToDatabase(username, password);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void addUserToDatabase(String username, String password) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);

        long newRowId = database.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error while registering user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
