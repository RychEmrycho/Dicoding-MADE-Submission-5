package com.developnerz.moviisky.modules.setting;

import android.util.Log;

import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;
import com.developnerz.moviisky.webservices.services.MainService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rych Emrycho on 9/11/2018 at 12:07 PM.
 * Updated by Rych Emrycho on 9/11/2018 at 12:07 PM.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private AlarmReceiver alarmReceiver;
    private MainService service;

    public SettingPresenter(AlarmReceiver alarmReceiver) {
        this.alarmReceiver = alarmReceiver;
        service = new MainService(this);
        //service.setPresenter(this);
    }

    public SettingPresenter(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void setReceiverMoviesData(List<Movie> moviesData) {
        alarmReceiver.setReceiverMoviesData(moviesData);
        Log.e("AlarmReceiver ", " dipanggil");
    }

    @Override
    public void receiverGetNowPlayingMovies() {
        service.receiverGetNowPlayingMovies();
    }
}
