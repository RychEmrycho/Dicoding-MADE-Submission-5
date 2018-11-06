package com.developnerz.moviiskydb;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.moviiskydb.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listview_favorite)
    ListView listView;
    @BindView(R.id.empty_container)
    LinearLayout emptyContainer;

    private FavoriteAdapter adapter;
    private final int LOAD_FAV_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adapter = new FavoriteAdapter(this, null, true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV_ID);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new android.support.v4.content.CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        if (data.getCount() == 0){
            listView.setVisibility(View.GONE);
            emptyContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) adapter.getItem(position);
        int i = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavColumns._ID));
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.setData(Uri.parse(CONTENT_URI+"/"+i));
        startActivity(intent);
    }
}
