package tech.android.tcmp13.volleydemo.movie.control;

import android.content.Context;
import android.util.Log;
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

import tech.android.tcmp13.volleydemo.movie.control.MovieAdapter;
import tech.android.tcmp13.volleydemo.movie.control.MovieClickListener;
import tech.android.tcmp13.volleydemo.movie.model.Movie;
import tech.android.tcmp13.volleydemo.networking.OmdbApiConstants;
import tech.android.tcmp13.volleydemo.networking.VolleyManager;

/**
 * Created by tcmp13-t on 11/9/2016.
 */

public class SelectMovieClickListener implements MovieClickListener {

    private Context context;
    private MovieAdapter adapter;

    public SelectMovieClickListener(Context context, MovieAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onMovieClicked(Movie movie) {
        getMovie(movie.getId());
    }

    public void getMovie(String id) {

        String url = OmdbApiConstants.BASE_URL + OmdbApiConstants.SEARCH_BY_IMDB_ID + id;


        final StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseString) {

                        //Try to create the JSON
                        JSONObject responseJson;
                        try {
                            responseJson = new JSONObject(responseString);
                        } catch (JSONException e) {
                            Toast.makeText(context, "Couldn't Understand what the server was saying, please call the RESTful API again.", Toast.LENGTH_SHORT).show();
                            updateUiForBadResponse();
                            return;
                        }

                        String id = responseJson.optString(OmdbApiConstants.MOVIE_ID_KEY);
                        if(id.isEmpty() || id == "" || id == null)
                        {
                            updateUiForBadResponse();
                            Toast.makeText(context, "Couldn't Understand what the server was saying, please call the RESTful API again.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String title = responseJson.optString(OmdbApiConstants.MOVIE_TITLE_KEY);
                        String yearDirty = responseJson.optString(OmdbApiConstants.MOVIE_YEAR_KEY);
                        String posterUrl = responseJson.optString(OmdbApiConstants.MOVIE_POSTER_URL_KEY);

                        String nonDigit = "\\D+";
                        String year = yearDirty.replaceAll(nonDigit, "");
                        Movie movie = new Movie(id, title, Integer.parseInt(year), posterUrl);

                        //Clear the adapter
                        adapter.clear();

                        adapter.add(movie);
                    }

                    private void updateUiForBadResponse() {
                        adapter.clear();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag(OmdbApiConstants.MOVIE_REQUEST_TAG);
        VolleyManager.getInstance(context).addRequest(context, request);
    }

}
