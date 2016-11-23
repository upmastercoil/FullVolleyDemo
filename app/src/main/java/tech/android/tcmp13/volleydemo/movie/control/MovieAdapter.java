package tech.android.tcmp13.volleydemo.movie.control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tech.android.tcmp13.volleydemo.movie.view.MovieViewHolder;
import tech.android.tcmp13.volleydemo.R;
import tech.android.tcmp13.volleydemo.movie.model.Movie;
import tech.android.tcmp13.volleydemo.networking.VolleyManager;

/**
 * Created by tcmp13-t on 11/9/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final Context context;
    private List<Movie> movies;
    private MovieClickListener movieClickListener;

    public MovieAdapter(Context context) {

        this.movies = new ArrayList<>();
        this.context = context;
        movieClickListener = null;
    }

    public void setMovieClickListener(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    /**
     * Will be called on a new (not recycled) view holder should be created. NO MORE getView!!!
     *
     * @param parent   The recycler view
     * @param viewType Used to mark different kinds of view holders.
     * @return The desired ViewHolder
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    /**
     * Called every time a new item should appear.
     *
     * @param holder   The ViewHolder that holds the references
     * @param position The position in the layout (not data).
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        // In anonymous class variables must be final. to not change the method's signature we create a final copy of the position and call it
        final int pos = position;
        final Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(String.valueOf(movie.getYear()));
        holder.posterImageView.setImageUrl(movie.getPosterUrl(), VolleyManager.getInstance(context).getImageLoader());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //Since we didn't initialize the listener at construction, a null check is required.
                if (movieClickListener != null)
                    movieClickListener.onMovieClicked(movie);
            }
        });
    }

    /**
     * Remove a movie
     *
     * @param position the position of the item to remove from the data.
     */
    public void removeMovie(int position) {

        movies.remove(position);
    }

    /**
     * Add a movie at position 0
     *
     * @param movie The new movie to add at position 0
     */
    public void insertMovie(Movie movie) {

        movies.add(0, movie);
    }

    /**
     * DON'T EXPOSE YOUR MEMBER VARIABLES!!! Instead provide a method that exposes the functionality.
     * <p>
     * get the current size of the data
     *
     * @return the current size of the data
     */
    public int dataSize() {

        return movies.size();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void clear() {

        movies.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Movie> movies) {

        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void add(Movie movie){
        movies.clear();
        insertMovie(movie);
        notifyDataSetChanged();
    }
}
