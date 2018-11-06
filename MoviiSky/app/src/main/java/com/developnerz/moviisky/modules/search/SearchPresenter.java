package com.developnerz.moviisky.modules.search;

import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.webservices.services.SearchService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rych Emrycho on 8/29/2018 at 3:21 PM.
 * Updated by Rych Emrycho on 8/29/2018 at 3:21 PM.
 */
public class SearchPresenter implements SearchContract.Presenter {

    @Inject
    SearchService service;

    private SearchContract.View view;

    public SearchPresenter(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void setView(SearchContract.View view) {
        this.view = view;
        service.setPresenter(this);
    }

    @Override
    public void searchMovieWithTitle(String titleQuery) {
        service.getMoviesWithTitle(titleQuery);
    }

    @Override
    public void setMoviesData(List<Movie> moviesData) {
        view.setMoviesData(moviesData);
    }

    @Override
    public void setEmptyOrFailed(String message) {

    }
}
