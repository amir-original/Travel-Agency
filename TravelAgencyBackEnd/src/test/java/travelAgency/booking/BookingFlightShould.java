package travelAgency.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.FlightTicket;
import travelAgency.domain.exceptions.*;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.fakeData.FakePassenger;
import travelAgency.repository.booking.BookingFlightRepository;
import travelAgency.services.booking.BookingFlight;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;

public class BookingFlightShould {

    private BookingFlight app;

    @BeforeEach
    void setUp() {
        app = new BookingFlight(new FakeBookingFlight(), new FakeFindFlight(), new FakePassenger());
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> app.book(flightTicket().build()));
    }

    @Test
    void throw_IllegalArgumentException_when_pass_null_argument() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicket().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicket().withFlight(null).build()))

        );

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
        private static final String EXIST_FLIGHT_NUMBER = "0321";

        @Override
        public int book(FlightTicket flightTicket) {
            if (!flightTicket.flightNumber().equals(EXIST_FLIGHT_NUMBER))
                throw new NotFindAnyFlightException();

            flightTicket.check();
            return 0;
        }

        @Override
        public Optional<FlightTicket> ticket(String ticketNumber) {
            return Optional.empty();
        }

        @Override
        public List<FlightTicket> tickets() {
            return null;
        }

        @Override
        public void truncate() {

        }
    }

}
