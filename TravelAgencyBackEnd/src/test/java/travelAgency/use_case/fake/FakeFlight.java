package travelAgency.use_case.fake;

import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.dao.database.flight.FlightRepository;
import travelAgency.domain.rate.currency.Money;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.domain.rate.currency.Currency.USD;


public class FakeFlight implements FlightRepository {

    private final List<Flight> flights;

    public static final LocalDate NOW = LocalDate.now();

    {
        final LocalDate yesterday = NOW.minusDays(1);

        final List<Flight> list = of(
                flight().withFlightNumber("8054").withPrice(Money.of(560, USD)).build(),
                flight().withFlightNumber("4256").withPrice(Money.of(560,USD)).build(),
                flight().withFlightNumber("0214").withPrice(Money.of(88,USD)).build(),
                flight().withFlightNumber("0321").withPrice(Money.of(100,USD)).build(),
                flight().withFlightNumber("1456").withPrice(Money.of(400,USD)).build(),
                flight().withFlightNumber("4784").withPrice(Money.of(310,USD)).departureAt(yesterday).build(),
                flight().withFlightNumber("5120").withPrice(Money.of(70,USD)).arrivalAt(yesterday).build());
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
    public void deleteFlight(Flight flight) {
        flights.stream()
                .filter(f -> f.match(flight))
                .findFirst()
                .ifPresent(flights::remove);
    }

    @Override
    public Optional<Flight> findFlight(String flightNumber) {
        return flights.stream()
                .filter(f -> f.hasSameFlightNumber(flightNumber))
                .findFirst();
    }


    public static FlightBuilder flight() {
        return FlightBuilder.flight()
                .withFlightNumber("0321")
                .withTotalCapacity(40)
                .withPrice(Money.of(100,USD))
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(NOW)
                .arrivalAt(NOW.plusDays(1));
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

}
