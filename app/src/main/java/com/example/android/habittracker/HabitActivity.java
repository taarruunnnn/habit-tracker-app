package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.ActivityDbHelper;
import com.example.android.habittracker.data.ActivityContract.ActivityEntry;

/**
 * Displays list of activities that were entered and stored in the app.
 */
public class HabitActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private ActivityDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new ActivityDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the activities database.
     */
    private void displayDatabaseInfo() {

        Cursor cursor = readAllHActivities();

        TextView displayView = (TextView) findViewById(R.id.text_view_activity);

        try {
            displayView.setText("The activities table contains " + cursor.getCount() + " activities.\n\n");
            displayView.append(ActivityEntry._ID + " - " +
                    ActivityEntry.COLUMN_ACTIVITY_DESCRIPTION + " - " +
                    ActivityEntry.COLUMN_ACTIVITY_CATEGORY + " - " +
                    ActivityEntry.COLUMN_ACTIVITY_MOMENT + " - " +
                    ActivityEntry.COLUMN_ACTIVITY_DURATION + " - " +
                    ActivityEntry.COLUMN_ACTIVITY_PLACE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ActivityEntry._ID);
            int descriptionColumnIndex = cursor.getColumnIndex(ActivityEntry.COLUMN_ACTIVITY_DESCRIPTION);
            int categoryColumnIndex = cursor.getColumnIndex(ActivityEntry.COLUMN_ACTIVITY_CATEGORY);
            int momentColumnIndex = cursor.getColumnIndex(ActivityEntry.COLUMN_ACTIVITY_MOMENT);
            int durationColumnIndex = cursor.getColumnIndex(ActivityEntry.COLUMN_ACTIVITY_DURATION);
            int placeColumnIndex = cursor.getColumnIndex(ActivityEntry.COLUMN_ACTIVITY_PLACE);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the element
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentCategory = cursor.getInt(categoryColumnIndex);
                int currentMoment = cursor.getInt(momentColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                String currentPlace = cursor.getString(placeColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentDescription + " - " +
                        currentCategory + " - " +
                        currentMoment + " - " +
                        currentDuration + " - " +
                        currentPlace));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public Cursor readAllHActivities() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ActivityEntry._ID,
                ActivityEntry.COLUMN_ACTIVITY_DESCRIPTION,
                ActivityEntry.COLUMN_ACTIVITY_CATEGORY,
                ActivityEntry.COLUMN_ACTIVITY_MOMENT,
                ActivityEntry.COLUMN_ACTIVITY_DURATION,
                ActivityEntry.COLUMN_ACTIVITY_PLACE };

        // Perform a query on the activities table
        Cursor cursor = db.query(
                ActivityEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }
}