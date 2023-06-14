package travelAgency.use_case.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.exceptions.*;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.services.BookingReservation;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.TicketNumberGenerator;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeTicketNumberGenerator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.use_case.fake.FakeReservationInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class BookingReservationShould {

    private BookingReservation app;

    @BeforeEach
    void setUp() {
        TicketNumberGenerator ticketNumberGenerator = new FakeTicketNumberGenerator();
        FakeReservationList fakeBookingList = new FakeReservationList();
        FakeFlight fakeFlight = new FakeFlight();
        FlightListService flightService = new FlightListServiceImpl(fakeFlight);
        ReservationListService reservationListService = new ReservationListServiceImpl(fakeBookingList, flightService);
        FlightAvailability flightAvailability = new FlightAvailability(reservationListService);
        PassengerRepository passengerService = new FakePassenger();

        app = new BookingReservation(fakeBookingList, passengerService, flightAvailability, ticketNumberGenerator);
    }


    @Test
    void book_a_flight_without_throw_any_exception() {
        final ReservationInformation reservationInformation = bookingInformation().build();

        assertThatNoException().isThrownBy(() -> app.book(reservationInformation));
    }


    @Test
    void throw_InvalidFlightNumberException_when_flight_number_is_empty_or_less_than_3_length() {
        assertThatExceptionOfType(InvalidFlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("").build())
                        .build()));

        assertThatExceptionOfType(InvalidFlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("45").build())
                        .build()));
    }

    @Test
    void throw_PastFlightScheduleException_when_book_flight_with_past_departure_date() {
        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight("4784"))
                        .build()));
    }

    @Test
    void throw_PastFlightScheduleException_when_book_flight_with_past_arrival_date() {
        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight("5120"))
                        .build()));
    }

    @Test
    void throw_IllegalArgumentException_when_origin_and_destination_is_the_same() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    final Flight flight = flight().from(TEHRAN).to(TEHRAN).build();
                    app.book(bookingInformation().withFlight(flight).build());
                });
    }

    @Test
    void throw_FullyBookedException_when_attempting_to_book_a_flight_that_has_reached_full_capacity() {
        bookingAllSeats();

        assertThatExceptionOfType(FullyBookedException.class)
                .isThrownBy(() -> app.book(bookingInformation().build()));
    }

    @Test
    void throw_NotEnoughCapacityException_if_there_are_not_enough_seats_available_when_booking_a_flight() {
        booking30seats();

        assertThatExceptionOfType(NotEnoughCapacityException.class)
                .isThrownBy(() -> app.book(bookingInformation().withTravelers(5).build()));
    }

    private void bookingAllSeats() {
        booking30seats();
        app.book(bookingInformation().withTravelers(4).build());
    }

    private void booking30seats() {
        insertTenBookingFlight();
        insertTenBookingFlight();
        insertTenBookingFlight();
    }

    private void insertTenBookingFlight() {
        app.book(bookingInformation().withTravelers(4).build());
        app.book(bookingInformation().withPassenger(passenger("5544556699")).withTravelers(2).build());
        app.book(bookingInformation().withPassenger(passenger("4444556622")).withTravelers(1).build());
        app.book(bookingInformation().withPassenger(passenger("2211334565")).withTravelers(3).build());

    }
}
