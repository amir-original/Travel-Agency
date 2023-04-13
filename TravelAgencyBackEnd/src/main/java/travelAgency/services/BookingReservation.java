package travelAgency.services;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.Reservation;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flights.FlightAvailability;

public class BookingReservation {

    private final BookingListRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final TicketNumberGenerator TicketNumberGenerator;

    public BookingReservation(BookingListRepository bookingLists,
                              FlightAvailability flightAvailability,
                              PassengerRepository passengers,
                              TicketNumberGenerator TicketNumberGenerator) {

        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.flightAvailability =  flightAvailability;
        this.TicketNumberGenerator = TicketNumberGenerator;
    }

    public Reservation book(BookingInformation bi) {
        flightAvailability.checkFlightPreBooking(bi);
        final String ticketNumber = TicketNumberGenerator.generateTicketNumber();

        final Reservation reservation = bi.getReservation(ticketNumber);

        passengers.save(bi.passenger());
        bookingLists.book(reservation);
        return reservation;
    }

}
