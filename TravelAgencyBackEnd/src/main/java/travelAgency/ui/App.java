package travelAgency.ui;

import org.jetbrains.annotations.NotNull;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.database.db_config.mysq.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.services.BookingReservation;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.reservation.TicketNumberGeneratorImpl;
import travelAgency.ui.pages.HomePage;

public class App {

    private  BookingReservation bookingReservation;
    private final CityService cityService;
    private  ReservationListService reservationListService;
    private  FlightListService flightListService;
    private final ExchangeRateDAOImpl exchangeRateDAO;

    public App() {
        final MySQLDbConnection db = new MySQLDbConnection();
        initBookingReservation(db);

        this.exchangeRateDAO = new ExchangeRateDAOImpl();
        this.cityService = new CityServiceImpl();
    }

    private void initBookingReservation(MySQLDbConnection db) {
        final ReservationListRepository bookings = createReservationListService(db);

        PassengerRepository passengers = new PassengerRepositoryImpl(db);

        TicketNumberGenerator ticketNumberGenerator = new TicketNumberGeneratorImpl();

        final FlightAvailability flightAvailability = new FlightAvailability(reservationListService);

        bookingReservation = new BookingReservation(bookings, flightAvailability, passengers, ticketNumberGenerator);
    }

    @NotNull
    private ReservationListRepository createReservationListService(MySQLDbConnection db) {
        final FlightRepository flights = new FlightRepositoryImpl(db);
        flightListService = new FlightListServiceImpl(flights);
        ReservationListRepository bookings = new ReservationListRepositoryImpl(db);
        reservationListService = new ReservationListServiceImpl(bookings, flightListService);
        return bookings;
    }


    public static void main(String[] args) {
        final App app = new App();
        app.run();
    }

    public void run() {
        buildHomePage();
    }

    private void buildHomePage() {
        new HomePage(reservationListService, flightListService, bookingReservation, exchangeRateDAO, cityService);
    }

}
