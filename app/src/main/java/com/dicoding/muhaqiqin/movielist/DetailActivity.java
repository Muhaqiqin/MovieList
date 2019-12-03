package com.dicoding.muhaqiqin.movielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView tvJudul, tvDesc, tvRelease, tvStudio, tvRating;
    private RatingBar rtRating;
    private ImageView imgPhoto;
    private ProgressBar progressBar;

    private DetailViewModel detailViewModel;


    private ArrayList<Movie> listMovie = new ArrayList<>();

    private String judul, desc, release, studio, rating, photo, movieID, tag_show;

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String TAG_SHOW = "tag_show";

    private static final String API_KEY = "3072179f5e9a50665a806661081a509e";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvJudul = findViewById(R.id.tv_judul);
        tvDesc = findViewById(R.id.tv_description);
        tvRelease = findViewById(R.id.tv_release);
        tvStudio = findViewById(R.id.tv_studio);
        tvRating = findViewById(R.id.tv_rating);
        imgPhoto = findViewById(R.id.img_photo);
        progressBar = findViewById(R.id.progressBar);
        rtRating = findViewById(R.id.rt_rating);

        movieID = getIntent().getStringExtra(EXTRA_MOVIE);
        tag_show = getIntent().getStringExtra(TAG_SHOW);

        detailViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);

        getData();

        detailViewModel.getListMovie().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if(movies != null){
                    listMovie.clear();
                    listMovie.addAll(movies);
                    setData(listMovie.get(0));
                    showLoading(false);
                }
            }
        });

    }

    private void getData(){
        showLoading(true);

        detailViewModel.setDetailMovie(tag_show, movieID);
    }

    private void setData(Movie movie) {
        tvJudul.setText(movie.getJudul());
        tvDesc.setText(movie.getDesc());
        tvRelease.setText(movie.getRelease());
        tvStudio.setText(movie.getStudio());
        tvRating.setText(movie.getRating());

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185"+movie.getPhoto())
                .into(imgPhoto);
    }

    private void showLoading(Boolean state){
        if(state){
            progressBar.setVisibility(View.VISIBLE);
            rtRating.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            rtRating.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_fav) {

        }
        return super.onOptionsItemSelected(item);
    }

}