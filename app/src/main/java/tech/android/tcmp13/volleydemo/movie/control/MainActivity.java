package tech.android.tcmp13.volleydemo.movie.control;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tech.android.tcmp13.volleydemo.movie.model.Movie;
import tech.android.tcmp13.volleydemo.R;
import tech.android.tcmp13.volleydemo.networking.OmdbApiConstants;
import tech.android.tcmp13.volleydemo.networking.VolleyManager;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_NUMBER_OF_COLS = 2;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupUi();
    }

    private void setupUi() {

        setContentView(R.layout.activity_main);
        setupMoviesRecyclerView();
        setupSearchBar();
    }

    private void setupMoviesRecyclerView() {

        //Find and define the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.moviesRecyclerView);

        //Define the Layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(this, DEFAULT_NUMBER_OF_COLS));

        //Define the adapter
        adapter = new MovieAdapter(this);
        recyclerView.setAdapter(adapter);

        //Set the click listener
        SelectMovieClickListener selectMovieClickListener = new SelectMovieClickListener(this, adapter);
        adapter.setMovieClickListener(selectMovieClickListener);
    }

    private void setupSearchBar() {

        SearchView inputMovieSearchView = (SearchView) findViewById(R.id.inputMovieSearchView);
        inputMovieSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() <= 1)
                    return false;
                VolleyManager.getInstance(MainActivity.this).cancelAll(MainActivity.this, OmdbApiConstants.MOVIE_REQUEST_TAG);
                getMovies(newText);
                return true;
            }
        });
    }

    public void getMovies(String title) {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        String url = OmdbApiConstants.BASE_URL + OmdbApiConstants.SEARCH_BY_TITLE + title;
        final StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseString) {

                        progressBar.setVisibility(View.GONE);
                        //Try to create the JSON
                        JSONObject responseJson;
                        try {
                            responseJson = new JSONObject(responseString);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Couldn't Understand what the server was saying, please call the RESTful API again.", Toast.LENGTH_SHORT).show();
                            updateUiForBadResponse();
                            return;
                        }

                        //First step parse the result into movies JSON array
                        JSONArray moviesArray = responseJson.optJSONArray(OmdbApiConstants.MOVIES_LIST_KEY);
                        if (moviesArray == null) {
                            updateUiForBadResponse();
                            return;
                        }

                        //Array was found, turn it into movies array
                        List<Movie> movies = new ArrayList<>(moviesArray.length());
                        for (int i = 0; i < moviesArray.length(); i++) {

                            try {
                                JSONObject movieJson = moviesArray.getJSONObject(i);
                                //Try find values for keys
                                String id = movieJson.optString(OmdbApiConstants.MOVIE_ID_KEY);
                                String title = movieJson.optString(OmdbApiConstants.MOVIE_TITLE_KEY);
                                String yearDirty = movieJson.optString(OmdbApiConstants.MOVIE_YEAR_KEY);
                                String posterUrl = movieJson.optString(OmdbApiConstants.MOVIE_POSTER_URL_KEY);

                                if (title == null || yearDirty == null) {
                                    continue;
                                }

                                //Create new object and update UI.
                                String nonDigit = "\\D+";
                                String year = yearDirty.replaceAll(nonDigit, "");
                                Movie movie = new Movie(id, title, Integer.parseInt(year), posterUrl);
                                movies.add(movie);
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, "Couldn't Understand what the server was saying, please call the RESTful API again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //Clear the adapter
                        adapter.clear();

                        //Add the new movies
                        progressBar.setVisibility(View.GONE);
                        adapter.addAll(movies);
                    }

                    private void updateUiForBadResponse() {
                        progressBar.setVisibility(View.GONE);
                        adapter.clear();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "404", Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag(OmdbApiConstants.MOVIE_REQUEST_TAG);
        VolleyManager.getInstance(this).addRequest(this, request);
    }
}
