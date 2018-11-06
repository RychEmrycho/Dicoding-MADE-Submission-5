package com.developnerz.moviisky.webservices.services;

import com.developnerz.moviisky.BuildConfig;
import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.response.SearchTopRatedResponse;
import com.developnerz.moviisky.modules.search.SearchContract;
import com.developnerz.moviisky.utils.Variables;
import com.developnerz.moviisky.webservices.MoviiSkyAPI;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rych Emrycho on 8/29/2018 at 3:27 PM.
 * Updated by Rych Emrycho on 8/29/2018 at 3:27 PM.
 */
public class SearchService implements SearchContract.Service {

    @Inject
    MoviiSkyAPI api;

    private SearchContract.Presenter presenter;

    public SearchService(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void getMoviesWithTitle(String titleQuery) {
        Call<SearchTopRatedResponse> call = api.getMoviesWithTitle(BuildConfig.TMDB_API_KEY, titleQuery);
        call.enqueue(new Callback<SearchTopRatedResponse>() {
            @Override
            public void onResponse(Call<SearchTopRatedResponse> call, Response<SearchTopRatedResponse> response) {
                //MoviesData.setMoviesData(response.body().getResults());
                if (response.isSuccessful()){
                    presenter.setMoviesData(response.body().getResults());
                } else {
                    presenter.setEmptyOrFailed("Ada masalah");
                }
            }

            @Override
            public void onFailure(Call<SearchTopRatedResponse> call, Throwable t) {
                presenter.setEmptyOrFailed("Ada masalah");
            }
        });
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
