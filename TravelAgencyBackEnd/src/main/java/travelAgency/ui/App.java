package travelAgency.ui;

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
import travelAgency.services.flight.FlightAvailabilityImpl;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.reservation.TicketNumberGeneratorImpl;

public class App {

    private static BookingReservation bookingReservation;
    private static CityService cityService;
    private static ReservationListService reservationListService;
    private static FlightListService flightListService;
    private final ExchangeRateDAOImpl exchangeRateDAO;

    public App() {
        exchangeRateDAO = new ExchangeRateDAOImpl();
        cityService = new CityServiceImpl();
        final MySQLDbConnection db = new MySQLDbConnection();
        final ReservationListRepository bookings = new ReservationListRepositoryImpl(db);
        final FlightRepository flights = new FlightRepositoryImpl(db);
        initBookingReservation(db, bookings, flights);
        flightListService = new FlightListServiceImpl(flights);
        reservationListService = new ReservationListServiceImpl(bookings, flightListService);
    }


    public static void main(String[] args) {
        final App app = new App();
        app.run();
    }

    public void run() {
        buildHomePage();
    }

    private  void buildHomePage() {
        new HomePage(reservationListService, flightListService, bookingReservation, exchangeRateDAO, cityService);
    }

    private static void initBookingReservation(MySQLDbConnection db, ReservationListRepository bookings, FlightRepository flights) {
        PassengerRepository passengers = new PassengerRepositoryImpl(db);
        TicketNumberGenerator ticketNumberGenerator = new TicketNumberGeneratorImpl();
        final FlightAvailabilityImpl flightAvailability =
                new FlightAvailabilityImpl(new FlightListServiceImpl(flights), new ReservationListServiceImpl(bookings,new FlightListServiceImpl(flights)));

        bookingReservation = new BookingReservation(bookings, flightAvailability,passengers,ticketNumberGenerator);
    }
}
