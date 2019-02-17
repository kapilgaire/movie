package com.example.movieinfo.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieinfo.R;
import com.example.movieinfo.base.MyApplication;
import com.example.movieinfo.detail.MovieDetailFragment;
import com.example.movieinfo.detail.SelectedMovieViewModel;
import com.example.movieinfo.model.Movie;
import com.example.movieinfo.util.NetworkState;
import com.example.movieinfo.util.NetworkUtil;
import com.example.movieinfo.util.SimpleDividerItemDecoration;
import com.example.movieinfo.viewModel.ViewModelFactory;
import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieListFragment extends Fragment implements MovieSelectedListener {
    private Unbinder unbinder;
    private MovieListViewModel movieListViewModel;

    @BindView(R.id.movie_list_rv)
    RecyclerView movieListRv;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.error_msg_tv)
    TextView errorMsgTv;

    @BindView(R.id.error_layout_ll)
    LinearLayout errorLayout;

    @BindString(R.string.internet_connectivity)
    String internetConnectivity;

    @BindString(R.string.error_msg_timeout)
    String errorMsgTimeout;

    @BindString(R.string.error_msg_unknown)
    String errorMsgUnknown;

    @Inject
    ViewModelFactory viewModelFactory;

    private MovieListAdapter movieListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyApplication.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(movieListRv);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_anim_fall_down);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        movieListRv.setLayoutManager(mLayoutManager);
        movieListRv.setItemAnimator(new DefaultItemAnimator());
        movieListRv.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        movieListRv.setLayoutAnimation(animationController);

        movieListAdapter = new MovieListAdapter(getContext(), this);
        observeViewModel();
        return view;
    }

    @Override
    public void onMovieSelected(Movie movie) {
        SelectedMovieViewModel selectedMovieViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SelectedMovieViewModel.class);
        selectedMovieViewModel.setSelectedMovie(movie);

        getActivity().
                getSupportFragmentManager().
                beginTransaction().setCustomAnimations(R.anim.enter_animation,
                R.anim.stay, R.anim.stay, R.anim.exit_animation)
                .replace(R.id.screen_container, new MovieDetailFragment())
                .addToBackStack(null)
                .commit();
    }

    private void observeViewModel() {
        movieListViewModel.getMovieLiveData().observe(this, movies -> movieListAdapter.submitList(movies));

        movieListViewModel.getNetworkState().observe(this, networkState -> {
            movieListAdapter.setNetworkState(networkState);

        });

        movieListViewModel.getInitialLoadingState().observe(this, networkState -> {
            if (networkState != null) {
                if (networkState.getStatus() == NetworkState.Status.RUNNING) {
                    showShimmerLoader();
                } else if (networkState.getStatus() == NetworkState.Status.SUCCESS) {

                    hideShimmerLoader();
                    showLayout();

                } else if (networkState.getStatus() == NetworkState.Status.FAILED) {
                    hideShimmerLoader();
                    showErrorLayout();
                }
            }


        });
        movieListRv.setAdapter(movieListAdapter);
    }
//        movieListViewModel.getDataLoadError().observe(this, isError -> {
//            if (isError) {
//
//                showErrorLayout();
////                    Log.e(getClass().getSimpleName(), "Error");
//            } else {
////                    Log.e(getClass().getSimpleName(), "No Error");
//            }
//        });

//        movieListViewModel.getDataLoadError().observe(this, dataLoadError -> {
//            if (dataLoadError != null) {
//                if (dataLoadError.getError()) {
//                    showErrorLayout(dataLoadError.getThrowable());
//
//                }
//            }
//        });
//        movieListViewModel.getLoading().observe(this, isLoading -> {
//            if (isLoading) {
//
//                showShimmerLoader();
//
////                    Log.e(getClass().getSimpleName(), "Loading");
//            } else {
//                hideShimmerLoader();
//                showLayout();
////                    Log.e(getClass().getSimpleName(), "no Loading ");
//
//            }
//
//        });
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }


    private void showShimmerLoader() {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
    }

    private void hideShimmerLoader() {
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    private void showLayout() {
        movieListRv.setVisibility(View.VISIBLE);
    }

    private void showErrorLayout(/*Throwable throwable*/) {
        errorLayout.setVisibility(View.VISIBLE);
        if (!NetworkUtil.isConnected(getContext())) {
            errorMsgTv.setText(internetConnectivity);
        } else {
            errorMsgTv.setText(errorMsgUnknown);

        }
//        } else {
//            if (throwable instanceof TimeoutException) {
//                errorMsgTv.setText(errorMsgTimeout);
//            } else {
//                errorMsgTv.setText(errorMsgUnknown);
//            }
//        }

    }
}
