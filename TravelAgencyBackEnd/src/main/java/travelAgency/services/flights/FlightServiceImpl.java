package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void add(Flight flight) {
        flight.check();
        flightRepository.addFlight(flight);
    }

    @Override
    public void add(List<Flight> flights) {
        checkFlights(flights);
        flightRepository.addFlights(flights);
    }

    private void checkFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            flight.check();
        }
    }

    @Override
    public void delete(String flightNumber) {
        flightRepository.checkExistenceFlightWith(flightNumber);
        flightRepository.deleteFlight(flightNumber);
    }


}
