package BookMyShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BookMyShow.Enums.City;

public class TheatreController {
    public List<Theatre> allTheatres;
    public Map<City, List<Theatre>> cityVsTheatre;

    public TheatreController() {
        allTheatres = new ArrayList<>();
        cityVsTheatre = new HashMap<>();
    }

    // Add theatre by city
    public void addTheatre(City city, Theatre theatre) {
        allTheatres.add(theatre);

        List<Theatre> theatres = cityVsTheatre.getOrDefault(theatre, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }

    //
    public Map<Theatre, List<Show>> getAllShows(Movie movie, City city) {
        Map<Theatre, List<Show>> getShowsbyTheatre = new HashMap<>();
        List<Theatre> theatreList = cityVsTheatre.get(city);

        for (Theatre theatre : theatreList) {
            List<Show> showList = new ArrayList<>();
            List<Show> shows = theatre.getShows();

            for (Show show : shows) {
                if (movie.getMovieId() == show.movie.getMovieId()) {
                    showList.add(show);
                }
            }

            getShowsbyTheatre.put(theatre, shows);
        }

        return getShowsbyTheatre;
    }
}
