package com.developnerz.moviisky.modules.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import com.developnerz.moviisky.MainApplication;
import com.developnerz.moviisky.R;
import com.developnerz.moviisky.modules.main.MainPresenter;
import com.developnerz.moviisky.utils.alarm.AlarmPreference;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_listview)
    ListView listView;
    @BindView(R.id.switch_daily_reminder)
    Switch switchDailyReminder;
    @BindView(R.id.switch_release_today)
    Switch switchReleaseToday;

    @Inject AlarmReceiver alarmReceiver;
    @Inject SettingPresenter presenter;
    private AlarmPreference alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        ((MainApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        setSupportActionBar(toolbar);

        listView.setAdapter(new ArrayAdapter<>(this,
                R.layout.listview_item_layout,
                new String[] {getString(R.string.change_language)}));
        listView.setOnItemClickListener(((parent, view, position, id) -> {
            if (position == 0){
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        }));

        Log.e("ISNULL?", "" + (presenter == null));
        alarmPreference = new AlarmPreference(this);

        switchDailyReminder.setChecked(alarmPreference.getDailyReminder());
        switchReleaseToday.setChecked(alarmPreference.getTodayRelease());

        switchDailyReminder.setOnClickListener(view -> {
            if (switchDailyReminder.isChecked()) {
                alarmPreference.setDailyReminder(true);
                alarmReceiver.setRepeatingReminder(this, AlarmReceiver.TYPE_REPEATING_DAILY_REMINDER,
                        "07:00", "We are missing you. Please click this notification so that you don't miss something.", AlarmReceiver.NOTIF_ID_REPEATING_DAILY_REMINDER);
            } else {
                alarmPreference.setDailyReminder(false);
                alarmReceiver.cancelReminder(this, AlarmReceiver.TYPE_REPEATING_DAILY_REMINDER);
            }
        });

        switchReleaseToday.setOnClickListener(view -> {
            if (switchReleaseToday.isChecked()) {
                Log.e("Checked", " benar");
                alarmPreference.setTodayRelease(true);
                alarmReceiver.setRepeatingReminder(this, AlarmReceiver.TYPE_REPEATING_RELEASE_TODAY,
                        "08:00", "Today", AlarmReceiver.NOTIF_ID_REPEATING_RELEASE_TODAY);
            } else {
                alarmPreference.setTodayRelease(false);
                alarmReceiver.cancelReminder(this, AlarmReceiver.TYPE_REPEATING_RELEASE_TODAY);
            }
        });
    }

}
