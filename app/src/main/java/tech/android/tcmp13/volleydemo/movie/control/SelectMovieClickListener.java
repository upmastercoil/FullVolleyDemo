package tech.android.tcmp13.volleydemo.movie.control;

import tech.android.tcmp13.volleydemo.movie.control.MovieAdapter;
import tech.android.tcmp13.volleydemo.movie.control.MovieClickListener;

/**
 * Created by tcmp13-t on 11/9/2016.
 */

public class SelectMovieClickListener implements MovieClickListener {

    private MovieAdapter movieAdapter;

    public SelectMovieClickListener(MovieAdapter movieAdapter) {

        this.movieAdapter = movieAdapter;
    }

    @Override
    public void onBookClicked(int position) {

        //Remove from the data
        movieAdapter.removeMovie(position);
        //Just Recreate everything
//            notifyDataSetChanged();
        movieAdapter.notifyItemRemoved(position);
        //Update the Layout manager positions
        movieAdapter.notifyItemRangeChanged(position, movieAdapter.dataSize());
    }
}
