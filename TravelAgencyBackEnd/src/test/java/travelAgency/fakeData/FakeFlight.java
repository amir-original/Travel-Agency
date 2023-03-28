package travelAgency.fakeData;

import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.repository.flight.FlightRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static travelAgency.domain.city.City.KISH;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FakeFlight implements FlightRepository {

    private final List<Flight> flights;

    {
        flights = of(flight().build(),
                flight().withFlightNumber("4256").to(KISH).withPrice(560).build(),
                flight().withFlightNumber("0214").to(City.AHVAZ).build(),
                flight().withFlightNumber("4578").withPrice(145)
                        .departureAt(LocalDate.of(2023, 4, 9))
                        .arrivalAt(LocalDate.of(2023, 4, 5)).build(),
                flight().withFlightNumber("1456").build());
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
