package travelAgency.services.flight;

import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.dao.database.flight.FlightRepository;

import java.util.List;

public class FlightListServiceImpl implements FlightListService {

    private final FlightRepository flights;

    public FlightListServiceImpl(FlightRepository flights) {
        this.flights = flights;
    }

    @Override
    public List<Flight> searchFlights(FlightPlan searchFlightPlan) {
        return flights.flights().stream()
                .filter(flight -> flight.hasSameFlightPlan(searchFlightPlan))
                .toList();
    }

    @Override
    public Flight findFlight(String flightNumber) {
        return flights.findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);
    }

    @Override
    public int getTotalCapacity(String flightNumber) {
        return findFlight(flightNumber).totalCapacity();
    }

}
