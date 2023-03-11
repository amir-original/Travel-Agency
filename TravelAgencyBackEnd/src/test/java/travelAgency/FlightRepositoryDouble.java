package travelAgency;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightInformation;
import travelAgency.fakeData.FlightsFake;
import travelAgency.repository.FlightRepository;

import java.util.List;
import java.util.Optional;

class FlightRepositoryDouble implements FlightRepository {

    private final FlightsFake flightsFake = new FlightsFake();

    @Override
    public List<Flight> getFlights() {
        return flightsFake.getFlights();
    }

    @Override
    public List<Flight> findFlights(FlightInformation flightSpec) {
        return flightsFake.getFlights().stream().filter(f -> f.isTheSameSpec(flightSpec)).toList();
    }

    @Override
    public Optional<Flight> findFlight(FlightInformation flightSpec) {
        return flightsFake.getFlights().stream().filter(f -> f.isTheSameSpec(flightSpec)).findFirst();
    }

}
