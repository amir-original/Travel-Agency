package travelAgency.use_case;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.application.dto.ReservationResponse;
import travelAgency.application.use_case.*;
import travelAgency.application.dto.ReservationInformation;
import travelAgency.infrastructure.mapper.PassengerMapper;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.infrastructure.mapper.ReservationMapper;
import travelAgency.model.reservation.ReservationNumber;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;
import static travelAgency.use_case.fake.FakeReservation.getReservation;

public class BookingReservationPriorityTestCase {

    public static final String RESERVATION_NUMBER = "AA-7845-65874";
    private BookingReservation app;
    private SearchReservationService findReservation;
    private PassengerRepository passengers;
    private ReservationRepository bookingLists;
    private ReservationNumberGenerator reservationNumberGenerator;
    private ReservationMapper reservationMapper;
    private PassengerMapper passengerMapper;

    @BeforeEach
    void setUp() {
        passengers = createPassengerRepository();
        bookingLists = reservations();
        findReservation = createFindReservationService();
        reservationNumberGenerator = createMockReservationNumberGenerator();
        app = new BookingReservation(bookingLists, passengers,findReservation, reservationNumberGenerator);
        reservationMapper = new ReservationMapper();
        passengerMapper = new PassengerMapper();
    }

    @Test
    void should_be_perform_tasks_in_order_when_booking_a_flight() {
        final ReservationInformation resInfo = reservationInformation().build();

        final ReservationResponse fetchedReservation = app.book(resInfo);
        assertThat(fetchedReservation.flightNumber()).isEqualTo(resInfo.getFlightNumber());


        final InOrder inOrder = inOrder(findReservation, passengers, bookingLists, reservationNumberGenerator);

        final ReservationNumber reservationNumber = this.reservationNumberGenerator.generateReservationNumber();
        int availableSeats = findReservation.availableSeats(resInfo.getFlightNumber());

        final Reservation reservation = reservationMapper.toEntity(resInfo, reservationNumber);
        reservation.ensureCanBooking(availableSeats);
        Passenger passenger = passengerMapper.toEntity(resInfo.passengerDto());

        inOrder.verify(passengers).enroll(passenger);
        inOrder.verify(bookingLists).book(reservation);
    }

    @NotNull
    private Reservation createReservation() {
        return getReservation(RESERVATION_NUMBER);
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
        doNothing().when(mock).enroll(any(Passenger.class));
        return mock;
    }

    private SearchReservationService createFindReservationService() {
        final SearchReservationService mock = mock(SearchReservation.class);
        when(mock.availableSeats("0321")).thenReturn(40);
        return mock;
    }

    @NotNull
    private ReservationNumberGenerator createMockReservationNumberGenerator() {
        ReservationNumberGenerator reservationNumber = mock(ReservationNumberGenerator.class);
        when(reservationNumber.generateReservationNumber())
                .thenReturn(ReservationNumber.ofString("AA-7845-65874"));
        return reservationNumber;
    }

}
