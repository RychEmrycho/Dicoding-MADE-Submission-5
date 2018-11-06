package com.developnerz.moviisky;

import android.app.Application;

import com.developnerz.moviisky.di.components.ApplicationComponent;
import com.developnerz.moviisky.di.components.DaggerApplicationComponent;
import com.developnerz.moviisky.di.modules.ApplicationModule;

/**
 * Created by Rych Emrycho on 8/25/2018 at 10:13 AM.
 * Updated by Rych Emrycho on 8/25/2018 at 10:13 AM.
 */
public class MainApplication extends Application {

    //Temporary Comment
    //How to use API_KEY
    //BuildConfig.TMDB_API_KEY

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
