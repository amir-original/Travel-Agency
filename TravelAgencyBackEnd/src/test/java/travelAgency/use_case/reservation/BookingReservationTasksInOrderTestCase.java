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
import travelAgency.domain.reservation.ReservationMapper;
import travelAgency.domain.reservation.ReservationNumber;
import travelAgency.services.BookingReservation;
import travelAgency.services.reservation.ReservationNumberGenerator;
import travelAgency.services.flight.FlightAvailability;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;
import static travelAgency.use_case.fake.FakeReservationList.getReservation;

public class BookingReservationTasksInOrderTestCase {

    public static final String TICKET_NUMBER = "AA-7845-65874";
    private BookingReservation app;
    private FlightAvailability flightAvailability;
    private PassengerRepository passengers;
    private ReservationListRepository bookingLists;
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
    private ReservationListRepository reservations() {
        final ReservationListRepository bookings = mock(ReservationListRepository.class);
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
