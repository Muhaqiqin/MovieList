package com.dicoding.muhaqiqin.movielist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DetailViewModel extends ViewModel {
    private static final String API_KEY = "3072179f5e9a50665a806661081a509e";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    void setDetailMovie(final String show, final String idMovie){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/"+show+"/"+idMovie+"?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject movie = new JSONObject(result);
//                    JSONArray list = responseObject.getJSONArray("results");

//                    for(int i = 0; i < list.length(); i++){
//                        JSONObject movie = list.getJSONObject(i);
                    Movie listMovie = new Movie();
                    if(show.equals("tv")){
                        listMovie.setJudul(movie.getString("original_name"));
                        listMovie.setRelease(movie.getString("first_air_date"));
                    }else {
                        listMovie.setJudul(movie.getString("original_title"));
                        listMovie.setRelease(movie.getString("release_date"));
                    }
                    listMovie.setStudio("Language: "+movie.getString("original_language"));
                    listMovie.setRating(movie.getString("vote_average"));
                    listMovie.setDesc(movie.getString("overview"));
                    listMovie.setPhoto(movie.getString("poster_path"));
                    listMovie.setIdMovie(movie.getString("id"));

                    listItems.add(listMovie);
                    listMovies.postValue(listItems);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("Exception", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Movie>> getListMovie(){
        return listMovies;
    }

}
