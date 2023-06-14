package travelAgency.use_case.fake;

import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;
import travelAgency.exceptions.PassengerNotFoundException;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.dao.database.passenger.PassengerRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.RASHT;
import static travelAgency.domain.city.City.TEHRAN;

public class FakePassenger implements PassengerRepository {

    private final List<Passenger> passengers;

    {
        final PassengerBuilder passenger = FakePassenger.passenger();
        final List<Passenger> passengerList = List.of(
                passenger.withId(PassengerId.of("4444556622")).fullName(FullName.of("ali","bahrami")).build(),
                passenger.withId(PassengerId.of("2211334565")).fullName(FullName.of("mona","jalili")).build(),
                passenger.withId(PassengerId.of("1221456578")).fullName(FullName.of("amir","amiri")).ofCity(RASHT.name()).build(),
                passenger.withId(PassengerId.of("5544556699")).build()
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
        return PassengerBuilder.passenger()
                .withId(PassengerId.of("5544556699"))
                .fullName(FullName.of("Sara","Baiati"))
                .withBirthday(of(1999, 4, 5))
                .ofCity(TEHRAN.name())
                .withAddress("Iran,TEHRAN")
                .withZipcode("1145789")
                .withPhoneNumber(Phone.of("09907994339"));
    }

    public static Passenger passenger(String passengerId) {
        final FakePassenger fakePassenger = new FakePassenger();

        return fakePassenger.findPassengerById(passengerId)
                .orElseThrow(PassengerNotFoundException::new);
    }
}
