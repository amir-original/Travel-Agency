package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.*;
import travelAgency.domain.exceptions.*;
import travelAgency.fakeData.BookingListFake;
import travelAgency.fakeData.TravelAgencyFake;
import travelAgency.repository.TravelAgencyRepository;
import travelAgency.service.TravelAgencyServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TravelAgencyShould {

    private final BookingListFake bookingListFake = new BookingListFake();
    private TravelAgencyServiceImpl app;
    private FlightSchedule flightSchedule;
    private FlightTransit transfer;

    @BeforeEach
    void setUp() {
        app = new TravelAgencyServiceImpl(new TravelAgencyRepositoryDouble());
        LocalDate departure = of(2023, 3, 3);
        LocalDate arrival = departure.plusDays(3);
        flightSchedule = new FlightSchedule(departure, arrival);
        transfer = new FlightTransit("Iran", "Paris");
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        BookingInformation bookingInformation = bookingListFake.getBookingTicket();

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

    @Test
    void find_flights_with_entered_flight_information() {
        Flight flight = new Flight(transfer, flightSchedule);
        final List<Flight> flights = app.findFlights(flight);

        flights.forEach(System.out::println);
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).like(flight)).isTrue()
        );
    }

    private static class TravelAgencyRepositoryDouble implements TravelAgencyRepository {
        private final TravelAgencyFake travelAgencyFake = new TravelAgencyFake();

        @Override
        public List<Flight> findFlights(Flight flight) {
            return travelAgencyFake.getFakeFlights().stream().filter(f -> f.like(flight)).toList();
        }

        @Override
        public void book(BookingInformation bookingInformation) {
            if (!bookingInformation.flight().getName().equals("0321"))
                throw new NotFindAnyFlightException();

            bookingInformation.check();
        }
    }
}
