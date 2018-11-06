package com.developnerz.moviisky.modules.main;

import android.content.Context;

import com.developnerz.moviisky.adapters.NowPlayingAdapter;
import com.developnerz.moviisky.adapters.SearchAdapter;
import com.developnerz.moviisky.adapters.TopRatedAdapter;
import com.developnerz.moviisky.adapters.UpcomingAdapter;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.main.fragment.UpcomingFragment;
import com.developnerz.moviisky.modules.setting.SettingContract;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;

import java.util.List;

/**
 * Created by Rych Emrycho on 8/22/2018 at 11:18 PM.
 * Updated by Rych Emrycho on 8/22/2018 at 11:18 PM.
 */
public class MainContract {
    public interface View {
        void setMoviesData(int reference, List<Movie> moviesData);
        void setEmptyOrFailed(int reference, String message);
    }

    public interface Presenter {
        void getTopRatedMovies();
        void getNowPlayingMovies();
        void getUpcomingMovies();
        void setView(MainContract.View view);
        void setMoviesData(int reference, List<Movie> moviesData);
        void setEmptyOrFailed(int reference, String message);
    }

    public interface Service {
        void getTopRatedMovies();
        void getNowPlayingMovies();
        void getUpcomingMovies();
        void setPresenter(MainContract.Presenter presenter);
        void setPresenter(SettingContract.Presenter presenter);
        void receiverGetNowPlayingMovies();
    }
}
