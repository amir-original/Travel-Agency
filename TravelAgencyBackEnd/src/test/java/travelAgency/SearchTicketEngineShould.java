package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.fakeData.FakeBookingList;
import travelAgency.fakeData.FakePassengerBuilder;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.bookingList.SearchTicketEngine;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class SearchTicketEngineShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_SERIAL_NUMBER = "0321";
    private SearchTicketEngine searchTicketEngine;

    @BeforeEach
    void setUp() {
        BookingListServiceImpl bookingList = new BookingListServiceImpl(new FakeBookingList());
        searchTicketEngine = new SearchTicketEngine(bookingList);
    }

    @Test
    void search_in_booking_list() {
        var result = searchTicketEngine.search(EXIST_FLIGHT_SERIAL_NUMBER, SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(result.flight().getSerialNumber()).isEqualTo(EXIST_FLIGHT_SERIAL_NUMBER),
                () -> assertThat(result.passenger()).isEqualTo(passenger().build()),
                () -> assertThat(result.flight()).isEqualTo(flight().build())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                .isThrownBy(()-> searchTicketEngine.search("023", SARA, SARA_BIRTHDAY));
    }
}
