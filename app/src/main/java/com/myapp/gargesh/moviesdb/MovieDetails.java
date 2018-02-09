package com.myapp.gargesh.moviesdb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.gargesh.utils.Constants;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView tv_movie_title = findViewById(R.id.tv_movie_title);
        TextView tv_release_date = findViewById(R.id.tv_release_date);
        TextView tv_vote_average = findViewById(R.id.tv_vote_average);
        TextView tv_plot_synopsis = findViewById(R.id.tv_plot_synopsis);
        ImageView iv_poster = findViewById(R.id.iv_poster);

        Intent movieDetailIntent = getIntent();

        tv_movie_title.setText(movieDetailIntent.getStringExtra("title"));
        tv_release_date.setText(getString(R.string.release_date_prefix) + " " + movieDetailIntent.getStringExtra("released"));
        Picasso.with(this)
                .load(Constants.POSTER_URL + Constants.POSTER_FOR_DETAIL + movieDetailIntent.getStringExtra("poster"))
                .into(iv_poster);
        tv_vote_average.setText(getString(R.string.voting_prefix)  + " " + Float.toString(movieDetailIntent.getFloatExtra("vote_average", 4.0f)));
        tv_plot_synopsis.setText(movieDetailIntent.getStringExtra("plot"));
    }
}
