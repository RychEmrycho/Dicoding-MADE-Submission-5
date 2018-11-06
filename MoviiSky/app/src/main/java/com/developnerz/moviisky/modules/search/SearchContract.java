package com.developnerz.moviisky.modules.search;

import com.developnerz.moviisky.models.Movie;

import java.util.List;

/**
 * Created by Rych Emrycho on 8/29/2018 at 3:21 PM.
 * Updated by Rych Emrycho on 8/29/2018 at 3:21 PM.
 */
public class SearchContract {

    public interface View {
        void setEmptyLayout(int imageReference, String title, String message);
        void setMoviesData(List<Movie> moviesData);
    }

    public interface Presenter {
        void setView(SearchContract.View view);
        void searchMovieWithTitle(String titleQuery);
        void setMoviesData(List<Movie> moviesData);
        void setEmptyOrFailed(String message);
    }

    public interface Service {
        void getMoviesWithTitle(String titleQuery);
        void setPresenter(SearchContract.Presenter presenter);
    }

}
