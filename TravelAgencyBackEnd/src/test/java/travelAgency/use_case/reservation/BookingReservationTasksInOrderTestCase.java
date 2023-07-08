package travelAgency.use_case.reservation;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.application.dto.ReservationMapper;
import travelAgency.model.reservation.ReservationNumber;
import travelAgency.application.reservation.BookingReservation;
import travelAgency.application.reservation.ReservationNumberGenerator;
import travelAgency.application.flight.FlightAvailability;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;
import static travelAgency.use_case.fake.FakeReservation.getReservation;

public class BookingReservationTasksInOrderTestCase {

    public static final String TICKET_NUMBER = "AA-7845-65874";
    private BookingReservation app;
    private FlightAvailability flightAvailability;
    private PassengerRepository passengers;
    private ReservationRepository bookingLists;
    private ReservationNumberGenerator reservationNumber;
    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        passengers = createPassengerRepository();
        bookingLists = reservations();
        flightAvailability = createFindFlightsRepository();
        reservationNumber = createMockTicketGenerator();
        app = new BookingReservation(bookingLists, passengers, flightAvailability, reservationNumber);
        reservationMapper = new ReservationMapper();
    }

    @Test
    void should_be_do_actions_in_order_when_booking_a_flight() {
        final ReservationInformation reservationInfo = reservationInformation().build();

        final Reservation fetchedReservation = app.book(reservationInfo);
        assertThat(fetchedReservation.flight().flightNumber()).isEqualTo(reservationInfo.getFlightNumber());


        final InOrder inOrder = inOrder(flightAvailability, passengers, bookingLists, reservationNumber);

        final ReservationNumber reservationNumber = this.reservationNumber.generateReservationNumber();
        final Reservation reservation = reservationMapper.toEntity(reservationInfo, reservationNumber);

        final Passenger passenger = reservation.passenger();

        inOrder.verify(flightAvailability).ensureCanBooking(reservation);
        inOrder.verify(passengers).save(passenger);
        inOrder.verify(bookingLists).book(reservation);
    }

    @NotNull
    private Reservation createReservation() {
        return getReservation(TICKET_NUMBER);
    }

    @NotNull
    private ReservationRepository reservations() {
        final ReservationRepository bookings = mock(ReservationRepository.class);
        final Reservation reservation = createReservation();
        doNothing().when(bookings).book(reservation);
        return bookings;
    }

    private PassengerRepository createPassengerRepository() {
        final PassengerRepository mock = mock(PassengerRepository.class);
        doNothing().when(mock).save(any(Passenger.class));
        return mock;
    }

    private FlightAvailability createFindFlightsRepository() {
        final FlightAvailability mock = mock(FlightAvailability.class);
        doNothing().when(mock).ensureCanBooking(any());
        return mock;
    }

    @NotNull
    private ReservationNumberGenerator createMockTicketGenerator() {
        ReservationNumberGenerator reservationNumber = mock(ReservationNumberGenerator.class);
        when(reservationNumber.generateReservationNumber())
                .thenReturn(ReservationNumber.ofString("AA-7845-65874"));
        return reservationNumber;
    }

}
