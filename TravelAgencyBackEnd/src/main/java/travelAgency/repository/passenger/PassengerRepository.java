package travelAgency.repository.passenger;

import travelAgency.domain.passenger.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository {
    void save(Passenger passenger);
    void save(List<Passenger> passengers);
    Optional<Passenger> findPassengerById(String passengerId);
    List<Passenger> getPassengers();
    void truncate();
}
