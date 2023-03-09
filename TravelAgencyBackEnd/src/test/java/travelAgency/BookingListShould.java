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

    private BookingListServiceImpl bookingList;
    private final BookingListFake bookingListFake = new BookingListFake();

    @BeforeEach
    void setUp() {
        bookingList = new BookingListServiceImpl(new BookingListDouble());
    }

    @Test
    void search_in_booking_list() {
        final LocalDate birthday =  of(1999, 4, 5);
        var result = bookingList.search("0321", "Sara", birthday);

        assertAll(
                () -> assertThat(result.flight().getName()).isEqualTo(bookingListFake.getFlight().getName()),
                () -> assertThat(result.passenger()).isEqualTo(bookingListFake.getPassenger()),
                () -> assertThat(result.flight()).isEqualTo(bookingListFake.getFlight())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {
        final LocalDate birthday =  of(1999, 4, 5);

        assertThatExceptionOfType(NotFoundAnyBookingFlightException.class)
                .isThrownBy(()-> bookingList.search("023", "Sara", birthday));
    }

    private  class BookingListDouble implements BookingListRepository {

        @Override
        public List<BookingInformation> getAllTickets() {
            return bookingListFake.getFakeBookingTickets();
        }
    }
}
