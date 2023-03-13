package travelAgency.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.FlightTicket;
import travelAgency.domain.exceptions.FlightInfoNotNullException;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.domain.exceptions.PassengerNotNullException;
import travelAgency.domain.exceptions.TicketNumberNotZeroException;
import travelAgency.fakeData.FakeFlight;
import travelAgency.fakeData.FakeTicketsData;
import travelAgency.repository.BookingFlightRepository;
import travelAgency.services.flights.FindFlights;
import travelAgency.services.booking.BookingFlight;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class BookingFlightShould {

    private final FakeTicketsData bookingListFake = new FakeTicketsData();
    private BookingFlight app;

    @BeforeEach
    void setUp() {
        app = new BookingFlight(new FakeBookingFlight(), new FindFlights(new FakeFlight()));
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        FlightTicket bookingInformation = bookingListFake.getExistTicket();

        assertThatNoException().isThrownBy(() -> app.book(bookingInformation));
    }

    @Test
    void throw_Passenger_Not_Null_exception_when_enter_null_passenger() {
        FlightTicket bookingInformation = bookingListFake.getBookingInfoWithNullPassenger();

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
    void throw_TicketNumberNotZeroException_when_enter_number_of_ticket_zero_or_less() {
        var zeroOrNullTicket = bookingListFake.getBookingInfoWithZeroOrNullTicket();
        assertThatExceptionOfType(TicketNumberNotZeroException.class)
                .isThrownBy(() -> app.book(zeroOrNullTicket));
    }

    @Test
    void throw_NotFindAnyFlightException_when_there_is_not_flight_with_entered_information() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(() -> app.book(bookingListFake.getNotFoundTicket()));
    }

    private static class FakeBookingFlight implements BookingFlightRepository {

        @Override
        public void book(FlightTicket bookingInformation) {
            if (!bookingInformation.flight().getSerialNumber().equals("0321"))
                throw new NotFindAnyFlightException();

            bookingInformation.check();
        }
    }

}
