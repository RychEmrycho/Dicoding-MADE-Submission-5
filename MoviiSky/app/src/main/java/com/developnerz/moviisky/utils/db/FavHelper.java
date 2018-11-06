package com.developnerz.moviisky.utils.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.developnerz.moviisky.models.Movie;

import java.util.ArrayList;

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
 * Created by Rych Emrycho on 9/3/2018 at 12:41 AM.
 * Updated by Rych Emrycho on 9/3/2018 at 12:41 AM.
 */
public class FavHelper {
    private static String DATABASE_TABLE = TABLE_FAV;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavHelper(Context context) {
        this.context = context;
    }

    public FavHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC"
                , null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setIdDatabase(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                movie.setVoteAverage((cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE))));
                movie.setPopularity((cursor.getDouble(cursor.getColumnIndexOrThrow(POPULARITY))));
                movie.setAdult(Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(IS_ADULT))));
                movie.setReleaseDate((cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE))));
                movie.setPosterPath((cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH))));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MOVIE_ID, movie.getId());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(LANGUAGE, movie.getOriginalLanguage());
        initialValues.put(VOTE_AVERAGE, movie.getVoteAverage());
        initialValues.put(POPULARITY, movie.getPopularity());
        initialValues.put(IS_ADULT, movie.getAdult());
        initialValues.put(RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(LANGUAGE, movie.getOriginalLanguage());
        args.put(VOTE_AVERAGE, movie.getVoteAverage());
        args.put(POPULARITY, movie.getPopularity());
        args.put(IS_ADULT, movie.getAdult());
        args.put(RELEASE_DATE, movie.getReleaseDate());
        args.put(POSTER_PATH, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.getIdDatabase() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_FAV, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
