package travelAgency.repository.passenger;

import travelAgency.domain.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository {
    void save(Passenger passenger);
    void save(List<Passenger> passengers);
    Optional<Passenger> getPassenger(String passengerId);
    List<Passenger> getPassengers();

    void truncate();
}
