package travelAgency.repository.passenger;

import travelAgency.domain.Passenger;

import java.util.Optional;

public interface PassengerRepository {
    void save(Passenger passenger);

    Optional<Passenger> passenger(String passengerId);

    void truncate();
}
