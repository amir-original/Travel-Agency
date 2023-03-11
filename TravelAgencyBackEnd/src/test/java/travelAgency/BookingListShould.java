package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.BookingInformation;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.fakeData.BookingListFake;
import travelAgency.repository.BookingListRepository;
import travelAgency.service.BookingListServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingListShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private BookingListServiceImpl bookingList;
    private final BookingListFake bookingListFake = new BookingListFake();

    @BeforeEach
    void setUp() {
        bookingList = new BookingListServiceImpl(new BookingListDouble());
    }

    @Test
    void search_in_booking_list() {
        var result = bookingList.search("0321", SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(result.flight().getSerialNumber()).isEqualTo(bookingListFake.getFlight().getSerialNumber()),
                () -> assertThat(result.passenger()).isEqualTo(bookingListFake.getPassenger()),
                () -> assertThat(result.flight()).isEqualTo(bookingListFake.getFlight())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                .isThrownBy(()-> bookingList.search("023", SARA, SARA_BIRTHDAY));
    }

    private  class BookingListDouble implements BookingListRepository {

        @Override
        public List<BookingInformation> getAllTickets() {
            return bookingListFake.getFakeBookingTickets();
        }
    }
}
