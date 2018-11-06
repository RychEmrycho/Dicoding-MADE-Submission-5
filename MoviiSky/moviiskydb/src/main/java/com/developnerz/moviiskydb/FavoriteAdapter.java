package com.developnerz.moviiskydb;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developnerz.moviiskydb.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.developnerz.moviiskydb.DatabaseContract.FavColumns.OVERVIEW;
import static com.developnerz.moviiskydb.DatabaseContract.FavColumns.POSTER_PATH;
import static com.developnerz.moviiskydb.DatabaseContract.FavColumns.RELEASE_DATE;
import static com.developnerz.moviiskydb.DatabaseContract.FavColumns.TITLE;
import static com.developnerz.moviiskydb.DatabaseContract.getColumnString;

/**
 * Created by Rych Emrycho on 7/21/2018 at 12:00 PM.
 * Updated by Rych Emrycho on 7/21/2018 at 12:00 PM.
 */
public class FavoriteAdapter extends CursorAdapter {

    private OnClickItemListener onClickItemListener;

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_layout, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    public interface OnClickItemListener {
        void OnClickItem();
    }

    private void setFieldEmpty(TextView view) {
        view.setLines(1);
        view.setText(view.getText().toString() + " -");
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            CardView movieCard = (CardView) view.findViewById(R.id.movieCard);
            ImageView img_movie_poster = (ImageView) view.findViewById(R.id.img_movie_poster);
            TextView tv_movie_title = (TextView) view.findViewById(R.id.tv_movie_title);
            TextView tv_movie_description = (TextView) view.findViewById(R.id.tv_movie_description);
            TextView tv_movie_date = (TextView) view.findViewById(R.id.tv_movie_date);

//            movieCard.setOnClickListener(views -> {
//                onClickItemListener.OnClickItem();
//            });

            tv_movie_title.setText(getColumnString(cursor, TITLE));

            if (TextUtils.isEmpty(getColumnString(cursor, OVERVIEW))) {
                setFieldEmpty(tv_movie_description);
            } else {
                tv_movie_description.setText(getColumnString(cursor, OVERVIEW));
            }

            if (TextUtils.isEmpty(getColumnString(cursor, RELEASE_DATE))) {
                setFieldEmpty(tv_movie_date);
            } else {
                tv_movie_date.setText("Release Date: " + getColumnString(cursor, RELEASE_DATE));
            }

            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w342/" + getColumnString(cursor, POSTER_PATH))
                    .placeholder(R.drawable.img_no_movie_poster)
                    //.error(R.mipmap.ic_launcher_round)
                    .into(img_movie_poster);
        }
    }
}