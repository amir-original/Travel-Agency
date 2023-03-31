package travelAgency.fake;

import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static travelAgency.fake.FakeFlightBuilder.flight;


public class FakeFlight implements FlightRepository {

    private final List<Flight> flights;

    {
        flights = of(flight().build(),
                flight().withPrice(560).withFlightNumber("4256").build(),
                flight().withFlightNumber("0214").withPrice(850).build(),
                flight().withFlightNumber("0321").withPrice(145).build(),
                flight().withFlightNumber("1456").withPrice(544).build());
    }

    @Override
    public void addFlight(Flight flight) {

    }

    @Override
    public void addFlights(List<Flight> flights) {

    }

    @Override
    public void deleteFlight(String serialNumber) {

    }

    @Override
    public Optional<Flight> findFlight(String flightNumber) {
        return flights.stream().filter(f -> f.matches(flightNumber)).findFirst();
    }

    @Override
    public List<Flight> flights() {
        return flights;
    }

    @Override
    public int numberOfSeats(String flightNumber) {
        return findFlight(flightNumber)
                .orElseThrow(NotFindAnyFlightException::new)
                .totalCapacity();
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightPlan) {
        return flights.stream().filter(flight -> flight.matches(flightPlan)).toList();
    }

    @Override
    public void truncate() {

    }

    @Override
    public void checkExistenceFlightWith(String flightNumber) {
        final boolean isPresent = flights.stream().anyMatch(flight -> flight.matches(flightNumber));
        if (!isPresent)
            throw new NotFindAnyFlightException();
    }

}
