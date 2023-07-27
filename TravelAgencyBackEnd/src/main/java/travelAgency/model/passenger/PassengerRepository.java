package travelAgency.model.passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository {
    void enroll(Passenger passenger);
    void enroll(List<Passenger> passengers);
    Optional<Passenger> findPassengerById(String passengerId);
    List<Passenger> getPassengers();
    void truncate();
}
