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
import travelAgency.services.flight.FlightService;
import travelAgency.services.flight.FlightServiceImpl;

import static java.util.List.of;

public class App {

    private static BookingReservation bookingReservation;
    private static CityService cityService;
    private static ReservationListService reservationListService;
    private static FlightService flightService;

    public App() {
        setup();
        run();
    }

    public static void main(String[] args) {
        setup();
        run();
    }

    private static void run() {
        new HomePage(cityService, reservationListService, flightService, bookingReservation);
    }

    private static void setup() {
        cityService = new CityServiceImpl();
        final MySQLDbConnection db = new MySQLDbConnection();
        final ReservationListRepository bookings = new ReservationListRepositoryImpl(db);
        final FlightRepository flights = new FlightRepositoryImpl(db);

        initBookingReservation(db, bookings, flights);

        flightService = new FlightServiceImpl(flights);
        reservationListService = new ReservationListServiceImpl(bookings, flightService);
    }

    private static void initBookingReservation(MySQLDbConnection db, ReservationListRepository bookings, FlightRepository flights) {
        PassengerRepository passengers = new PassengerRepositoryImpl(db);
        TicketNumberGenerator ticketNumberGenerator = new TicketNumberGeneratorImpl();
        final FlightAvailabilityImpl flightAvailability =
                new FlightAvailabilityImpl(new FlightServiceImpl(flights), new ReservationListServiceImpl(bookings,new FlightServiceImpl(flights)));

        bookingReservation = new BookingReservation(bookings, flightAvailability,passengers,ticketNumberGenerator);
    }
}
