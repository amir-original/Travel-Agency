package travelAgency.use_case.fake;

import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.domain.passenger.FullName;
import travelAgency.domain.passenger.PassengerId;
import travelAgency.domain.passenger.PhoneNumber;
import travelAgency.domain.passenger.ResidentialAddress;
import travelAgency.exceptions.PassengerNotFoundException;
import travelAgency.domain.passenger.Passenger;
import travelAgency.dao.database.passenger.PassengerRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.TEHRAN;

public class FakePassenger implements PassengerRepository {

    private final List<Passenger> passengers;

    {
        final PassengerBuilder passenger = FakePassenger.passenger();
        final List<Passenger> passengerList = List.of(
                passenger.withId(PassengerId.withId("4444556622")).withFullName(FullName.of("ali","bahrami")).build(),
                passenger.withId(PassengerId.withId("2211334565")).withFullName(FullName.of("mona","jalili")).build(),
                passenger.withId(PassengerId.withId("1221456578")).withFullName(FullName.of("amir","amiri")).build(),
                passenger.withId(PassengerId.withId("5544556699")).build()
        );
        passengers = new LinkedList<>(passengerList);
    }

    @Override
    public void save(Passenger passenger) {
        passengers.add(passenger);
    }

    @Override
    public void save(List<Passenger> passengers) {
        passengers.forEach(this::save);
    }

    @Override
    public Optional<Passenger> findPassengerById(String passengerId) {
        return passengers.stream()
                .filter(passenger -> passenger.getId().equals(passengerId))
                .findFirst();
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public void truncate() {

    }

    public static PassengerBuilder passenger() {
        final var residential = ResidentialAddress.of(TEHRAN.name(),
                "Iran,TEHRAN", "1145789655");
        return PassengerBuilder.passenger()
                .withId(PassengerId.withId("5544556699"))
                .withFullName(FullName.of("Sara","Baiati"))
                .birthday(of(1999, 4, 5))
                .withResidential(residential)
                .withPhoneNumber(PhoneNumber.of("09907994339"));
    }

    public static Passenger passenger(String passengerId) {
        final FakePassenger fakePassenger = new FakePassenger();

        return fakePassenger.findPassengerById(passengerId)
                .orElseThrow(PassengerNotFoundException::new);
    }
}
