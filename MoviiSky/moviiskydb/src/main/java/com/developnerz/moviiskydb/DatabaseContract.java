package com.developnerz.moviiskydb;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rych Emrycho on 9/3/2018 at 12:38 AM.
 * Updated by Rych Emrycho on 9/3/2018 at 12:38 AM.
 */
public class DatabaseContract {
    public static final String AUTHORITY = "com.developnerz.moviisky";
    public static String TABLE_FAV = "favorite";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class FavColumns implements BaseColumns {
        public static String MOVIE_ID = "movieid";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String LANGUAGE = "language";
        public static String VOTE_AVERAGE = "voteaverage";
        public static String POPULARITY = "popularity";
        public static String IS_ADULT = "isadult";
        public static String RELEASE_DATE = "releasedate";
        public static String POSTER_PATH = "posterpath";
    }
}
