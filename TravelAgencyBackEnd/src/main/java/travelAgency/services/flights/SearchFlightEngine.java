package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public record SearchFlightEngine(List<Flight> flights) {

    public List<Flight> search(FlightPlan searchFlightPlan){
       return flights.stream()
               .filter(flight -> flight.matches(searchFlightPlan))
               .toList();
    }

}
