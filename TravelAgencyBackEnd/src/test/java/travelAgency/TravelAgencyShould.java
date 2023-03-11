package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.BookingInformation;
import travelAgency.domain.exceptions.FlightInfoNotNullException;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.domain.exceptions.PassengerNotNullException;
import travelAgency.domain.exceptions.TicketNumberNotZeroException;
import travelAgency.fakeData.BookingListFake;
import travelAgency.repository.TravelAgencyRepository;
import travelAgency.service.FlightServiceImpl;
import travelAgency.service.TravelAgencyServiceImpl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class TravelAgencyShould {

    private final BookingListFake bookingListFake = new BookingListFake();
    private TravelAgencyServiceImpl app;

    @BeforeEach
    void setUp() {
        app = new TravelAgencyServiceImpl(new TravelAgencyRepositoryDouble(),
                new FlightServiceImpl(new FlightRepositoryDouble()));
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        BookingInformation bookingInformation = bookingListFake.getExistTicket();

        assertThatNoException().isThrownBy(() -> app.book(bookingInformation));
    }

    @Test
    void throw_Passenger_Not_Null_exception_when_enter_null_passenger() {
        BookingInformation bookingInformation = bookingListFake.getBookingInfoWithNullPassenger();

        assertThatExceptionOfType(PassengerNotNullException.class)
                .isThrownBy(() -> app.book(bookingInformation));
    }


    @Test
    void throw_Booking_Information_not_null_exception_when_enter_null_info() {
        var bookingInformation = bookingListFake.getBookingInfoWithNullFlight();

        assertThatExceptionOfType(FlightInfoNotNullException.class)
                .isThrownBy(() -> app.book(bookingInformation));
    }

    @Test
    void throw_Ticket_not_Zero_Exception_when_enter_number_of_ticket_zero_or_less() {
        var zeroOrNullTicket = bookingListFake.getBookingInfoWithZeroOrNullTicket();
        assertThatExceptionOfType(TicketNumberNotZeroException.class)
                .isThrownBy(() -> app.book(zeroOrNullTicket));
    }

    @Test
    void throw_not_find_any_flight_exception_when_there_is_not_flight_with_entered_information() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(() -> app.book(bookingListFake.getNotFoundTicket()));
    }

    private static class TravelAgencyRepositoryDouble implements TravelAgencyRepository {

        @Override
        public void book(BookingInformation bookingInformation) {
            if (!bookingInformation.flight().getSerialNumber().equals("0321"))
                throw new NotFindAnyFlightException();

            bookingInformation.check();
        }
    }

}
