package com.example.stroopgame;
import android.content.ContentValues;

public class ResultsContentValues {
    public static final String TABLE_NAME = "results";
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
    public static final String COLUMN_RED_INCORRECT_PERCENT = "red_incorrect_percent";
    public static final String COLUMN_BLUE_INCORRECT_PERCENT = "blue_incorrect_percent";
    public static final String COLUMN_GREEN_INCORRECT_PERCENT = "green_incorrect_percent";
    public static final String COLUMN_YELLOW_INCORRECT_PERCENT = "yellow_incorrect_percent";
    public static final String COLUMN_TOTAL_RESPONSE_TIME = "total_response_time";
    public static final String COLUMN_CONGRUENT_RESPONSE_TIME = "congruent_response_time";
    public static final String COLUMN_NON_CONGRUENT_RESPONSE_TIME = "non_congruent_response_time";
    public static final String COLUMN_STROOP_EFFECT = "stroop_effect";

    public static ContentValues createContentValues(Result results) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, results.getUsername());
        contentValues.put(COLUMN_TOTAL_PERCENT, results.getTotalPercent());
        contentValues.put(COLUMN_RED_PERCENT, results.getRedPercentage());
        contentValues.put(COLUMN_BLUE_PERCENT, results.getBluePercentage());
        contentValues.put(COLUMN_GREEN_PERCENT, results.getGreenPercentage());
        contentValues.put(COLUMN_YELLOW_PERCENT, results.getYellowPercentage());
        contentValues.put(COLUMN_RED_CORRECT_PERCENT, results.getRedCorrectPercentage());
        contentValues.put(COLUMN_BLUE_CORRECT_PERCENT, results.getBlueCorrectPercentage());
        contentValues.put(COLUMN_GREEN_CORRECT_PERCENT, results.getGreenCorrectPercentage());
        contentValues.put(COLUMN_YELLOW_CORRECT_PERCENT, results.getYellowCorrectPercentage());
        contentValues.put(COLUMN_RED_INCORRECT_PERCENT, results.getRedIncorrectPercentage());
        contentValues.put(COLUMN_BLUE_INCORRECT_PERCENT, results.getBlueIncorrectPercentage());
        contentValues.put(COLUMN_GREEN_INCORRECT_PERCENT, results.getGreenIncorrectPercentage());
        contentValues.put(COLUMN_YELLOW_INCORRECT_PERCENT, results.getYellowIncorrectPercentage());
        contentValues.put(COLUMN_TOTAL_RESPONSE_TIME, results.getTotalAvgResponseTime());
        contentValues.put(COLUMN_CONGRUENT_RESPONSE_TIME, results.getCongruentAvgResponseTime());
        contentValues.put(COLUMN_NON_CONGRUENT_RESPONSE_TIME, results.getNonCongruentAvgResponseTime());
        contentValues.put(COLUMN_STROOP_EFFECT, results.getStroopEffect());
        return contentValues;
    }
}