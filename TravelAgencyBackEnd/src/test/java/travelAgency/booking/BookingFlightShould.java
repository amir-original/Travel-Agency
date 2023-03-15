package travelAgency.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.FlightTicket;
import travelAgency.domain.exceptions.*;
import travelAgency.fakeData.FakeFlight;
import travelAgency.repository.BookingFlightRepository;
import travelAgency.services.flights.FindFlights;
import travelAgency.services.booking.BookingFlight;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;

public class BookingFlightShould {

    private BookingFlight app;

    @BeforeEach
    void setUp() {
        app = new BookingFlight(new FakeBookingFlight(), new FindFlights(new FakeFlight()));
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> app.book(flightTicket().build()));
    }

    @Test
    void throw_PassengerNotNullException_when_pass_ticket_with_null_passenger() {
        assertThatExceptionOfType(PassengerNotNullException.class)
                .isThrownBy(() -> app.book(flightTicket().withPassenger(null).build()));
    }


    @Test
    void throw_FlightNotNullException_when_pass_ticket_with_null_flight() {
        assertThatExceptionOfType(FlightNotNullException.class)
                .isThrownBy(() -> app.book(flightTicket().withFlight(null).build()));
    }

    @Test
    void throw_TicketNumberNotZeroException_when_enter_number_of_ticket_zero_or_less() {
        assertThatExceptionOfType(TicketNumberNotZeroException.class)
                .isThrownBy(() -> app.book(flightTicket().withNumbers(0).build()));
    }

    @Test
    void throw_NotFindAnyFlightException_when_there_is_not_find_flight_with_entered_information() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(() -> app.book(flightTicket().withNotFoundFlight().build()));
    }

    private static class FakeBookingFlight implements BookingFlightRepository {
        private static final String EXIST_FLIGHT_SERIAL_NUMBER = "0321";

        @Override
        public void book(FlightTicket bookingInformation) {
            if (!bookingInformation.flight().getSerialNumber().equals(EXIST_FLIGHT_SERIAL_NUMBER))
                throw new NotFindAnyFlightException();

            bookingInformation.check();
        }
    }

}
