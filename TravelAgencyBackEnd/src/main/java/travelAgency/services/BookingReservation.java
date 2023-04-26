package travelAgency.services;

import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.reservation.Reservation;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.flight.FlightAvailability;

// application service
public class BookingReservation {

    private final ReservationListRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final TicketNumberGenerator TicketNumberGenerator;

    public BookingReservation(ReservationListRepository bookingLists,
                              FlightAvailability flightAvailability,
                              PassengerRepository passengers,
                              TicketNumberGenerator TicketNumberGenerator) {

        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.flightAvailability =  flightAvailability;
        this.TicketNumberGenerator = TicketNumberGenerator;
    }

    public Reservation book(ReservationInformation resInfo) {
        flightAvailability.canBooking(resInfo);
        final String ticketNumber = TicketNumberGenerator.getTicketNumberFormat();

        final Reservation reservation = resInfo.getReservation(ticketNumber);
        final Passenger passenger = reservation.passenger();

        passengers.save(passenger);
        bookingLists.book(reservation);
        return reservation;
    }

}
