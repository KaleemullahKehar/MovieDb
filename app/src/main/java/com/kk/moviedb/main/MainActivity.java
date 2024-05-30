package com.kk.moviedb.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;

import com.kk.moviedb.adapter.MovieAdapter;
import com.kk.moviedb.model.Movie;
import com.kk.moviedb.R;
import com.kk.moviedb.network.TMDBApi;
import com.kk.moviedb.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(movieList, this);
        recyclerView.setAdapter(movieAdapter);

        fetchMovies();
    }

    private void fetchMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TMDBApi tmdbApi = retrofit.create(TMDBApi.class);
        Call<MovieResponse> call = tmdbApi.getPopularMovies("99e3b7c68c146700906f0a70133ba340");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovies();
                    movieList.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "onResponse: Response is not successful");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
