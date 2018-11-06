package com.developnerz.moviisky.modules.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developnerz.moviisky.MainApplication;
import com.developnerz.moviisky.R;
import com.developnerz.moviisky.adapters.SearchAdapter;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.moviedetail.MovieDetailActivity;
import com.developnerz.moviisky.utils.Variables;
import com.developnerz.moviisky.utils.networking.CheckInternetAvailability;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, SearchAdapter.OnClickItemListener {

    private String searchQuery;
    private List<Movie> moviesData;

    @Inject SearchPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.recyclerview_search_movie)
    RecyclerView recyclerView;
    @BindView(R.id.empty_container)
    LinearLayout emptyContainer;
    @BindView(R.id.tv_search_query)
    TextView emptySearchQuery;
    @BindView(R.id.img_empty_icon)
    ImageView emptyIcon;
    @BindView(R.id.tv_empty_title)
    TextView emptyTitle;
    @BindView(R.id.tv_empty_message)
    TextView emptyMessage;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ((MainApplication) getApplication()).getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        presenter.setView(this);

        setSupportActionBar(toolbar);
        setUpMaterialSearch();


        swipeRefreshLayout.setOnRefreshListener(() -> refreshData());

        if (savedInstanceState != null) {
            moviesData = savedInstanceState.getParcelableArrayList(Variables.ACTIVITY_SEARCH_TAG);
        }
        setUpLayout();
    }

    void setUpLayout() {
        if (moviesData == null || moviesData.isEmpty()){
            setEmptyLayout(R.drawable.ic_search_greeting, getString(R.string.title_search_greeting),
                    getString(R.string.message_search_greeting));
            refreshData();
        } else {
            setMoviesData(moviesData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (moviesData != null) {
            outState.putParcelableArrayList(Variables.ACTIVITY_SEARCH_TAG, new ArrayList<>(moviesData));
        }
    }

    void refreshData() {
        if (!TextUtils.isEmpty(searchQuery)){
            presenter.searchMovieWithTitle(searchQuery);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    void hideRefreshing() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setMoviesData(List<Movie> moviesData) {
        this.moviesData = moviesData;
        if (!moviesData.isEmpty()) {
            emptyContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            SearchAdapter movieAdapter = new SearchAdapter(moviesData, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(movieAdapter);
            hideRefreshing();

        } else {
            hideRefreshing();
            recyclerView.setVisibility(View.GONE);
            emptyContainer.setVisibility(View.VISIBLE);
            setEmptyLayout(R.drawable.ic_empty_layout,getString(R.string.title_search_no_connection),
                    getString(R.string.message_search_no_connection));
        }
    }

    @Override
    public void setEmptyLayout(int imageReference, String title, String message) {
        hideRefreshing();
        recyclerView.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.VISIBLE);
        emptyIcon.setImageResource(imageReference);
        emptyTitle.setText(title);
        emptyMessage.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.setHint(getString(R.string.search_movie));
        searchView.showSearch(true);

        return true;
    }

    void setUpMaterialSearch(){
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (CheckInternetAvailability.isInternetAvailable(SearchActivity.this)){
                    emptySearchQuery.setVisibility(View.VISIBLE);
                    emptySearchQuery.setText(getString(R.string.result_search)+" "+query);
                    searchQuery = query;
                    swipeRefreshLayout.setRefreshing(true);
                    presenter.searchMovieWithTitle(query);
                } else {
                    setEmptyLayout(R.drawable.ic_empty_no_internet,getString(R.string.title_search_no_internet),
                            getString(R.string.message_search_no_internet));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void OnClickItem(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}