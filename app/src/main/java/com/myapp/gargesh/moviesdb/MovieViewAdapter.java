package com.myapp.gargesh.moviesdb;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.gargesh.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Adapter for the RecyclerView to display Movies as a grid.
 * Created by gargesh on 02/02/18.
 */

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieViewHolder> {

    private List<MovieDb> getmMoviesList() {
        return mMoviesList;
    }

    private void setmMoviesList(List<MovieDb> mMoviesList) {
        this.mMoviesList = mMoviesList;
    }

    public interface MovieClickListener {
        void onMovieClicked(MovieDb movie);
    }

    public void updateMovieListingsBasedOnCriteria(List<MovieDb> moviesList) {

        List<MovieDb> oldList = getmMoviesList();
        setmMoviesList(moviesList);
        MovieDiffUtilCallback callback = new MovieDiffUtilCallback(oldList, moviesList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        result.dispatchUpdatesTo(this);

    }

    private List<MovieDb> mMoviesList;
    private MovieClickListener mMovieClickListener;
    public MovieViewAdapter(List<MovieDb> moviesList, MovieClickListener movieClickListener) {

        setmMoviesList(moviesList);
        mMovieClickListener = movieClickListener;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layOutId = R.layout.movie_list;
        View view = LayoutInflater.from(context).inflate(layOutId, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return getmMoviesList().size();
    }

    public class MovieViewHolder extends ViewHolder  {

        ImageView mMovieThumbnail;
        Context mContextForPicasso;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieThumbnail = itemView.findViewById(R.id.iv_poster);

            mContextForPicasso = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    mMovieClickListener.onMovieClicked(getmMoviesList().get(pos));
                }
            });
        }

        public void bind(int position) {

            MovieDb movie = getmMoviesList().get(position);
            String posterPath = movie.getPosterPath();

            Picasso.with(mContextForPicasso)
                    .load(Constants.POSTER_URL + Constants.POSTER_SIZE + posterPath)
                    .into(mMovieThumbnail);

        }

    }
}
