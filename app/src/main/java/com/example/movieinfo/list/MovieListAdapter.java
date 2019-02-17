package com.example.movieinfo.list;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movieinfo.R;
import com.example.movieinfo.model.Movie;
import com.example.movieinfo.util.NetworkState;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends PagedListAdapter<Movie, RecyclerView.ViewHolder> {


    private Context context;
    private final MovieSelectedListener movieSelectedListener;
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private NetworkState networkState;

//    MovieListAdapter(MovieListViewModel movieListViewModel, LifecycleOwner lifecycleOwner, Context context, MovieSelectedListener movieSelectedListener) {
//
//        this.movieSelectedListener = movieSelectedListener;
//        movieListViewModel.getMovieData().observe(lifecycleOwner, movieResponse -> {
//            movieList.clear();
//            if (movieResponse != null) {
//                movieList.addAll(movieResponse.getMovieData().getMovieList());
//                notifyDataSetChanged();//TODO Use DiffUtill
//            }
//        });
//
//        this.context = context;
//
//        setHasStableIds(true);
//    }


    public MovieListAdapter(Context context, MovieSelectedListener movieSelectedListener) {
        super(Movie.DIFF_CALLBACK);
        this.context = context;
        this.movieSelectedListener = movieSelectedListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item_layout, viewGroup, false);
//        return new MovieDataHolder(view, movieSelectedListener);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROGRESS) {
            View view = layoutInflater.inflate(R.layout.item_progress, parent, false);
            return new LoadingVH(view);
        } else {
            View view = layoutInflater.inflate(R.layout.movie_item_layout, parent, false);
            return new MovieDataHolder(view, movieSelectedListener);

        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull MovieDataHolder movieDataHolder, int position) {
//        movieDataHolder.bind(movieList.get(position));
//
//        Picasso.with(context).load(movieList.get(position).getLarge_cover_image()).into(movieDataHolder.movieCoverartIv);
//
//    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof MovieDataHolder) {
            ((MovieDataHolder) viewHolder).bind(getItem(position));

        } else {
            ((LoadingVH) viewHolder).bindView(networkState);
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
//
//    @Override
//    public int getItemCount() {
//        return movieList.size();
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return movieList.get(position).getId();
//    }

    final class MovieDataHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.movie_cover_art_iv_movie_layout)
        ImageView movieCoverartIv;
        @BindView(R.id.movie_genre_tv_movie_layout)
        TextView movieGenreTv;
        @BindView(R.id.movie_name_tv_movie_layout)
        TextView movieNameTv;
        @BindView(R.id.movie_rating_tv_movie_layout)
        TextView movieRatingTv;
        @BindView(R.id.movie_year_tv_movie_layout)
        TextView movieYearTv;
//        @BindView(R.id.movie_summary_tv_movie_layout)
//        TextView movieSummaryTv;

        private Movie movie;

        public MovieDataHolder(View itemView, MovieSelectedListener movieSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (movie != null) {
                    movieSelectedListener.onMovieSelected(movie);
                }
            });

        }

        void bind(Movie movie) {
            this.movie = movie;
            movieYearTv.setText(movie.getYear() + "");
            movieNameTv.setText(movie.getTitle_long() + "");

            StringBuilder genre = new StringBuilder();
            for (int i = 0; i < movie.getGenre().size(); i++) {
                genre.append(i);
            }
            movieGenreTv.setText(genre.toString());
//            movieSummaryTv.setText(movie.getSummary()+"");
            movieRatingTv.setText(movie.getRating().toString() + "");

            Picasso.with(context).load(movie.getLarge_cover_image()).into(movieCoverartIv);
        }

    }

    final class LoadingVH extends RecyclerView.ViewHolder {
        @BindView(R.id.loadmore_progress)
         ProgressBar mProgressBar;

        public LoadingVH(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }

//            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
//                binding.errorMsg.setVisibility(View.VISIBLE);
//                binding.errorMsg.setText(networkState.getMsg());
//            } else {
//                binding.errorMsg.setVisibility(View.GONE);
//            }
        }
    }


}
