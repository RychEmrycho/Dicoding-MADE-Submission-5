package com.developnerz.moviisky.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.developnerz.moviisky.R;
import com.developnerz.moviisky.models.Movie;

import java.util.concurrent.ExecutionException;

import static com.developnerz.moviisky.utils.db.DatabaseContract.CONTENT_URI;

/**
 * Created by Rych Emrycho on 9/11/2018 at 4:06 PM.
 * Updated by Rych Emrycho on 9/11/2018 at 4:06 PM.
 */
public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private int mAppWidgetId;

    private Cursor favList;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private Movie getItem(int position) {
        if (!favList.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(favList);
    }

    @Override
    public void onCreate() {
        favList = mContext.getContentResolver()
                .query(CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
    }

    @Override
    public void onDataSetChanged() {
        //Log.e("onDataSetChanged", "dipanggil");
        if (favList != null) {
            favList.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        favList = mContext.getContentResolver()
                .query(CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (favList != null) {
            favList.close();
        }
    }

    @Override
    public int getCount() {
        if (favList == null) {
            return 0;
        }
        return favList.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = getItem(position);
        Log.e("favList isClosed", "" + favList.isClosed());
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w780/" + movie.getPosterPath())
                    .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }
        rv.setImageViewBitmap(R.id.imageView, bmp);
        rv.setTextViewText(R.id.widgetTitle, movie.getTitle());

        Bundle extras = new Bundle();
        extras.putInt(MoviiSkyWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return favList.moveToPosition(i) ? favList.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}