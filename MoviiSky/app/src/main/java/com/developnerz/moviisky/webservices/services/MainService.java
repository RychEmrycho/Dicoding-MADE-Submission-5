package com.developnerz.moviisky.webservices.services;

import android.util.Log;

import com.developnerz.moviisky.BuildConfig;
import com.developnerz.moviisky.adapters.NowPlayingAdapter;
import com.developnerz.moviisky.adapters.SearchAdapter;
import com.developnerz.moviisky.adapters.TopRatedAdapter;
import com.developnerz.moviisky.adapters.UpcomingAdapter;
import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.models.response.SearchTopRatedResponse;
import com.developnerz.moviisky.models.response.UpcomingNowPlayingResponse;
import com.developnerz.moviisky.modules.main.MainContract;
import com.developnerz.moviisky.modules.setting.SettingContract;
import com.developnerz.moviisky.modules.setting.SettingPresenter;
import com.developnerz.moviisky.utils.Variables;
import com.developnerz.moviisky.webservices.MoviiSkyAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rych Emrycho on 8/26/2018 at 10:48 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 10:48 PM.
 */
public class MainService implements MainContract.Service {

    @Inject
    MoviiSkyAPI api;

    private MainContract.Presenter presenter;
    private SettingContract.Presenter receiverPresenter;

    public MainService(SettingPresenter presenter) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl("https://api.themoviedb.org/3/")
                .build();
        this.receiverPresenter = presenter;
        api = retrofit.create(MoviiSkyAPI.class);
    }

    public MainService(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void getTopRatedMovies() {
        Call<SearchTopRatedResponse> call = api.getTopRatedMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<SearchTopRatedResponse>() {
            @Override
            public void onResponse(Call<SearchTopRatedResponse> call, Response<SearchTopRatedResponse> response) {
                if (response.isSuccessful()) {
                    //MoviesData.setMoviesData(response.body().getResults());
                    presenter.setMoviesData(Variables.FRAGMENT_TOP_RATED, response.body().getResults());

                } else {
                    //presenter.showSnackBar("Some error Occured..", Snackbar.LENGTH_INDEFINITE);
                    presenter.setEmptyOrFailed(Variables.FRAGMENT_TOP_RATED, "There's something wrong");
                }
            }

            @Override
            public void onFailure(Call<SearchTopRatedResponse> call, Throwable t) {
                presenter.setEmptyOrFailed(Variables.FRAGMENT_TOP_RATED, "There's something wrong");

            }
        });
    }

    @Override
    public void getNowPlayingMovies() {
        Call<UpcomingNowPlayingResponse> call = api.getNowPlayingMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<UpcomingNowPlayingResponse>() {
            @Override
            public void onResponse(Call<UpcomingNowPlayingResponse> call, Response<UpcomingNowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    //MoviesData.setMoviesData(response.body().getResults());
                    presenter.setMoviesData(Variables.FRAGMENT_NOW_PLAYING, response.body().getResults());

                } else {
                    //presenter.showSnackBar("Some error Occured..", Snackbar.LENGTH_INDEFINITE);
                    presenter.setEmptyOrFailed(Variables.FRAGMENT_NOW_PLAYING, "There's something wrong");
                }
            }

            @Override
            public void onFailure(Call<UpcomingNowPlayingResponse> call, Throwable t) {
                presenter.setEmptyOrFailed(Variables.FRAGMENT_NOW_PLAYING, "There's something wrong");

            }
        });
    }

    @Override
    public void getUpcomingMovies() {
        Call<UpcomingNowPlayingResponse> call = api.getUpcomingMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<UpcomingNowPlayingResponse>() {
            @Override
            public void onResponse(Call<UpcomingNowPlayingResponse> call, Response<UpcomingNowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    //MoviesData.setMoviesData(response.body().getResults());
                    presenter.setMoviesData(Variables.FRAGMENT_UPCOMING, response.body().getResults());

                } else {
                    //presenter.showSnackBar("Some error Occured..", Snackbar.LENGTH_INDEFINITE);
                    presenter.setEmptyOrFailed(Variables.FRAGMENT_UPCOMING, "There's something wrong");
                }
            }

            @Override
            public void onFailure(Call<UpcomingNowPlayingResponse> call, Throwable t) {
                presenter.setEmptyOrFailed(Variables.FRAGMENT_UPCOMING, "There's something wrong");
            }
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        receiverPresenter = presenter;
    }

    @Override
    public void receiverGetNowPlayingMovies() {
        Call<UpcomingNowPlayingResponse> call = api.getNowPlayingMovies(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<UpcomingNowPlayingResponse>() {
            @Override
            public void onResponse(Call<UpcomingNowPlayingResponse> call, Response<UpcomingNowPlayingResponse> response) {
                if (response.isSuccessful()) {
                    receiverPresenter.setReceiverMoviesData(response.body().getResults());
                    Log.e("Jumlah filem ", "" + (response.body().getResults() == null) + response.body().getResults().size());
                }
            }

            @Override
            public void onFailure(Call<UpcomingNowPlayingResponse> call, Throwable t) {
                presenter.setEmptyOrFailed(Variables.FRAGMENT_NOW_PLAYING, "There's something wrong");
            }
        });
    }

}
