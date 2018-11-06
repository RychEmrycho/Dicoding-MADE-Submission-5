package com.developnerz.moviisky.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.IS_ADULT;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.LANGUAGE;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.MOVIE_ID;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.OVERVIEW;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.POPULARITY;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.POSTER_PATH;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.RELEASE_DATE;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.TITLE;
import static com.developnerz.moviisky.utils.db.DatabaseContract.FavColumns.VOTE_AVERAGE;
import static com.developnerz.moviisky.utils.db.DatabaseContract.TABLE_FAV;

/**
 * Created by Rych Emrycho on 8/31/2018 at 12:35 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 12:35 AM.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbfavorit";
    private static int DATABASE_VERSION = 1;
    private static String CREATE_TABLE_FAV = "create table "
            + TABLE_FAV + "(" + _ID + " integer primary key autoincrement, "
            + MOVIE_ID + " int not null, "
            + TITLE + " text not null, "
            + OVERVIEW + " text not null, "
            + LANGUAGE + " text not null, "
            + VOTE_AVERAGE + " double not null, "
            + POPULARITY + " double not null, "
            + IS_ADULT + " text not null, "
            + RELEASE_DATE + " text not null, "
            + POSTER_PATH + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(sqLiteDatabase);
    }
}
