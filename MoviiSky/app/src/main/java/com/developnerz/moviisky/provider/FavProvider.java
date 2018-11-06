package com.developnerz.moviisky.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.developnerz.moviisky.utils.db.DatabaseContract;
import com.developnerz.moviisky.utils.db.FavHelper;

import static com.developnerz.moviisky.utils.db.DatabaseContract.AUTHORITY;
import static com.developnerz.moviisky.utils.db.DatabaseContract.CONTENT_URI;

/**
 * Created by Rych Emrycho on 9/3/2018 at 12:47 AM.
 * Updated by Rych Emrycho on 9/3/2018 at 12:47 AM.
 */
public class FavProvider extends ContentProvider {
    private static final int FAV = 1;
    private static final int FAV_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAV, FAV);

        // content://com.dicoding.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAV + "/#",
                FAV_ID);
    }

    private FavHelper favHelper;

    @Override
    public boolean onCreate() {
        favHelper = new FavHelper(getContext());
        favHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                cursor = favHelper.queryProvider();
                break;
            case FAV_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added;

        switch (sUriMatcher.match(uri)) {
            case FAV:
                added = favHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                updated = favHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                deleted = favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
