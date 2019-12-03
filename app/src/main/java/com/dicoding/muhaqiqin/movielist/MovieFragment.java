package com.dicoding.muhaqiqin.movielist;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment{
    private MovieAdapter movieAdapter;
    private RecyclerView rvMovies;
    private ProgressBar progressBar;

    private String TAG_SHOW = "movie/now_playing";

    private MainViewModel mainViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = getView().findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);

        progressBar = getView().findViewById(R.id.progressBar);

        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(movieAdapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        showRecyclerList();

        mainViewModel.getListMovie().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if(movies != null){
                    movieAdapter.setData(movies);
                    showLoading(false);
                }
            }
        });

    }

    private void showRecyclerList() {

        mainViewModel.setListMovie(TAG_SHOW);
        showLoading(true);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                showSelectedMovie(data);
            }
        });
    }

    private void showSelectedMovie(Movie movie) {
        Intent moveToDetail = new Intent(getContext(), DetailActivity.class);
        moveToDetail.putExtra(DetailActivity.EXTRA_MOVIE, movie.getIdMovie());
        moveToDetail.putExtra(DetailActivity.TAG_SHOW, "movie");
        startActivity(moveToDetail);
    }

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}