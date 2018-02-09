package com.myapp.gargesh.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.myapp.gargesh.utils.Criteria;
import com.myapp.gargesh.utils.Constants;
import com.myapp.gargesh.async.CallMoviesBy;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class MoviesGrid extends AppCompatActivity implements MovieViewAdapter.MovieClickListener {

    private RecyclerView recyclerView;

    private boolean isInitialized() {
        return isInitialized;
    }

    private boolean isInitialized = false;

    private boolean checkForInternetConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected()) {
            return false;
        } else if(netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void initializeWidgets(Criteria criteria) {
        if(!isInitialized()) {
            Toast.makeText(this, getString(R.string.toast_message) + criteria.toString(), Toast.LENGTH_LONG).show();
            recyclerView = findViewById(R.id.rv_movie_thumbnails);
            CallMoviesBy moviesBy = new CallMoviesBy(criteria);
            moviesBy.execute(Constants.API_KEY_VALUE);

            List<MovieDb> movieList = null;

            try {
                MovieResultsPage movieResultPage = moviesBy.get();
                movieList = movieResultPage.getResults();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, GridLayoutManager.VERTICAL);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(new MovieViewAdapter(movieList, this));
            isInitialized = true;
        }
    }

    private void showErrorMessage() {
        findViewById(R.id.tv_internet_available_message).setVisibility(View.VISIBLE);
        findViewById(R.id.rv_movie_thumbnails).setVisibility(View.INVISIBLE);
    }
    private void hideErrorMessage() {
        findViewById(R.id.tv_internet_available_message).setVisibility(View.INVISIBLE);
        findViewById(R.id.rv_movie_thumbnails).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_grid);

        if(!checkForInternetConnectivity()) {
            showErrorMessage();
            return;
        } else {
            hideErrorMessage();
        }

        initializeWidgets(Criteria.NOW_PLAYING);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.movies_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(!checkForInternetConnectivity()) {
            showErrorMessage();
            return true;
        } else {
            hideErrorMessage();
        }



        MovieViewAdapter adapter;

        int menuSelected = item.getItemId();
        switch (menuSelected) {
            case R.id.menu_popular:
                Toast.makeText(this, getString(R.string.toast_message) + Criteria.POPULAR.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(!isInitialized()) {
                        initializeWidgets(Criteria.POPULAR);
                    } else {
                        CallMoviesBy popular = new CallMoviesBy(Criteria.POPULAR);
                        popular.execute(Constants.API_KEY_VALUE);
                        adapter = (MovieViewAdapter) recyclerView.getAdapter();
                        adapter.updateMovieListingsBasedOnCriteria(popular.get().getResults());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu_rating:
                Toast.makeText(this, getString(R.string.toast_message) + Criteria.TOP_RATED.toString(), Toast.LENGTH_LONG).show();
                try {
                    if(!isInitialized()) {
                        initializeWidgets(Criteria.TOP_RATED);
                    } else {
                        CallMoviesBy topRated = new CallMoviesBy(Criteria.TOP_RATED);
                        topRated.execute(Constants.API_KEY_VALUE);
                        adapter = (MovieViewAdapter) recyclerView.getAdapter();
                        adapter.updateMovieListingsBasedOnCriteria(topRated.get().getResults());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }

        return true;
    }

    @Override
    public void onMovieClicked(MovieDb movie) {

        Intent movieDetailIntent = new Intent(this, MovieDetails.class);
        movieDetailIntent.putExtra("title", movie.getTitle());
        movieDetailIntent.putExtra("released", movie.getReleaseDate());
        movieDetailIntent.putExtra("poster", movie.getPosterPath());
        movieDetailIntent.putExtra("vote_average", movie.getVoteAverage());
        movieDetailIntent.putExtra("plot", movie.getOverview());

        startActivity(movieDetailIntent);


    }
}
