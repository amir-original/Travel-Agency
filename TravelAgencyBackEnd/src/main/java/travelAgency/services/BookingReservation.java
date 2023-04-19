package travelAgency.services;

import org.apache.commons.lang.RandomStringUtils;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.reservation.BookingInformation;
import travelAgency.domain.reservation.Reservation;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flight.FlightAvailability;

// application service
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
        final String ticketNumber = TicketNumberGenerator.getTicketNumberFormat();
        //final String passengerId = RandomStringUtils.randomNumeric(5);

        final Reservation reservation = bi.getReservation(ticketNumber);

        final Passenger passenger = bi.passenger();

        passengers.save(passenger);
        bookingLists.book(reservation);
        return reservation;
    }

}
