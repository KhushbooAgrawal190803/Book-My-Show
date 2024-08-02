package BookMyShow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import BookMyShow.Enums.City;
import BookMyShow.Enums.SeatCategory;

public class BookMyShow {
    MovieController movieController;
    TheatreController theatreController;

    BookMyShow() {
        movieController = new MovieController();
        theatreController = new TheatreController();
    }

    public static void main(String[] args) {
        BookMyShow bookMyShow = new BookMyShow();

        bookMyShow.initialize();

        bookMyShow.createBooking(City.BANGALROE, "Justice League");
        bookMyShow.createBooking(City.DELHI, "Avengers");
    }

    private void createBooking(City userCity, String movieName) {
        // 1- Search movie by location
        List<Movie> movies = movieController.getMoviesByCity(userCity);

        // 2- Check if user interested in Movie
        Movie interestedMovie = null;
        for (Movie movie : movies) {
            if (movie.getMovieName().equals(movieName)) {
                interestedMovie = movie;
            }
        }

        // 3- Check for shows in the city
        Map<Theatre, List<Show>> allShows = theatreController.getAllShows(interestedMovie, userCity);

        // 4- Check if interested in show
        Map.Entry<Theatre, List<Show>> entry = allShows.entrySet().iterator().next();
        List<Show> shows = entry.getValue();
        Show interestedShow = shows.get(0);

        // 5- Select seats
        int seatNumber = 45;

        // Uncomment below to check if Seat already taken
        // interestedShow.setBookedSeatIds(new ArrayList<>(List.of(45)));

        List<Integer> bookedSeats = interestedShow.getBookedSeatIds();
        if (!bookedSeats.contains(seatNumber)) {
            bookedSeats.add(seatNumber);

            Booking booking = new Booking();
            List<Seat> selectedSeats = new ArrayList<>();
            for (Seat seat : interestedShow.getScreen().getSeats()) {
                if (seat.getSeatId() == seatNumber) {
                    selectedSeats.add(seat);
                }
            }
            booking.setShow(interestedShow);
            booking.setSeats(selectedSeats);
        } else {
            System.out.println("Booking cannot be done, seat already taken");
            return;
        }

        System.out.println("Booking done");
        return;
    }

    private void initialize() {
        createMovie();
        createTheatre();
    }

    private void createMovie() {
        Movie avengers = new Movie();
        avengers.setMovieId(001);
        avengers.setMovieName("Avengers");
        avengers.setDuration(2);

        Movie justiceLeague = new Movie();
        justiceLeague.setMovieId(002);
        justiceLeague.setMovieName("Justice League");
        justiceLeague.setDuration(3);

        movieController.addMovie(avengers, City.BANGALROE);
        movieController.addMovie(avengers, City.DELHI);
        movieController.addMovie(justiceLeague, City.BANGALROE);
        movieController.addMovie(justiceLeague, City.DELHI);

    }

    private List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.SILVER);
            seats.add(seat);
        }
        for (int i = 40; i < 80; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.GOLD);
            seats.add(seat);
        }
        for (int i = 80; i < 120; i++) {
            Seat seat = new Seat();
            seat.setSeatId(i);
            seat.setSeatCategory(SeatCategory.PLATINUM);
            seats.add(seat);
        }

        return seats;
    }

    private Show createShow(int showId, Screen screen, Movie movie, int showTime) {
        Show show = new Show();
        show.setShowId(showId);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setShowTime(showTime);

        return show;
    }

    private List<Screen> createScreen() {
        List<Screen> screens = new ArrayList<>();
        Screen screen = new Screen();
        screen.setScreenId(7);
        screen.setSeats(createSeats());
        screens.add(screen);

        return screens;
    }

    private void createTheatre() {
        Movie avengers = movieController.getMovieByName("Avengers");
        Movie justiceLeague = movieController.getMovieByName("Justice League");

        Theatre inox = new Theatre();
        inox.setTheatreId(1);
        inox.setCity(City.BANGALROE);
        inox.setScreens(createScreen());
        List<Show> inoxShows = new ArrayList<>();
        Show show1 = createShow(19, inox.getScreens().get(0), avengers, 13);
        Show show2 = createShow(22, inox.getScreens().get(0), justiceLeague, 17);
        inoxShows.add(show1);
        inoxShows.add(show2);
        inox.setShows(inoxShows);

        Theatre pvr = new Theatre();
        pvr.setTheatreId(1);
        pvr.setCity(City.BANGALROE);
        pvr.setScreens(createScreen());
        List<Show> pvrShows = new ArrayList<>();
        Show show3 = createShow(07, pvr.getScreens().get(0), avengers, 9);
        Show show4 = createShow(22, pvr.getScreens().get(0), justiceLeague, 22);
        pvrShows.add(show3);
        pvrShows.add(show4);
        pvr.setShows(pvrShows);

        theatreController.addTheatre(City.BANGALROE, pvr);
        theatreController.addTheatre(City.DELHI, inox);
    }
}
