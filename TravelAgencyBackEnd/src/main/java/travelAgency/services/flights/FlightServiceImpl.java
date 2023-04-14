package travelAgency.services.flights;

import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flights;

    public FlightServiceImpl(FlightRepository flights) {
        this.flights = flights;
    }

    @Override
    public List<Flight> searchFlights(FlightPlan searchFlightPlan) {
        return searchFlightPlan.search(flights.flights());
    }

    @Override
    public Flight findFlight(String flightNumber) {
        return flights.findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);
    }

    @Override
    public int availableSeats(String flightNumber) {
        return 5;
    }
}
