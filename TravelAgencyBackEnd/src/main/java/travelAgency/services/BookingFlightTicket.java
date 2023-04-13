package travelAgency.services;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flights.FlightAvailability;

public class BookingFlightTicket {

    private final BookingListRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final TicketNumberGenerator TicketNumberGenerator;

    public BookingFlightTicket(BookingListRepository bookingLists,
                               FlightAvailability flightAvailability,
                               PassengerRepository passengers,
                               TicketNumberGenerator TicketNumberGenerator) {

        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.flightAvailability =  flightAvailability;
        this.TicketNumberGenerator = TicketNumberGenerator;
    }

    public FlightTicket book(BookingInformation bi) {
        flightAvailability.checkFlight(bi);
        final String ticketNumber = TicketNumberGenerator.generateTicketNumber();

        final FlightTicket flightTicket = bi.getFlightTicket(ticketNumber);

        passengers.save(bi.passenger());
        bookingLists.book(flightTicket);
        return flightTicket;
    }

}
