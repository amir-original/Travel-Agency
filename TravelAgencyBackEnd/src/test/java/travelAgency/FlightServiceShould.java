package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.exceptions.FlightNumberException;
import travelAgency.domain.exceptions.FlightPriceException;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.services.flights.FlightService;
import travelAgency.services.flights.FlightServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FlightServiceShould {

    private static final String NOT_FOUND_FLIGHT_NUMBER = "NotFoundFlightNumber";
    private FlightService flightService;


    @BeforeEach
    void setUp() {
        final FlightRepository mock = createMockFlightRepository();
        flightService = new FlightServiceImpl(mock);
    }

    @Test
    void add_flight_without_throw_any_exceptions() {
        assertThatNoException()
                .isThrownBy(()-> flightService.add(flight().build()));
    }

    @Test
    void throw_FlightNumberException_when_enter_flight_number_empty_or_blank() {
        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(()->flightService.add(flight().withFlightNumber("").build()));

        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(()->flightService.add(flight().withFlightNumber("     ").build()));
    }

    @Test
    void throw_FlightPriceException_when_enter_price_zero_or_less() {
        assertThatExceptionOfType(FlightPriceException.class)
                .isThrownBy(()->flightService.add(flight().withPrice(0).build()));
    }

    @Test
    void add_multiple_flights_without_throw_any_exceptions() {
        List<Flight> flights = List.of(flight().build(),
                flight().withFlightNumber("sert788").build(),
                flight().withFlightNumber("naw784").build());

        assertThatNoException()
                .isThrownBy(()-> flightService.add(flights));
    }

    @Test
    void remove_flight_without_throw_any_exception() {
        final Flight flight = flight().build();

        assertThatNoException()
                .isThrownBy(()-> flightService.delete(flight.flightNumber()));
    }

    @Test
    void throw_NotFoundAnyFlightException_when_delete_not_found_flight() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(()-> flightService.delete(NOT_FOUND_FLIGHT_NUMBER));
    }

    private FlightRepository createMockFlightRepository() {
        final FlightRepository mock = mock(FlightRepository.class);
        doNothing().when(mock).addFlight(any(Flight.class));
        doNothing().when(mock).checkExistenceFlightWith(anyString());
        doThrow(NotFindAnyFlightException.class).when(mock).checkExistenceFlightWith(NOT_FOUND_FLIGHT_NUMBER);
        return mock;
    }
}
