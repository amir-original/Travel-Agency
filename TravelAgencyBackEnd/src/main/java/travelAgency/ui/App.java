package travelAgency.ui;

import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.flight.FlightRepositoryImpl;
import travelAgency.repository.passenger.PassengerRepositoryImpl;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.bookingList.TicketNumberGeneratorImpl;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.flights.FlightAvailabilityImpl;
import travelAgency.services.flights.FlightService;
import travelAgency.services.flights.FlightServiceImpl;

public class App {

    public App() {
        runHomePage();
    }

    public static void main(String[] args) {
        runHomePage();
    }

    private static void runHomePage() {
        CityService cityService = new CityServiceImpl();
        final MySQLDbConnection db = new MySQLDbConnection();
        final BookingListRepositoryImpl bookings = new BookingListRepositoryImpl(db);
        final FlightRepositoryImpl flightRepository = new FlightRepositoryImpl(db);
        final FlightAvailabilityImpl flightAvailability = new FlightAvailabilityImpl(flightRepository, bookings);
        final PassengerRepositoryImpl passengers = new PassengerRepositoryImpl(db);
        final TicketNumberGeneratorImpl ticketGenerator = new TicketNumberGeneratorImpl();

        BookingFlightTicket bookingFlightTicketInfo = new BookingFlightTicket(bookings, flightAvailability, passengers, ticketGenerator);
        BookingListService bookingListService = new BookingListServiceImpl(bookings);
        FlightService flightService = new FlightServiceImpl(flightRepository,flightAvailability);
        final HomePage homePage = new HomePage(cityService,bookingListService,flightService);
    }
}
