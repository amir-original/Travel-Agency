package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.fake.FakeBookingList;
import travelAgency.fake.FakeFlight;
import travelAgency.fake.FakePassenger;
import travelAgency.fake.FakeTicketGenerator;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.bookingList.TicketGenerator;
import travelAgency.services.flights.FlightAvailabilityImpl;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.fake.FakeFlight.flight;
import static travelAgency.fake.FakePassenger.passenger;

public class BookingListServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private BookingListService app;

    @BeforeEach
    void setUp() {
        TicketGenerator ticketGenerator = new FakeTicketGenerator();
        final FakeBookingList bookings = new FakeBookingList();

        final FlightAvailabilityImpl flightAvailability = new FlightAvailabilityImpl(new FakeFlight(), bookings);
        final FakePassenger passengers = new FakePassenger();
        BookingFlightTicket bookingFlightTicket =
                new BookingFlightTicket(bookings, flightAvailability, passengers, ticketGenerator);
        app = new BookingListServiceImpl(bookings, bookingFlightTicket);
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

        assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
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
                () -> assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                        .isThrownBy(() -> app.findBooking(ticket.ticketNumber())),
                () -> assertThat(app.getBookedSeats(EXIST_FLIGHT_NUMBER)).isEqualTo(bookingsAfterCancel)
                );
    }

    @Test
    void get_number_of_booked_flight() {
        insertMultipleBooking();
        assertThat(app.getBookedSeats("0321")).isEqualTo(19);
    }

    private void insertMultipleBooking() {
        app.book(bookingInformation().withTravelers(5).build());
        app.book(bookingInformation().withPassenger(passenger("se478")).withTravelers(1).build());
        app.book(bookingInformation().withPassenger(passenger("mes784")).withTravelers(3).build());
        app.book(bookingInformation().withPassenger(passenger("ew471")).withTravelers(4).build());
    }
}
