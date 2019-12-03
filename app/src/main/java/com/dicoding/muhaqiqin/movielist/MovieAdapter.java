package com.dicoding.muhaqiqin.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

    private ArrayList<Movie> listMovie = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    private Context adapterContext = null;

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul;
        private TextView txtDesc;
        private ImageView imgPhoto;
        private TextView txtRelease;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.tv_judul);
            txtDesc = itemView.findViewById(R.id.tv_description);
            txtRelease = itemView.findViewById(R.id.tv_release);
            imgPhoto = itemView.findViewById(R.id.img_movie);
            adapterContext = itemView.getContext();
        }

        void bind(Movie movie){
            txtJudul.setText(movie.getJudul());
            txtRelease.setText(movie.getRelease());
            txtDesc.setText(movie.getDesc());

            Glide.with(adapterContext)
                    .load("https://image.tmdb.org/t/p/w185"+movie.getPhoto())
//                .apply(new RequestOptions().override(55,55))
                    .into(imgPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listMovie.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie data);
    }

}
