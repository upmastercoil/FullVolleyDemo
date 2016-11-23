package tech.android.tcmp13.volleydemo.movie.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import tech.android.tcmp13.volleydemo.R;

/**
 * Created by tcmp13-t on 11/9/2016.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView posterImageView;
    public TextView titleTextView;
    public TextView yearTextView;

    /**
     * Creates a new item view holder.
     *
     * @param itemView The view that each item in the list holds (hint: findViewById will be called on this view)
     */
    public MovieViewHolder(View itemView) {

        super(itemView);
        posterImageView = (NetworkImageView) itemView.findViewById(R.id.movieItemPoster);
        titleTextView = (TextView) itemView.findViewById(R.id.movieItemTitleTextView);
        yearTextView = (TextView) itemView.findViewById(R.id.movieItemYearTextView);
    }
}
