package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.fakeData.FakeBookingList;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class BookingListServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private BookingListService service;

    @BeforeEach
    void setUp() {
        service = new BookingListServiceImpl(new FakeBookingList());
    }

    @Test
    void search_in_booking_list() {
        var ticket = service.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(ticket.flightNumber()).isEqualTo(EXIST_FLIGHT_NUMBER),
                () -> assertThat(ticket.passenger()).isEqualTo(passenger().build()),
                () -> assertThat(ticket.flight()).isEqualTo(flight().build())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                .isThrownBy(() -> service.search("023", SARA, SARA_BIRTHDAY));
    }

    @Test
    void cancel_a_book_flight_with_ticket_number() {
        var ticket = service.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);

        assertThatNoException().isThrownBy(()->service.cancel(ticket));

        assertThat(service.isExistFlightTicket(ticket)).isFalse();

       // assertThat(service.tickets().size()).isEqualTo(2);

    }
}
