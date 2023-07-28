package travelAgency.application.use_case;

import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.dto.FlightDto;

import java.util.List;

public interface FindFlightService {
    List<FlightDto> searchFlights(FlightPlanRequest searchFlightPlan);
    FlightDto findFlight(String flightNumber);
}
