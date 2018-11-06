package com.developnerz.moviisky.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.developnerz.moviisky.MainApplication;
import com.developnerz.moviisky.R;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.about.AboutActivity;
import com.developnerz.moviisky.modules.favorite.FavoriteActivity;
import com.developnerz.moviisky.modules.main.fragment.NowPlayingFragment;
import com.developnerz.moviisky.modules.main.fragment.TopRatedFragment;
import com.developnerz.moviisky.modules.main.fragment.UpcomingFragment;
import com.developnerz.moviisky.modules.search.SearchActivity;
import com.developnerz.moviisky.modules.setting.SettingActivity;
import com.developnerz.moviisky.utils.Variables;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.moviisky.utils.Variables.ACTIVITY_STATE;

public class MainActivity extends AppCompatActivity implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    @Inject MainPresenter presenter;
    @Inject NowPlayingFragment nowPlayingFragment;
    @Inject UpcomingFragment upcomingFragment;
    @Inject TopRatedFragment topRatedFragment;

    private boolean doubleTapBackKeyPressedOnce = false;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((MainApplication) getApplication())
                .getApplicationComponent()
                .inject(this);

        setSupportActionBar(toolbar);

        setUpTabsWithViewPager();
        setUpDrawerListener();

        navigationView.setNavigationItemSelectedListener(this);
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ACTIVITY_STATE = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        item.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_search) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
            return true;
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleTapBackKeyPressedOnce){
            super.onBackPressed();
            finish();
            return;
        }
        if (searchView.isSearchOpen()){
            if (searchView.isSearchOpen()){
                searchView.closeSearch();
            }
        } else if (viewPager.getCurrentItem() == Variables.FRAGMENT_NOW_PLAYING){
            this.doubleTapBackKeyPressedOnce = true;
            Toast.makeText(this, R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTapBackKeyPressedOnce = false;
                }
            }, 1500);
        } else {
            viewPager.setCurrentItem(Variables.FRAGMENT_NOW_PLAYING);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_favorite) {
            ACTIVITY_STATE = 1;
        } else if (id == R.id.nav_search) {
            ACTIVITY_STATE = 2;
        } else if (id == R.id.nav_setting) {
            ACTIVITY_STATE = 3;
        } else if (id == R.id.nav_about) {
            ACTIVITY_STATE = 4;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setUpDrawerListener(){
        final Intent iFavorite = new Intent(this, FavoriteActivity.class);
        final Intent iSearch = new Intent(this, SearchActivity.class);
        final Intent iSetting = new Intent(this, SettingActivity.class);
        final Intent iAbout = new Intent(this, AboutActivity.class);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                navigationView.setCheckedItem(R.id.nav_discover);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (ACTIVITY_STATE == 1){
                    startActivity(iFavorite);
                } else if (ACTIVITY_STATE == 2){
                    startActivity(iSearch);
                } else if (ACTIVITY_STATE == 3){
                    startActivity(iSetting);
                } else if (ACTIVITY_STATE == 4){
                    startActivity(iAbout);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        drawer.addDrawerListener(listener);
    }

    void setUpTabsWithViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(nowPlayingFragment);
        adapter.addFragment(upcomingFragment);
        adapter.addFragment(topRatedFragment);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_now_playing);
        tabs.getTabAt(0).setText(R.string.now_playing);
        tabs.getTabAt(1).setIcon(R.drawable.ic_upcoming);
        tabs.getTabAt(1).setText(R.string.upcoming);
        tabs.getTabAt(2).setIcon(R.drawable.ic_top_rated);
        tabs.getTabAt(2).setText(R.string.top_rated);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void setMoviesData(int reference, List<Movie> moviesData) {
        if (reference == Variables.FRAGMENT_NOW_PLAYING){
            nowPlayingFragment.setMoviesData(moviesData);
        } else if (reference == Variables.FRAGMENT_UPCOMING){
            upcomingFragment.setMoviesData(moviesData);
        } else if (reference == Variables.FRAGMENT_TOP_RATED){
            topRatedFragment.setMoviesData(moviesData);
        }
    }

    @Override
    public void setEmptyOrFailed(int reference, String message) {
        if (reference == Variables.FRAGMENT_NOW_PLAYING){
            nowPlayingFragment.setEmptyLayout(R.drawable.ic_empty_layout, getString(R.string.something_wrong), message);
        } else if (reference == Variables.FRAGMENT_UPCOMING){
            upcomingFragment.setEmptyLayout(R.drawable.ic_empty_layout, getString(R.string.something_wrong), message);
        } else if (reference == Variables.FRAGMENT_TOP_RATED){
            topRatedFragment.setEmptyLayout(R.drawable.ic_empty_layout,  getString(R.string.something_wrong), message);
        }
    }

    public MainPresenter getPresenter() {
        return presenter;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}