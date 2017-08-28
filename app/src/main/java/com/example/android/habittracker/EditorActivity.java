package com.example.android.habittracker;

/**
 * Created by Younes on 06/07/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.ActivityDbHelper;
import com.example.android.habittracker.data.ActivityContract.ActivityEntry;

/**
 * Allows user to create a new activity or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the activity's description */
    private EditText mDescriptionEditText;

    /** EditText field to enter the activity's category */
    private Spinner mCategorySpinner;

    /** EditText field to enter the activity's moment */
    private Spinner mMomentSpinner;

    /** EditText field to enter the activity's duration */
    private EditText mDurationEditText;

    /** EditText field to enter the activity's place */
    private EditText mPlaceEditText;

    /**
     * Category and moment of the activity. The possible values are:
     * For the category : 0 for health, 1 for spiritual, 2 for intellectual, 3 for manual and 4 for social
     * For the moment : 0 for the dawn, 1 for the morning, 2 for the afternoon, 3 for the evening and 4 for the night
     */
    private int mCategory = 0;
    private int mMoment = 0;

    /** Database helper that will provide us access to the database */
    private ActivityDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mDescriptionEditText = (EditText) findViewById(R.id.edit_activity_description);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mMomentSpinner = (Spinner) findViewById(R.id.spinner_moment);
        mDurationEditText = (EditText) findViewById(R.id.edit_activity_duration);
        mPlaceEditText = (EditText) findViewById(R.id.edit_activity_place);

        setupCategorySpinner();
        setupMomentSpinner();

        mDbHelper = new ActivityDbHelper(this);
    }

    /**
     * Setup the dropdown spinner that allow the user to select the category of the activity.
     */
    private void setupCategorySpinner() {
        // Create adapter for spinners. The list options are from the String array it will use
        // the spinners will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapters to the spinners
        mCategorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category_health))) {
                        mCategory = ActivityEntry.CATEGORY_HEALTH; // Health
                    } else if (selection.equals(getString(R.string.category_spiritual))) {
                        mCategory = ActivityEntry.CATEGORY_SPIRITUAL; // Spiritual
                    } else if (selection.equals(getString(R.string.category_intellectual))) {
                        mCategory = ActivityEntry.CATEGORY_INTELLECTUAL; // Intellectual
                    } else if (selection.equals(getString(R.string.category_manual))) {
                        mCategory = ActivityEntry.CATEGORY_MANUAL; // Manual
                    } else {
                        mCategory = ActivityEntry.CATEGORY_SOCIAL; // Social
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = 0; // Health
            }
        });
    }

    /**
     * Setup the dropdown spinner that allow the user to select the moment of the activity.
     */
    private void setupMomentSpinner() {
        // Create adapter for spinners. The list options are from the String array it will use
        // the spinners will use the default layout
        ArrayAdapter momentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_moment_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        momentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapters to the spinners
        mMomentSpinner.setAdapter(momentSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mMomentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.moment_dawn))) {
                        mCategory = ActivityEntry.MOMENT_DAWN; // Dawn
                    } else if (selection.equals(getString(R.string.moment_morning))) {
                        mCategory = ActivityEntry.MOMENT_MORNING; // Morning
                    } else if (selection.equals(getString(R.string.moment_afternoon))) {
                        mCategory = ActivityEntry.MOMENT_AFTERNOON; // Afternoon
                    } else if (selection.equals(getString(R.string.moment_evening))) {
                        mCategory = ActivityEntry.MOMENT_EVENING; // Evening
                    } else {
                        mCategory = ActivityEntry.MOMENT_NIGHT; // Night
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMoment = 0; // Dawn
            }
        });
    }

    /**
     * Get user input from editor and save new activity into the database.
     */
    private void insertActivity(){
        String descriptionString = mDescriptionEditText.getText().toString().trim();
        int durationInt = Integer.parseInt(mDurationEditText.getText().toString().trim());
        String placeString = mPlaceEditText.getText().toString().trim();

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ActivityEntry.COLUMN_ACTIVITY_DESCRIPTION, descriptionString);
        values.put(ActivityEntry.COLUMN_ACTIVITY_CATEGORY, mCategory);
        values.put(ActivityEntry.COLUMN_ACTIVITY_MOMENT, mMoment);
        values.put(ActivityEntry.COLUMN_ACTIVITY_DURATION, durationInt);
        values.put(ActivityEntry.COLUMN_ACTIVITY_PLACE, placeString);

        long newRowId = db.insert(ActivityEntry.TABLE_NAME, null, values);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if (newRowId != -1) {
            text = "Activity saved with Id: " + newRowId;
        }
        else {
            text = "Error with saving activity";
        }
        Toast.makeText(context, text, duration).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertActivity();
                finish();

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}