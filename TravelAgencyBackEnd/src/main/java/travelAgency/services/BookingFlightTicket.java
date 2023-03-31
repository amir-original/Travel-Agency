package travelAgency.services;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.flights.FlightAvailabilityImpl;

public class BookingFlightTicket {

    private final BookingListService bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailabilityImpl flightAvailability;

    public BookingFlightTicket(BookingListService bookingLists,
                               FlightAvailabilityImpl flightAvailability,
                               PassengerRepository passengers) {
        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.flightAvailability = flightAvailability;
    }

    public FlightTicket book(BookingInformation bookingInfo) {
        check(bookingInfo);
        final String flightNumber = bookingInfo.flightNumber();

        flightAvailability.checkingFlight(flightNumber, bookingInfo.numberOfTickets());

        passengers.save(bookingInfo.passenger());
        return bookingLists.book(bookingInfo);
    }

    private void check(BookingInformation bookingInformation) {
        bookingInformation.check();
    }


}
