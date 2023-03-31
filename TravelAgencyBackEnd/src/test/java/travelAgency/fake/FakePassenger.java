package travelAgency.fake;

import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.passenger.PassengerRepository;

import java.util.List;
import java.util.Optional;

public class FakePassenger implements PassengerRepository {


    @Override
    public void save(Passenger passenger) {
    }

    @Override
    public void save(List<Passenger> passengers) {

    }

    @Override
    public Optional<Passenger> getPassenger(String passengerId) {
        return Optional.empty();
    }

    @Override
    public List<Passenger> getPassengers() {
        return null;
    }

    @Override
    public void truncate() {

    }
}
