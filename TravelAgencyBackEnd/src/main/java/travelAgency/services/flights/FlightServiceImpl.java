package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flights;
    private final FlightAvailability flightAvailability;

    public FlightServiceImpl(FlightRepository flights, FlightAvailability flightAvailability) {
        this.flights = flights;
        this.flightAvailability = flightAvailability;
    }

    @Override
    public List<Flight> search(FlightPlan searchFlightPlan) {
        return searchFlightPlan.search(flights.flights());
    }

    @Override
    public Flight findFlight(String flightNumber) {
        return flights.findFlight(flightNumber)
                .orElseThrow(FlightNumberNotFoundException::new);
    }

    @Override
    public int availableSeats(String flightNumber) {
        return 5;
    }
}
