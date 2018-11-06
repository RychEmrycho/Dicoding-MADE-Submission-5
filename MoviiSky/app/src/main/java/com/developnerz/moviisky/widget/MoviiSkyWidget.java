package com.developnerz.moviisky.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.developnerz.moviisky.R;
import com.developnerz.moviisky.provider.FavProvider;

import static com.developnerz.moviisky.modules.moviedetail.MovieDetailActivity.ACTION_MOVIISKYDB_UPDATED;

/**
 * Implementation of App Widget functionality.
 */
public class MoviiSkyWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.developnerz.moviisky.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.developnerz.moviisky.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.movii_sky_widget);

        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, MoviiSkyWidget.class);
        toastIntent.setAction(MoviiSkyWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        //Toast.makeText(context, "ACTION_MOVIISKYDB_UPDATED CALLED ON RECEIVE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
                Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
            }

            if (intent.getAction().equals(ACTION_MOVIISKYDB_UPDATED)) {
                //ComponentName componentName = new ComponentName(context, FavProvider.class);
                //mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(componentName), R.id.stack_view);
                mgr.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack_view);

                //Toast.makeText(context, "ACTION_MOVIISKYDB_UPDATED", Toast.LENGTH_SHORT).show();
            }
        }
        super.onReceive(context, intent);
    }
}