package travelAgency.ui;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.repository.passenger.PassengerRepositoryImpl;
import travelAgency.services.BookingReservation;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.bookingList.TicketNumberGeneratorImpl;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.flights.FlightAvailabilityImpl;
import travelAgency.services.flights.FlightService;
import travelAgency.services.flights.FlightServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;
import static travelAgency.domain.city.City.*;

public class App {

    private static BookingReservation bookingReservation;

    public App() {
        runHomePage();
    }

    public static void main(String[] args) {
        runHomePage();
    }

    private static void runHomePage() {
        CityService cityService = new CityServiceImpl();
        final MySQLDbConnection db = new MySQLDbConnection();
        final BookingListRepository bookings = new BookingListRepositoryImpl(db);
        final FlightRepository flights = new FlightRepositoryImpl(db);

      // insertFlights(flights);

        initBookingReservation(db, bookings, flights);

        BookingListService bookingListService = new BookingListServiceImpl(bookings);
        FlightService flightService = new FlightServiceImpl(flights,bookings);
        final HomePage homePage = new HomePage(cityService,bookingListService, flightService, bookingReservation);
    }

    private static void insertFlights(FlightRepository flights) {

        List<Flight> list = List.of(FlightBuilder.flight()
                .withFlightNumber("780")
                .withTotalCapacity(45)
                .withPrice(1000)
                .from(TEHRAN)
                .to(NAJAF)
                .departureAt(LocalDate.of(2023,4,18))
                .arrivalAt(LocalDate.of(2023,4,21)).build());

        flights.addFlights(list);
    }

    private static void initBookingReservation(MySQLDbConnection db, BookingListRepository bookings, FlightRepository flights) {
        PassengerRepository passengers = new PassengerRepositoryImpl(db);
        TicketNumberGenerator ticketNumberGenerator = new TicketNumberGeneratorImpl();
        final FlightAvailabilityImpl flightAvailability = new FlightAvailabilityImpl(flights, bookings);

        bookingReservation = new BookingReservation(bookings, flightAvailability,passengers,ticketNumberGenerator);
    }
}
