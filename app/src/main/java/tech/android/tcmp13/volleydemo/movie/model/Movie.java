package tech.android.tcmp13.volleydemo.movie.model;

/**
 * Created by tcmp13-t on 11/9/2016.
 */
public class Movie {

    private String id;
    private String title;
    private int year;
    private String posterUrl;

    public Movie(String id, String title, int year, String posterUrl) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
    }

    public String getId() { return  id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPosterUrl() {

        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {

        this.posterUrl = posterUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return title.equals(movie.title);

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
