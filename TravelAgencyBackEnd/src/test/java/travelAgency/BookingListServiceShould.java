package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.fake.FakeBookingList;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.fake.FakeFlightBuilder.flight;
import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.fake.FakePassengerBuilder.passenger;

public class BookingListServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private BookingListService app;

    @BeforeEach
    void setUp() {
        TicketNumberGenerator ticketNumberGenerator = mock(TicketNumberGenerator.class);
        when(ticketNumberGenerator.generate()).thenReturn("56472514");
        app = new BookingListServiceImpl(new FakeBookingList(), ticketNumberGenerator);
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
                () -> assertThatNoException().isThrownBy(() -> app.cancel(ticket)),
                () -> assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                        .isThrownBy(() -> app.ticket(ticket.ticketNumber())),
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
        app.book(bookingInformation().withPassenger(passenger().withId("oi").firstName("ali").build())
                .withTravelers(1).build());
        app.book(bookingInformation().withPassenger(passenger().withId("srs").firstName("hasan").build())
                .withTravelers(3).build());
        app.book(bookingInformation().withPassenger(passenger().withId("mona").firstName("mona").build())
                .withTravelers(4).build());
    }
}
