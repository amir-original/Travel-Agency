package travelAgency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.Flight;
import travelAgency.domain.FlightSchedule;
import travelAgency.domain.FlightInformation;
import travelAgency.domain.FlightLocation;
import travelAgency.domain.country.France;
import travelAgency.domain.country.Iran;
import travelAgency.domain.country.Iraq;
import travelAgency.domain.country.UnitedKingdom;
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
    private FlightLocation transfer;

    @BeforeEach
    void setUp() {
        app = new FlightServiceImpl(new FlightRepositoryDouble());
        LocalDate departure = of(2023, 3, 3);
        LocalDate arrival = departure.plusDays(3);
        flightSchedule = new FlightSchedule(departure, arrival);
        transfer = new FlightLocation(Iran.TEHRAN, France.PARIS);
    }

    @Test
    void find_flights_with_entered_flight_information() {
        FlightInformation flight = new FlightInformation(transfer, flightSchedule);
        final List<Flight> flights = app.findFlights(flight);

        assertAll(
                () -> assertThat(flights).isNotEmpty(),
                () -> assertThat(flights.get(0).getInfo().equals(flight)).isTrue()
        );
    }

    @Test
    void return_empty_list_when_entered_wrong_information() {
        FlightInformation flight = new FlightInformation(new FlightLocation(UnitedKingdom.LONDON, Iraq.BAGHDAD), flightSchedule);

        assertAll(
                () -> assertThat(app.findFlights(flight)).isEmpty(),
                () -> assertThat(app.isExistThisFlight(flight)).isFalse()
        );

    }

    @Test
    void find_flight_information() {
        FlightInformation flight = new FlightInformation(transfer, flightSchedule);

        assertAll(
                ()->assertThat(app.isExistThisFlight(flight)).isTrue(),
                ()->assertThat(app.findFlight(flight).get())
                        .isEqualTo(new Flight("0321", 145,
                                new FlightInformation(new FlightLocation(Iran.TEHRAN, France.PARIS), flightSchedule)))

        );
    }

    @Test
    void get_all_flights() {
        assertThat(app.getFlights()).isNotEmpty();
    }
}
