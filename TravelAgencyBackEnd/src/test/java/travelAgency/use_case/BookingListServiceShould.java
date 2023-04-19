package travelAgency.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.ReservationNotFoundException;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeTicketNumberGenerator;
import travelAgency.services.BookingReservation;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.flight.FlightAvailabilityImpl;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.use_case.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class BookingListServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    public static final String NOT_FOUND_TICKET_NUMBER = "78456871";
    private BookingListService app;
    private BookingReservation appService;

    @BeforeEach
    void setUp() {
        TicketNumberGenerator ticketNumberGenerator = new FakeTicketNumberGenerator();
        final FakeBookingList bookings = new FakeBookingList();

        final FlightAvailabilityImpl flightAvailability = new FlightAvailabilityImpl(new FakeFlight(), bookings);
        final FakePassenger passengers = new FakePassenger();
        appService = new BookingReservation(bookings, flightAvailability, passengers, ticketNumberGenerator);
        app = new BookingListServiceImpl(bookings);
    }


    @Test
    void search_in_booking_list() {
        var ticket = app.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(ticket.flightNumber()).isEqualTo(EXIST_FLIGHT_NUMBER),
                () -> assertThat(ticket.passenger()).isEqualTo(passenger().build()),
                () -> assertThat(ticket.flight()).isEqualTo(flight().build())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(ReservationNotFoundException.class)
                .isThrownBy(() -> app.search("023", SARA, SARA_BIRTHDAY));
    }

    @Test
    void cancel_a_book_flight_with_ticket_number() {
        var ticket = app.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);
        final int travelers = ticket.travelers();
        final int bookingsBeforeCancel = 6;
        final int bookingsAfterCancel = bookingsBeforeCancel - travelers;

        assertAll(
                () -> assertThat(app.getBookedSeats(EXIST_FLIGHT_NUMBER)).isEqualTo(bookingsBeforeCancel),
                () -> assertThatNoException().isThrownBy(() -> app.cancel(ticket.ticketNumber())),
                () -> assertThat(app.getBookedSeats(EXIST_FLIGHT_NUMBER)).isEqualTo(bookingsAfterCancel)
                );
    }

    @Test
    void throw_ReservationNotFoundException_when_cancel_the_ticket_number_is_not_found() {
        assertThatExceptionOfType(ReservationNotFoundException.class)
                .isThrownBy(()-> app.cancel(NOT_FOUND_TICKET_NUMBER));
    }

    @Test
    void get_number_of_booked_flight() {
        insertMultipleBooking();
        assertThat(app.getBookedSeats("0321")).isEqualTo(19);
    }


    private void insertMultipleBooking() {
        appService.book(bookingInformation().withTravelers(5).build());
        appService.book(bookingInformation().withPassenger(passenger("44")).withTravelers(1).build());
        appService.book(bookingInformation().withPassenger(passenger("55")).withTravelers(3).build());
        appService.book(bookingInformation().withPassenger(passenger("22")).withTravelers(4).build());
    }
}
