package com.example.stroopgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, "
                + UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }

//    public long addUser(String user, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("username", user);
//        contentValues.put("password", password);
//        long res = db.insert("users.db", null, contentValues);
//        db.close();
//        return res;
//    }
//
//    public boolean checkUser(String username, String password) {
//        String[] columns = { COL_1 };
//        SQLiteDatabase db = getReadableDatabase();
//        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
//        String[] selectionArgs = { username, password };
//        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (count > 0)
//            return true;
//        else
//            return false;
//    }
}
