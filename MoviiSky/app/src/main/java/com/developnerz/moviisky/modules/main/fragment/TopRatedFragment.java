package com.developnerz.moviisky.modules.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developnerz.moviisky.MainApplication;
import com.developnerz.moviisky.R;
import com.developnerz.moviisky.adapters.TopRatedAdapter;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.main.MainActivity;
import com.developnerz.moviisky.modules.moviedetail.MovieDetailActivity;
import com.developnerz.moviisky.utils.Variables;
import com.developnerz.moviisky.utils.networking.CheckInternetAvailability;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends Fragment implements TopRatedAdapter.OnClickItemListener {

    private List<Movie> moviesData;

    @BindView(R.id.recyclerview_top_rated)
    RecyclerView recyclerView;
    @BindView(R.id.empty_container)
    LinearLayout emptyContainer;
    @BindView(R.id.img_empty_icon)
    ImageView emptyIcon;
    @BindView(R.id.tv_empty_title)
    TextView emptyTitle;
    @BindView(R.id.tv_empty_message)
    TextView emptyMessage;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(() -> refreshData());

        if (savedInstanceState != null) {
            moviesData = savedInstanceState.getParcelableArrayList(Variables.FRAGMENT_TOP_RATED_TAG);
        }
        setUpLayout();

        return view;
    }

    void setUpLayout() {
        if (moviesData == null || moviesData.isEmpty()){
            setEmptyLayout(R.drawable.ic_top_rated, getString(R.string.title_top_rated_greeting), getString(R.string.message_top_rated_greeting));
            refreshData();
        } else {
            setMoviesData(moviesData);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (moviesData != null) {
            outState.putParcelableArrayList(Variables.FRAGMENT_TOP_RATED_TAG, new ArrayList<>(moviesData));
        }
    }

    void refreshData() {
        if (CheckInternetAvailability.isInternetAvailable(getActivity())){
            ((MainActivity) getActivity()).getPresenter().getTopRatedMovies();
        } else {
            hideRefreshing();
        }
    }

    void hideRefreshing() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void setMoviesData(List<Movie> moviesData) {
        this.moviesData = moviesData;
        if (moviesData.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyContainer.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyContainer.setVisibility(View.GONE);

            TopRatedAdapter movieAdapter = new TopRatedAdapter(moviesData, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(movieAdapter);
            hideRefreshing();
        }
    }

    public void setEmptyLayout(int imageReference, String title, String message) {
        hideRefreshing();
        recyclerView.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.VISIBLE);
        emptyIcon.setImageResource(imageReference);
        emptyTitle.setText(title);
        emptyMessage.setText(message);
    }

    @Override
    public void OnClickItem(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
