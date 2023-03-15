package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.repository.FindFlightRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.List.of;
import static travelAgency.domain.city.City.HAMBURG;
import static travelAgency.domain.city.City.KISH;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FakeFlight implements FindFlightRepository {

    private final List<Flight> flights;

    {
        flights = of(flight().build(),
                flight().withSerialNumber("4256").to(KISH).withPrice(560).build(),
                flight().withSerialNumber("0214").to(HAMBURG).build(),
                flight().withSerialNumber("4578").withPrice(145)
                        .departureAt(LocalDate.of(2023,4,9))
                        .arrivalAt(LocalDate.of(2023,4,5)).build(),
                flight().withSerialNumber("1456").build());
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightSpec) {
        return flights.stream().filter(f -> f.matches(flightSpec)).toList();
    }

    @Override
    public Optional<Flight> findFlight(FlightPlan flightSpec) {
        return flights.stream().filter(f -> f.matches(flightSpec)).findFirst();
    }

}
