package BookMyShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BookMyShow.Enums.City;

public class MovieController {
    List<Movie> allMovies;
    Map<City, List<Movie>> cityVsMovie;

    MovieController() {
        allMovies = new ArrayList<>();
        cityVsMovie = new HashMap<>();
    }

    // add movies by city
    public void addMovie(Movie movie, City city) {
        allMovies.add(movie);

        List<Movie> movies = cityVsMovie.getOrDefault(city, new ArrayList<>());
        movies.add(movie);
        cityVsMovie.put(city, movies);
    }

    // Search movie by name
    public Movie getMovieByName(String movie) {
        for (Movie oneMovie : allMovies) {
            if (oneMovie.getMovieName().equals(movie)) {
                return oneMovie;
            }
        }

        return null;
    }

    // Get all movies by city
    public List<Movie> getMoviesByCity(City city) {
        return cityVsMovie.get(city);
    }
}
