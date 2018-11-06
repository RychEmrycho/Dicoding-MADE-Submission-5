package com.developnerz.moviisky.di.components;

import com.developnerz.moviisky.di.modules.ApplicationModule;
import com.developnerz.moviisky.di.modules.WebServiceModule;
import com.developnerz.moviisky.utils.alarm.AlarmReceiver;
import com.developnerz.moviisky.modules.main.MainActivity;
import com.developnerz.moviisky.modules.main.MainPresenter;
import com.developnerz.moviisky.modules.main.fragment.NowPlayingFragment;
import com.developnerz.moviisky.modules.main.fragment.TopRatedFragment;
import com.developnerz.moviisky.modules.main.fragment.UpcomingFragment;
import com.developnerz.moviisky.modules.search.SearchActivity;
import com.developnerz.moviisky.modules.search.SearchPresenter;
import com.developnerz.moviisky.modules.setting.SettingActivity;
import com.developnerz.moviisky.modules.setting.SettingPresenter;
import com.developnerz.moviisky.webservices.services.MainService;
import com.developnerz.moviisky.webservices.services.SearchService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Rych Emrycho on 8/26/2018 at 9:33 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 9:33 PM.
 */
@Component(modules = {ApplicationModule.class, WebServiceModule.class})
@Singleton
public interface ApplicationComponent {

    //Activity
    void inject(MainActivity activity);
    void inject(SearchActivity activity);

    //Fragment
    void inject(NowPlayingFragment fragment);
    void inject(UpcomingFragment fragment);
    void inject(TopRatedFragment fragment);

    //Presenter
    void inject(MainPresenter presenter);
    void inject(SearchPresenter presenter);

    //Service
    void inject(MainService service);
    void inject(SearchService service);

    void inject(SettingActivity activity);
    void inject(AlarmReceiver alarmReceiver);
    void inject(SettingPresenter presenter);

}
