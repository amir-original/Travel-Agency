package travelAgency.application.flight;

import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.FlightMapper;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.model.flight.FlightRepository;

import java.util.List;

public final class FlightListServiceImpl implements FlightListService {

    private final FlightRepository flights;
    private final FlightMapper flightMapper;

    public FlightListServiceImpl(FlightRepository flights) {
        this.flights = flights;
        flightMapper = new FlightMapper();
    }

    @Override
    public List<Flight> searchFlights(FlightPlan searchFlightPlan) {
        return flights.flights().stream()
                .filter(flight -> flight.hasSameFlightPlan(searchFlightPlan))
                .toList();
    }

    @Override
    public FlightDto findFlight(String flightNumber) {
        final Flight flight = flights.findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);

        return flightMapper.toViewDto(flight);
    }

    @Override
    public int getTotalCapacity(String flightNumber) {
        return findFlight(flightNumber).getTotalCapacity();
    }

}
