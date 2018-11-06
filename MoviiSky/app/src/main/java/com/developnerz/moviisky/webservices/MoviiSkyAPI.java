package com.developnerz.moviisky.webservices;

import com.developnerz.moviisky.models.response.SearchTopRatedResponse;
import com.developnerz.moviisky.models.response.UpcomingNowPlayingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rych Emrycho on 8/26/2018 at 10:01 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 10:01 PM.
 */
public interface MoviiSkyAPI {
    @GET("search/movie")
    Call<SearchTopRatedResponse> getMoviesWithTitle(
            @Query("api_key") String apiKey,
            @Query("query") String movieTitle);

    @GET("movie/top_rated")
    Call<SearchTopRatedResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<UpcomingNowPlayingResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<UpcomingNowPlayingResponse> getUpcomingMovies(@Query("api_key") String apiKey);
}
