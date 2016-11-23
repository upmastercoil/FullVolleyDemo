package tech.android.tcmp13.volleydemo.networking;

/**
 * Created by tcmp13-t on 11/20/2016.
 */
public interface OmdbApiConstants {

    String MOVIE_REQUEST_TAG = "movieRequest";

    String BASE_URL = "http://www.omdbapi.com/?";
    String SEARCH_BY_TITLE = "s=";
    String SEARCH_BY_IMDB_ID = "i=";

    String MOVIE_ID_KEY = "imdbID";
    String MOVIE_TITLE_KEY = "Title";
    String MOVIE_YEAR_KEY = "Year";
    String MOVIE_POSTER_URL_KEY = "Poster";
    String MOVIES_LIST_KEY = "Search";
}
