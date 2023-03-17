package travelAgency.fakeData;

import travelAgency.domain.Passenger;
import travelAgency.repository.passenger.PassengerRepository;

import java.util.Optional;

public class FakePassenger implements PassengerRepository {


    @Override
    public void save(Passenger passenger) {
    }

    @Override
    public Optional<Passenger> passenger(String passengerId) {
        return Optional.empty();
    }

    @Override
    public void truncate() {

    }
}
