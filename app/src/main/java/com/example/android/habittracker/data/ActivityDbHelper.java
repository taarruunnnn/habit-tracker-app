package com.example.android.habittracker.data;

/**
 * Created by Younes on 06/07/2017.
 */

import com.example.android.habittracker.data.ActivityContract.ActivityEntry;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
/**
 * Database helper for Habit Tracker app. Manages database creation and version management.
 */
public class ActivityDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ActivityDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "journal.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ActivityDbHelper}.
     *
     * @param context of the app
     */
    public ActivityDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the activities table
        String SQL_CREATE_ACTIVITIES_TABLE =  "CREATE TABLE " + ActivityEntry.TABLE_NAME + " ("
                + ActivityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ActivityEntry.COLUMN_ACTIVITY_DESCRIPTION + " TEXT NOT NULL, "
                + ActivityEntry.COLUMN_ACTIVITY_CATEGORY + " INTEGER NOT NULL, "
                + ActivityEntry.COLUMN_ACTIVITY_MOMENT + " INTEGER NOT NULL, "
                + ActivityEntry.COLUMN_ACTIVITY_DURATION + " INTEGER NOT NULL DEFAULT 1, "
                + ActivityEntry.COLUMN_ACTIVITY_PLACE + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ACTIVITIES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
