package com.developnerz.moviisky.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developnerz.moviisky.R;
import com.developnerz.moviisky.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rych Emrycho on 7/21/2018 at 12:00 PM.
 * Updated by Rych Emrycho on 7/21/2018 at 12:00 PM.
 */
public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {

    private List<Movie> movies;
    private OnClickItemListener onClickItemListener;

    public NowPlayingAdapter(List<Movie> movies, OnClickItemListener onClickItemListener) {
        this.movies = movies;
        this.onClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recyclerview_item_layout, parent, false);
        return new ViewHolder(view);
    }

    private void setFieldEmpty(TextView view) {
        view.setLines(1);
        view.setText(view.getText().toString() + " -");
    }

    public interface OnClickItemListener {
        void OnClickItem(Movie movie);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movieCard.setOnClickListener(view -> {
            onClickItemListener.OnClickItem(movies.get(position));
        });
        holder.tv_movie_title.setText(movies.get(position).getTitle());

        if (TextUtils.isEmpty(movies.get(position).getOverview())) {
            setFieldEmpty(holder.tv_movie_description);
        } else {
            holder.tv_movie_description.setText(movies.get(position).getOverview());
        }

        if (TextUtils.isEmpty(movies.get(position).getReleaseDate())) {
            setFieldEmpty(holder.tv_movie_date);
        } else {
            holder.tv_movie_date.setText("Release Date: " + movies.get(position).getReleaseDate());
        }

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w342/" + movies.get(position).getPosterPath())
                .placeholder(R.drawable.img_no_movie_poster)
                //.error(R.mipmap.ic_launcher_round)
                .into(holder.img_movie_poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movieCard)
        CardView movieCard;

        @BindView(R.id.img_movie_poster)
        ImageView img_movie_poster;

        @BindView(R.id.tv_movie_title)
        TextView tv_movie_title;

        @BindView(R.id.tv_movie_description)
        TextView tv_movie_description;

        @BindView(R.id.tv_movie_date)
        TextView tv_movie_date;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
