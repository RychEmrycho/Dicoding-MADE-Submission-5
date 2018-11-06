package com.developnerz.moviisky.modules.favorite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.developnerz.moviisky.R;
import com.developnerz.moviisky.adapters.FavoriteAdapter;
import com.developnerz.moviisky.models.Movie;
import com.developnerz.moviisky.modules.moviedetail.MovieDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.moviisky.utils.db.DatabaseContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.OnClickItemListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview_favorite)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.empty_container)
    LinearLayout emptyContainer;

    private Cursor favList;
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new FavoriteAdapter(this);
        adapter.setMoviesList(favList);
        recyclerView.setAdapter(adapter);

        new LoadFavMovieAsync().execute();
    }

    @Override
    public void OnClickItem(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI + "/" + movie.getIdDatabase());
        intent.setData(uri);
        startActivityForResult(intent, MovieDetailActivity.REQUEST_UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MovieDetailActivity.REQUEST_UPDATE) {
            if (resultCode == MovieDetailActivity.RESULT_DELETE){
                new LoadFavMovieAsync().execute();
            }
        }
    }

    class LoadFavMovieAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver()
                    .query(CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            progressBar.setVisibility(View.GONE);

            favList = cursor;

            if (favList.getCount() == 0){
                recyclerView.setVisibility(View.GONE);
                emptyContainer.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyContainer.setVisibility(View.GONE);
                adapter.setMoviesList(favList);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
