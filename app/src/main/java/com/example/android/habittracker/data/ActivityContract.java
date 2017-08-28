package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Younes on 06/07/2017.
 */

public final class ActivityContract {

    // To prevent someone from accidentally instantiating the contract class,
    // an empty constructor is added.
    private ActivityContract() {}
    
    public static abstract class ActivityEntry implements BaseColumns {

        public static final String TABLE_NAME = "activities";
        public static final String COLUMN_ACTIVITY_DESCRIPTION = "description";
        public static final String COLUMN_ACTIVITY_CATEGORY = "category";
        public static final String COLUMN_ACTIVITY_MOMENT = "moment";
        public static final String COLUMN_ACTIVITY_DURATION = "duration";
        public static final String COLUMN_ACTIVITY_PLACE = "place";

        /**
         * Possible values for the category of the activity.
         */
        public static final int CATEGORY_HEALTH = 0;
        public static final int CATEGORY_SPIRITUAL = 1;
        public static final int CATEGORY_INTELLECTUAL = 2;
        public static final int CATEGORY_MANUAL = 3;
        public static final int CATEGORY_SOCIAL = 4;

        /**
         * Possible values for the moment of the activity.
         */
        public static final int MOMENT_DAWN = 0;
        public static final int MOMENT_MORNING = 1;
        public static final int MOMENT_AFTERNOON = 2;
        public static final int MOMENT_EVENING = 3;
        public static final int MOMENT_NIGHT = 4;
    }
}
