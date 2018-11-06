package com.developnerz.moviisky.di.modules;

import com.developnerz.moviisky.webservices.MoviiSkyAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rych Emrycho on 8/26/2018 at 9:52 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 9:52 PM.
 */
@Module
public class WebServiceModule {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson){
        return new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    MoviiSkyAPI provideAPI(Retrofit retrofit){
        return retrofit.create(MoviiSkyAPI.class);
    }

}
