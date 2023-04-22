package travelAgency.ui;

import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.dao.database.db_config.mysq.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;
import travelAgency.services.BookingReservation;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.reservation.TicketNumberGeneratorImpl;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.flight.FlightAvailabilityImpl;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;

import static java.util.List.of;

public class App {

    private static BookingReservation bookingReservation;
    private static CityService cityService;
    private static ReservationListService reservationListService;
    private static FlightListService flightListService;

    public App() {
        setup();
        run();
    }

    public static void main(String[] args) {
        setup();
        run();
    }

    private static void run() {
        new HomePage(cityService, reservationListService, flightListService, bookingReservation);
    }

    private static void setup() {
        cityService = new CityServiceImpl();
        final MySQLDbConnection db = new MySQLDbConnection();
        final ReservationListRepository bookings = new ReservationListRepositoryImpl(db);
        final FlightRepository flights = new FlightRepositoryImpl(db);

        initBookingReservation(db, bookings, flights);

        flightListService = new FlightListServiceImpl(flights);
        reservationListService = new ReservationListServiceImpl(bookings, flightListService);
    }

    private static void initBookingReservation(MySQLDbConnection db, ReservationListRepository bookings, FlightRepository flights) {
        PassengerRepository passengers = new PassengerRepositoryImpl(db);
        TicketNumberGenerator ticketNumberGenerator = new TicketNumberGeneratorImpl();
        final FlightAvailabilityImpl flightAvailability =
                new FlightAvailabilityImpl(new FlightListServiceImpl(flights), new ReservationListServiceImpl(bookings,new FlightListServiceImpl(flights)));

        bookingReservation = new BookingReservation(bookings, flightAvailability,passengers,ticketNumberGenerator);
    }
}