package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.repository.FindFlightRepository;

import java.util.List;
import java.util.Optional;

public class FakeFlight implements FindFlightRepository {

    private final FakeFlightData flightsFake = new FakeFlightData();

    @Override
    public List<Flight> getFlights() {
        return flightsFake.getFlights();
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightSpec) {
        return flightsFake.getFlights().stream().filter(f -> f.matches(flightSpec)).toList();
    }

    @Override
    public Optional<Flight> findFlight(FlightPlan flightSpec) {
        return flightsFake.getFlights().stream().filter(f -> f.matches(flightSpec)).findFirst();
    }

}
