package travelAgency.use_case.reservation;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.passenger.Passenger;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.services.BookingReservation;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.flight.FlightAvailabilityImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.use_case.fake.FakeReservationInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakeReservationList.flightTicket;

public class MockBookingFlightShould {

    public static final String TICKET_NUMBER = "78456587";
    private BookingReservation app;
    private FlightAvailabilityImpl flightAvailability;
    private PassengerRepository passengers;
    private ReservationListRepository bookingLists;
    private TicketNumberGenerator ticketNumberGenerator;

    @BeforeEach
    void setUp() {
        passengers = createPassengerRepository();
        bookingLists = createTicketService();
        flightAvailability = createFindFlightsRepository();
        ticketNumberGenerator = createMockTicketGenerator();
        app = new BookingReservation(bookingLists, flightAvailability, passengers, ticketNumberGenerator);
    }

    @Test
    void be_do_actions_in_order_when_booking_a_flight() {
        final ReservationInformation reservationInformation = bookingInformation().build();

        final Reservation ticket = app.book(reservationInformation);
        assertThat(ticket.reservationInformation()).isEqualTo(reservationInformation);


        final InOrder inOrder = inOrder(flightAvailability, passengers,
                bookingLists, ticketNumberGenerator);

        inOrder.verify(flightAvailability).canBooking(reservationInformation);

        final Reservation reservation = reservationInformation.getReservation(ticketNumberGenerator.getTicketNumberFormat());

        inOrder.verify(passengers).save(reservationInformation.passenger());
        inOrder.verify(bookingLists).book(reservation);
    }

    @NotNull
    private Reservation createTicket() {
        return flightTicket(TICKET_NUMBER);
    }

    @NotNull
    private ReservationListRepository createTicketService() {
        final ReservationListRepository bookings = mock(ReservationListRepository.class);
        final Reservation reservation = createTicket();
        doNothing().when(bookings).book(reservation);
        return bookings;
    }

    private PassengerRepository createPassengerRepository() {
        final PassengerRepository mock = mock(PassengerRepository.class);
        doNothing().when(mock).save(any(Passenger.class));
        return mock;
    }

    private FlightAvailabilityImpl createFindFlightsRepository() {
        final FlightAvailabilityImpl mock = mock(FlightAvailabilityImpl.class);
        doNothing().when(mock).canBooking(any());
        return mock;
    }

    @NotNull
    private TicketNumberGenerator createMockTicketGenerator() {
        TicketNumberGenerator ticketNumberGenerator = mock(TicketNumberGenerator.class);
        when(ticketNumberGenerator.getTicketNumberFormat()).thenReturn("AA-7845-65874");
        return ticketNumberGenerator;
    }

}
