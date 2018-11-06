package com.developnerz.moviisky.utils.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.developnerz.moviisky.R;
import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.main.MainActivity;
import com.developnerz.moviisky.modules.main.MainContract;
import com.developnerz.moviisky.modules.main.MainPresenter;
import com.developnerz.moviisky.modules.setting.SettingContract;
import com.developnerz.moviisky.modules.setting.SettingPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static android.support.v4.app.NotificationCompat.DEFAULT_LIGHTS;
import static android.support.v4.app.NotificationCompat.DEFAULT_SOUND;
import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;

/**
 * Created by Rych Emrycho on 9/10/2018 at 11:57 PM.
 * Updated by Rych Emrycho on 9/10/2018 at 11:57 PM.
 */
public class AlarmReceiver extends BroadcastReceiver implements SettingContract.Receiver {
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_MESSAGE = "message";
    public static final String TYPE_REPEATING_DAILY_REMINDER = "DailyReminder";
    public static final String TYPE_REPEATING_RELEASE_TODAY = "ReleaseToday";
    public static final int NOTIF_ID_REPEATING_DAILY_REMINDER = 100;
    public static final int NOTIF_ID_REPEATING_RELEASE_TODAY = 101;

    private Context context;

    public AlarmReceiver(){}

    public AlarmReceiver(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getStringExtra(EXTRA_TYPE) != null && intent.getStringExtra(EXTRA_MESSAGE) != null) {
            if (intent.getStringExtra(EXTRA_TYPE).equals(TYPE_REPEATING_DAILY_REMINDER)) {
                showReminderNotification(context, intent.getStringExtra(EXTRA_TYPE), intent.getStringExtra(EXTRA_MESSAGE), NOTIF_ID_REPEATING_DAILY_REMINDER);
                //Toast.makeText(context, "ACTION DailyReminderNotification CALLED", Toast.LENGTH_LONG).show();
            } else {
                SettingPresenter presenter = new SettingPresenter(this);
                presenter.receiverGetNowPlayingMovies();
            }
        } else {
            AlarmPreference alarmPreference = new AlarmPreference(context);
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equals(ACTION_BOOT_COMPLETED)) {
                if (alarmPreference.getDailyReminder()) {
                    setRepeatingReminder(context, AlarmReceiver.TYPE_REPEATING_DAILY_REMINDER,
                            "07:00", "We are missing you. Please click this notification so that you don't miss something.", AlarmReceiver.NOTIF_ID_REPEATING_DAILY_REMINDER);

                    //Toast.makeText(context, "getDailyReminder()", Toast.LENGTH_LONG).show();
                }

                if (alarmPreference.getTodayRelease()) {
                    setRepeatingReminder(context, AlarmReceiver.TYPE_REPEATING_RELEASE_TODAY,
                            "08:00", "Today", AlarmReceiver.NOTIF_ID_REPEATING_RELEASE_TODAY);

                    //Toast.makeText(context, "getTodayRelease()", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showReminderNotification(Context context, String title, String message, int notifId) {
        NotificationManagerCompat notificationManager;
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(notifId))
                .setSmallIcon(R.drawable.moviisky_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notifId, builder.build());
    }

    public void setRepeatingReminder(Context context, String type, String time, String message, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                pendingIntent);
    }

    public void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        int requestCode = type.equalsIgnoreCase(TYPE_REPEATING_DAILY_REMINDER) ? NOTIF_ID_REPEATING_DAILY_REMINDER : NOTIF_ID_REPEATING_RELEASE_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void setReceiverMoviesData(List<Movie> moviesData) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateNow = dateFormat.format(calendar.getTime());

        //Toast.makeText(context, "ACTION setReceiverMoviesData CALLED", Toast.LENGTH_LONG).show();

        for (Movie movie: moviesData) {
            if (TextUtils.equals(movie.getReleaseDate(), dateNow)) {
                showReminderNotification(context, "Release today: " +movie.getTitle(), movie.getOverview(), movie.getId());
            }
        }
    }
}
