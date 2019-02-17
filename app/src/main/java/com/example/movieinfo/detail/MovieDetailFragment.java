package com.example.movieinfo.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieinfo.R;
import com.example.movieinfo.base.MyApplication;
import com.example.movieinfo.viewModel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieDetailFragment extends Fragment {
    @BindView(R.id.movie_cover_art_iv_movie_detail)
    ImageView movieCoverartIv;
    @BindView(R.id.movie_genre_tv_movie_detail)
    TextView movieGenreTv;
    @BindView(R.id.movie_name_tv_movie_detail)
    TextView movieNameTv;
    @BindView(R.id.movie_rating_tv_movie_detail)
    TextView movieRatingTv;
    @BindView(R.id.movie_year_tv_movie_detail)
    TextView movieYearTv;
    @BindView(R.id.movie_summary_tv_movie_detail)
    TextView movieSummaryTv;
    private Unbinder unbinder;

    private SelectedMovieViewModel selectedMovieViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        selectedMovieViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedMovieViewModel.class);

        displayMovieDetail();

    }

    private void displayMovieDetail() {

        selectedMovieViewModel.getSelectedMovie().observe(this, movie -> {
            StringBuilder genre = new StringBuilder();

            if (movie != null) {
                for (int i = 0; i < movie.getGenre().size(); i++) {
                    genre.append(i);
                }
                movieGenreTv.setText(genre+"");
                movieNameTv.setText(movie.getTitle_long()+"");
                movieRatingTv.setText(movie.getRating().toString()+"");
                movieYearTv.setText(movie.getYear()+"");
                movieSummaryTv.setText(movie.getSummary()+"");
                Picasso.with(getContext()).
                        load(movie.getLarge_cover_image())
                        .into(movieCoverartIv);


            }

        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }


}
