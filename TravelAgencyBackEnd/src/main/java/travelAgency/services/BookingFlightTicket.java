package travelAgency.services;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.bookingList.TicketGenerator;
import travelAgency.services.flights.FlightAvailability;

public class BookingFlightTicket {

    private final BookingListRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final TicketGenerator ticketGenerator;

    public BookingFlightTicket(BookingListRepository bookingLists,
                               FlightAvailability flightAvailability,
                               PassengerRepository passengers,
                               TicketGenerator ticketGenerator) {
        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.flightAvailability =  flightAvailability;
        this.ticketGenerator = ticketGenerator;
    }

    public FlightTicket book(BookingInformation bi) {
        checkBookingInformation(bi);

        flightAvailability.checkFlight(bi);
        final FlightTicket flightTicket = ticketGenerator.getFlightTicket(bi);

        passengers.save(bi.passenger());
        bookingLists.book(flightTicket);
        return flightTicket;
    }

    private void checkBookingInformation(BookingInformation bookingInformation) {
        bookingInformation.check();
    }

}
