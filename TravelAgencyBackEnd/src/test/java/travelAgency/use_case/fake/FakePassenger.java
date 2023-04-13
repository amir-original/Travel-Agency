package travelAgency.use_case.fake;

import travelAgency.domain.exceptions.NotFoundAnyPassengerException;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.repository.passenger.PassengerRepository;

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
                passenger.withId("se478").firstName("ali").lastName("bahrami").build(),
                passenger.withId("mes784").firstName("mona").lastName("jalili").build(),
                passenger.withId("ew471").firstName("amir").lastName("amiri").ofCity(RASHT).build());
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
                .filter(passenger -> passenger.id().equals(passengerId))
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
                .withId("sa78478")
                .firstName("Sara")
                .lastName("Baiati")
                .withBirthday(of(1999, 4, 5))
                .ofCity(TEHRAN)
                .withAddress("Iran,TEHRAN")
                .withZipcode("1145789")
                .withPhoneNumber("989907994339");
    }

    public static Passenger passenger(String passengerId) {
        final FakePassenger fakePassenger = new FakePassenger();

        return fakePassenger.findPassengerById(passengerId).orElseThrow(NotFoundAnyPassengerException::new);
    }
}
