package com.developnerz.moviisky.modules.main;

import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;
import com.developnerz.moviisky.webservices.services.MainService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rych Emrycho on 8/22/2018 at 11:18 PM.
 * Updated by Rych Emrycho on 8/22/2018 at 11:18 PM.
 */
public class MainPresenter implements MainContract.Presenter {

    @Inject
    MainService service;

    private MainContract.View view;

    public MainPresenter(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    public void getTopRatedMovies() {
        service.getTopRatedMovies();
    }

    @Override
    public void getNowPlayingMovies() {
        service.getNowPlayingMovies();
    }

    @Override
    public void getUpcomingMovies() {
        service.getUpcomingMovies();
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
        service.setPresenter(this);
    }

    @Override
    public void setMoviesData(int reference, List<Movie> moviesData) {
        view.setMoviesData(reference, moviesData);
    }

    @Override
    public void setEmptyOrFailed(int reference, String message) {
        view.setEmptyOrFailed(reference, message);
    }

}
