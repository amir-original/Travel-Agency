package travelAgency.use_case.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.*;
import travelAgency.domain.flight.Flight;
import travelAgency.services.BookingReservation;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flights.FlightAvailabilityImpl;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeTicketNumberGenerator;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.use_case.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class BookingReservationShould {

    private BookingReservation app;

    @BeforeEach
    void setUp() {
        TicketNumberGenerator ticketNumberGenerator = new FakeTicketNumberGenerator();
        FakeBookingList fakeBookingList = new FakeBookingList();
        final FlightAvailabilityImpl flightAvailability =
                new FlightAvailabilityImpl(new FakeFlight(), fakeBookingList);

        app = new BookingReservation(fakeBookingList, flightAvailability,
                new FakePassenger(), ticketNumberGenerator
        );
    }


    @Test
    void book_a_flight_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> app.book(bookingInformation().build()));
    }


    @Test
    void throw_FlightNumberException_when_flight_number_is_empty_or_less_than_3_length() {
        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("").build())
                        .build()));

        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("45").build())
                        .build()));
    }

    @Test
    void throw_PastFlightScheduleException_when_book_flight_with_past_departure_and_arrival_date() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> {
                    final Flight flight = flight().departureAt(yesterday).build();
                    app.book(bookingInformation().withFlight(flight).build());
                });

        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> {
                    final Flight flight = flight().arrivalAt(yesterday).build();
                    app.book(bookingInformation().withFlight(flight).build());
                });
    }

    @Test
    void throw_FlightLocationException_when_origin_and_destination_is_the_same() {
        assertThatExceptionOfType(FlightLocationException.class)
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
    void throw_NotEnoughCapacityException_when_booking_a_flight_if_there_are_insufficient_seats_available() {
        booking30seats();

        assertThatExceptionOfType(NotEnoughCapacityException.class)
                .isThrownBy(() -> app.book(bookingInformation().withTravelers(6).build()));
    }

    private void bookingAllSeats() {
        booking30seats();
        app.book(bookingInformation().withTravelers(5).build());
    }

    private void booking30seats() {
        insertTenBookingFlight();
        insertTenBookingFlight();
        insertTenBookingFlight();
    }

    private void insertTenBookingFlight() {
        app.book(bookingInformation().withTravelers(4).build());
        app.book(bookingInformation().withPassenger(passenger("se478")).withTravelers(2).build());
        app.book(bookingInformation().withPassenger(passenger("mes784")).withTravelers(1).build());
        app.book(bookingInformation().withPassenger(passenger("ew471")).withTravelers(3).build());

    }
}
