package com.example.stroopgame;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ResultsContentValues extends SQLiteOpenHelper {
    public static final String DB_NAME = "users.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Stroop";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_TOTAL_PERCENT = "total_percent";
    public static final String COLUMN_RED_PERCENT = "red_percent";
    public static final String COLUMN_BLUE_PERCENT = "blue_percent";
    public static final String COLUMN_GREEN_PERCENT = "green_percent";
    public static final String COLUMN_YELLOW_PERCENT = "yellow_percent";
    public static final String COLUMN_RED_CORRECT_PERCENT = "red_correct_percent";
    public static final String COLUMN_BLUE_CORRECT_PERCENT = "blue_correct_percent";
    public static final String COLUMN_GREEN_CORRECT_PERCENT = "green_correct_percent";
    public static final String COLUMN_YELLOW_CORRECT_PERCENT = "yellow_correct_percent";
    public static final String COLUMN_BLUE_INCORRECT_PERCENT = "blue_incorrect_percent";
    public static final String COLUMN_RED_INCORRECT_PERCENT = "red_incorrect_percent";
    public static final String COLUMN_GREEN_INCORRECT_PERCENT = "green_incorrect_percent";
    public static final String COLUMN_YELLOW_INCORRECT_PERCENT = "yellow_incorrect_percent";
    public static final String COLUMN_TOTAL_RESPONSE_TIME = "total_response_time";
    public static final String COLUMN_CONGRUENT_RESPONSE_TIME = "congruent_response_time";
    public static final String COLUMN_NON_CONGRUENT_RESPONSE_TIME = "non_congruent_response_time";
    public static final String COLUMN_STROOP_EFFECT = "stroop_effect";

    public ResultsContentValues(Context context) {
        super(context, DB_NAME, null  , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+"("+COLUMN_USERNAME + "VARCHAR,"+ COLUMN_TOTAL_PERCENT + "DOUBLE," + COLUMN_RED_PERCENT + "DOUBLE,"+ COLUMN_BLUE_PERCENT + "DOUBLE,"
                + COLUMN_GREEN_PERCENT + "DOUBLE," + COLUMN_YELLOW_PERCENT + "DOUBLE," + COLUMN_RED_CORRECT_PERCENT + "DOUBLE," + COLUMN_BLUE_CORRECT_PERCENT + "DOUBLE,"
                + COLUMN_GREEN_CORRECT_PERCENT + "DOUBLE," + COLUMN_YELLOW_CORRECT_PERCENT + "DOUBLE," + COLUMN_RED_INCORRECT_PERCENT + "DOUBLE," + COLUMN_BLUE_INCORRECT_PERCENT + "DOUBLE,"
                + COLUMN_GREEN_INCORRECT_PERCENT + "DOUBLE," + COLUMN_YELLOW_INCORRECT_PERCENT + "DOUBLE," + COLUMN_TOTAL_RESPONSE_TIME + "DOUBLE," + COLUMN_CONGRUENT_RESPONSE_TIME + "DOUBLE,"
                + COLUMN_NON_CONGRUENT_RESPONSE_TIME + "DOUBLE," + COLUMN_STROOP_EFFECT + "DOUBLE)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void  createContentValues(String username, Double totalPercent,double redPercentage, double bluePercentage,double greenPercentage,double yellowPercentage,double redCorrectPer,double blueCorrectPer,double greenCorrectPer,
                                     double yellowCorrectPer,double redIncorrectPer,double blueIncorrectPer,double greenIncorrectPer,double yellowIncorrectPer,
                                     Double totalAvgResponseTime,Double congruentAvgResponseTime,Double nonCongruentAvgResponseTime,Double stroopEffect) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_TOTAL_PERCENT, totalPercent);
        contentValues.put(COLUMN_RED_PERCENT, redPercentage);
        contentValues.put(COLUMN_BLUE_PERCENT, bluePercentage);
        contentValues.put(COLUMN_GREEN_PERCENT, greenPercentage);
        contentValues.put(COLUMN_YELLOW_PERCENT, yellowPercentage);
        contentValues.put(COLUMN_RED_CORRECT_PERCENT, redCorrectPer);
        contentValues.put(COLUMN_BLUE_CORRECT_PERCENT, blueCorrectPer);
        contentValues.put(COLUMN_GREEN_CORRECT_PERCENT, greenCorrectPer);
        contentValues.put(COLUMN_YELLOW_CORRECT_PERCENT, yellowCorrectPer);
        contentValues.put(COLUMN_RED_INCORRECT_PERCENT, redIncorrectPer);
        contentValues.put(COLUMN_BLUE_INCORRECT_PERCENT, blueIncorrectPer);
        contentValues.put(COLUMN_GREEN_INCORRECT_PERCENT, greenIncorrectPer);
        contentValues.put(COLUMN_YELLOW_INCORRECT_PERCENT, yellowIncorrectPer);
        contentValues.put(COLUMN_TOTAL_RESPONSE_TIME, totalAvgResponseTime);
        contentValues.put(COLUMN_CONGRUENT_RESPONSE_TIME, congruentAvgResponseTime);
        contentValues.put(COLUMN_NON_CONGRUENT_RESPONSE_TIME, nonCongruentAvgResponseTime);
        contentValues.put(COLUMN_STROOP_EFFECT, stroopEffect);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, contentValues);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


}