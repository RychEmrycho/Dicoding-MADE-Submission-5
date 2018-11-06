package com.developnerz.moviisky.di.modules;

import com.developnerz.moviisky.MainApplication;
import com.developnerz.moviisky.modules.main.MainActivity;
import com.developnerz.moviisky.modules.main.MainPresenter;
import com.developnerz.moviisky.modules.main.fragment.NowPlayingFragment;
import com.developnerz.moviisky.modules.main.fragment.TopRatedFragment;
import com.developnerz.moviisky.modules.main.fragment.UpcomingFragment;
import com.developnerz.moviisky.modules.search.SearchPresenter;
import com.developnerz.moviisky.modules.setting.SettingPresenter;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;
import com.developnerz.moviisky.webservices.services.MainService;
import com.developnerz.moviisky.webservices.services.SearchService;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rych Emrycho on 8/26/2018 at 9:33 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 9:33 PM.
 */
@Module
public class ApplicationModule {

    private MainApplication application;

    public ApplicationModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MainApplication application(){
        return new MainApplication();
    }

    @Provides
    @Singleton
    MainPresenter mainPresenter(){
        return new MainPresenter(application.getApplicationComponent());
    }

    @Provides
    @Singleton
    MainService mainService(){
        return new MainService(application.getApplicationComponent());
    }

    @Provides
    @Singleton
    MainActivity mainActivity(){
        return new MainActivity();
    }

    @Provides
    @Singleton
    NowPlayingFragment nowPlayingFragment(){
        return new NowPlayingFragment();
    }

    @Provides
    @Singleton
    UpcomingFragment upcomingFragment(){
        return new UpcomingFragment();
    }

    @Provides
    @Singleton
    TopRatedFragment topRatedFragment(){
        return new TopRatedFragment();
    }

    @Provides
    @Singleton
    SearchPresenter searchPresenter(){
        return new SearchPresenter(application.getApplicationComponent());
    }

    @Provides
    @Singleton
    SearchService searchService(){
        return new SearchService(application.getApplicationComponent());
    }

    @Provides
    @Singleton
    AlarmReceiver alarmReceiver(){
        return new AlarmReceiver(application.getApplicationComponent());
    }

    @Provides
    @Singleton
    SettingPresenter presenter(){
        return new SettingPresenter(application.getApplicationComponent());
    }
}
