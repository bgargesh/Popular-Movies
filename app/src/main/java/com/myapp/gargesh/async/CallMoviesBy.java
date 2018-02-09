package com.myapp.gargesh.async;

import android.os.AsyncTask;

import com.myapp.gargesh.utils.Criteria;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * AsyncTask to fetch the Movies data by different criteria.
 * Created by gargesh on 01/02/18.
 */

public class CallMoviesBy extends AsyncTask<String, Void, MovieResultsPage > {

    private MovieResultsPage responseData;
    private Criteria mCriteria;

    /**
     * Constructor to set the criteria;
     * @param criteria Criteria to search the movie
     */
    public CallMoviesBy(Criteria criteria) {
        mCriteria = criteria;
    }

    /**
     * This method fetches the movies page based on the criteria.
     * @param api_key User's api key.
     * @return MovieResulsPage which has the list of Movies to be displayed.
     */
    @Override
    protected MovieResultsPage doInBackground(String... api_key) {

        TmdbMovies movieApi = new TmdbApi(api_key[0]).getMovies();
        switch (mCriteria) {
            case NOW_PLAYING:
                responseData = movieApi.getNowPlayingMovies(null, null,null);
                break;
            case POPULAR:
                responseData = movieApi.getPopularMovies(null, null);
                break;
            case TOP_RATED:
                responseData = movieApi.getTopRatedMovies(null, null);
                break;
        }
        return responseData;
    }


}
