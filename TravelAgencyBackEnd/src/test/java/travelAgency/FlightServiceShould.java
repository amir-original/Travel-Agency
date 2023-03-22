package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.exceptions.FlightNumberException;
import travelAgency.domain.exceptions.FlightPriceException;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.repository.flight.FlightRepository;
import travelAgency.services.flights.FindFlights;
import travelAgency.services.flights.FindFlightsService;
import travelAgency.services.flights.FlightService;
import travelAgency.services.flights.FlightServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FlightServiceShould {

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


    private FlightRepository createMockFlightRepository() {
        final FlightRepository mock = mock(FlightRepository.class);
        doNothing().when(mock).addFlight(any(Flight.class));
        return mock;
    }
}
