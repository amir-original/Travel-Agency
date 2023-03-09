package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightTransit;
import travelAgency.fakeData.TravelAgencyFake;
import travelAgency.repository.FlightRepository;
import travelAgency.service.FlightService;
import travelAgency.service.FlightServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FlightServiceShould {
    private FlightService app;
    private FlightSchedule flightSchedule;
    private FlightTransit transfer;

    @BeforeEach
    void setUp() {
        app = new FlightServiceImpl(new FlightRepositoryDouble());
        LocalDate departure = of(2023, 3, 3);
        LocalDate arrival = departure.plusDays(3);
        flightSchedule = new FlightSchedule(departure, arrival);
        transfer = new FlightTransit("Iran", "Paris");
    }

    @Test
    void find_flights_with_entered_flight_information() {
        Flight flight = new Flight(transfer, flightSchedule);
        final List<Flight> flights = app.findFlights(flight);
        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).like(flight)).isTrue()
        );
    }

    private static class FlightRepositoryDouble implements FlightRepository {
        private final TravelAgencyFake travelAgencyFake = new TravelAgencyFake();

        @Override
        public List<Flight> getFlights() {
            return null;
        }

        @Override
        public List<Flight> findFlights(Flight flightInfo) {
            return travelAgencyFake.getFakeFlights().stream().filter(f -> f.like(flightInfo)).toList();
        }
    }
}
