package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.passenger.PassengerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static travelAgency.fake.FakePassengerBuilder.passenger;

public class PassengerRepositoryShould {

    private PassengerRepositoryImpl api;

    @BeforeEach
    void setUp() {
        api = new PassengerRepositoryImpl(new MySQLDbConnection());
    }

    @Test
    void save_passenger_info_and_fetch_passenger_with_id() {
        final Passenger passenger = passenger().build();
        api.save(passenger);

        final Optional<Passenger> fetchedPassenger = api.getPassenger(passenger.id());

        if (fetchedPassenger.isEmpty()) {
            fail("not found any getPassenger with this id in db");
        }

        assertThat(passenger).isEqualTo(fetchedPassenger.get());
    }

    @Test
    void fetch_all_passengers() {
        insertMultiplePassengers();

        assertThat(api.getPassengers().size()).isEqualTo(3);
    }

    private void insertMultiplePassengers() {
        final List<Passenger> passengers = List.of(
                passenger().withId("sa45").firstName("Sara").build(),
                passenger().withId("am45").firstName("amir").build(),
                passenger().withId("moh45").firstName("mohsen").build());
        api.save(passengers);
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }
}
