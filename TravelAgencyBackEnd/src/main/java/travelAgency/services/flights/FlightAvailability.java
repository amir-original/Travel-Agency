package travelAgency.services.flights;

public interface FlightAvailability {
    void checkingFlight(String flightNumber, int newTravelers);
    int totalCapacity(String flightNumber);
}
