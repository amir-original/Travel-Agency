package travelAgency;

import travelAgency.domain.Flight;
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
    public List<Flight> findFlights(Flight flight) {
        return flightsFake.getFlights().stream().filter(f -> f.like(flight)).toList();
    }

    @Override
    public Optional<Flight> findFlight(Flight flightInfo) {
        return flightsFake.getFlights().stream().filter(f -> f.like(flightInfo)).findFirst();
    }
}
