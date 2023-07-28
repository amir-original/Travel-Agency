package travelAgency.application.use_case;

import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.infrastructure.mapper.FlightMapper;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.model.flight.FlightRepository;

import java.util.List;

public final class FindFlight implements FindFlightService {

    private final FlightRepository flights;
    private final FlightMapper flightMapper;

    public FindFlight(FlightRepository flights) {
        this.flights = flights;
        flightMapper = new FlightMapper();
    }

    @Override
    public List<FlightDto> searchFlights(FlightPlanRequest searchFlightPlan) {
        FlightPlan flightPlan = flightMapper.toEntity(searchFlightPlan);
        List<Flight> flightList = flights.flights().stream()
                .filter(flight -> flight.hasSameFlightPlan(flightPlan))
                .toList();

        return flightMapper.toViewDto(flightList);
    }

    @Override
    public FlightDto findFlight(String flightNumber) {
        final Flight flight = flights.findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);

        return flightMapper.toViewDto(flight);
    }

}
