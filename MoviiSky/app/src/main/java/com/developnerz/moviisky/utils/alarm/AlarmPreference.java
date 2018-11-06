package com.developnerz.moviisky.utils.alarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rych Emrycho on 9/11/2018 at 2:37 AM.
 * Updated by Rych Emrycho on 9/11/2018 at 2:37 AM.
 */
public class AlarmPreference {
    private final String PREF_NAME = "AlarmPreference";
    private final String KEY_DAILY_REMINDER = "dailyreminder";
    private final String KEY_RELEASE_TODAY = "releasetoday";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AlarmPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setDailyReminder(boolean status) {
        editor.putBoolean(KEY_DAILY_REMINDER, status);
        editor.commit();
    }

    public void setTodayRelease(boolean status) {
        editor.putBoolean(KEY_RELEASE_TODAY, status);
        editor.commit();
    }

    public boolean getDailyReminder() {
        return sharedPreferences.getBoolean(KEY_DAILY_REMINDER, false);
    }

    public boolean getTodayRelease() {
        return sharedPreferences.getBoolean(KEY_RELEASE_TODAY, false);
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
