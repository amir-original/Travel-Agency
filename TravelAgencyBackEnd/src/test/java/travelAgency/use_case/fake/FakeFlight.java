package travelAgency.use_case.fake;

import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.repository.flight.FlightRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;


public class FakeFlight implements FlightRepository {

    private final List<Flight> flights;

    {
        final List<Flight> list = of(
                flight().withFlightNumber("8054").withPrice(450).build(),
                flight().withFlightNumber("4256").withPrice(560).build(),
                flight().withFlightNumber("0214").withPrice(850).build(),
                flight().withFlightNumber("0321").withPrice(145).build(),
                flight().withFlightNumber("1456").withPrice(544).build());
        flights = new LinkedList<>(list);
    }

    @Override
    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    @Override
    public void addFlights(List<Flight> flights) {
        flights.forEach(this::addFlight);
    }

    @Override
    public void deleteFlight(String flightNumber) {
        flights.stream()
                .filter(flight -> flight.matches(flightNumber))
                .findFirst()
                .ifPresent(flights::remove);
    }

    @Override
    public Optional<Flight> findFlight(String flightNumber) {
        return flights.stream().filter(f -> f.matches(flightNumber)).findFirst();
    }


    public static FlightBuilder flight() {
        return FlightBuilder.flight()
                .withFlightNumber("0321")
                .withTotalCapacity(40)
                .withPrice(145)
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(LocalDate.now())
                .arrivalAt(LocalDate.now().plusDays(1));
    }

    public static Flight flight(String flightNumber) {
        return new FakeFlight().findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);
    }


    @Override
    public List<Flight> flights() {
        return flights;
    }

    @Override
    public void truncate() {
        flights.clear();
    }

    public void insertMultipleFlights(){
        addFlights(flights);
    }

}
