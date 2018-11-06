package com.developnerz.moviisky.modules.setting;

import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;

import java.util.List;

/**
 * Created by Rych Emrycho on 9/11/2018 at 12:06 PM.
 * Updated by Rych Emrycho on 9/11/2018 at 12:06 PM.
 */
public class SettingContract {

    public interface Presenter {
        void setReceiverMoviesData(List<Movie> moviesData);
        void receiverGetNowPlayingMovies();
    }

    public interface Receiver {
        void setReceiverMoviesData(List<Movie> moviesData);
    }
}
