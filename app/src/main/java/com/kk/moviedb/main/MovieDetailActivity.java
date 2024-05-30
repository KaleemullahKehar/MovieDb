package com.kk.moviedb.main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.moviedb.R;
import com.kk.moviedb.model.Movie;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        TextView title = findViewById(R.id.detail_title);
        TextView releaseDate = findViewById(R.id.detail_release_date);
        TextView overview = findViewById(R.id.detail_overview);
        TextView rating = findViewById(R.id.detail_rating);
        ImageView poster = findViewById(R.id.detail_poster);

        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());
        rating.setText(String.valueOf(movie.getRating()));
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(poster);
    }
}

