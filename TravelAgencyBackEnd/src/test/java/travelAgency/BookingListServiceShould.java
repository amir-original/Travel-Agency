package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.booking.TicketNumberGenerator;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.domain.exceptions.OverBookedFlightException;
import travelAgency.fakeData.FakeBookingList;
import travelAgency.services.booking.BookingFlightService;
import travelAgency.services.booking.BookingFlightServiceImpl;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

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

        assertThatNoException().isThrownBy(() -> app.cancel(ticket));

        assertThat(app.isExistFlightTicket(ticket.ticketNumber())).isFalse();
    }

    @Test
    void get_number_of_booked_blight() {
        insertMultipleBooking();
        assertThat(app.numberOfBookedFlight("0321")).isEqualTo(19);
    }

    //TODO implement
   /* @Test
    void throw_OverBookedFlightException_when_book_flight_that_all_flight_seats_is_sold() {
        insertMultipleBooking();
        assertThatExceptionOfType(OverBookedFlightException.class)
                .isThrownBy(() -> app.book(flightTicketInfo().build()));
    }*/

    private void insertMultipleBooking() {
        app.book(flightTicketInfo().withNumbers(5).build());
        app.book(flightTicketInfo().withPassenger(passenger().withId("oi").firstName("ali").build())
                .withNumbers(1).build());
        app.book(flightTicketInfo().withPassenger(passenger().withId("srs").firstName("hasan").build())
                .withNumbers(3).build());
        app.book(flightTicketInfo().withPassenger(passenger().withId("mona").firstName("mona").build())
                .withNumbers(4).build());
    }
}
