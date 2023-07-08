package travelAgency.application.flight;

import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.dto.FlightDto;

import java.util.List;

public interface FlightListService {
    List<Flight> searchFlights(FlightPlan searchFlightPlan);
    FlightDto findFlight(String flightNumber);
    int getTotalCapacity(String flightNumber);
}
