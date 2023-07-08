package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.model.passenger.PassengerId;
import travelAgency.exceptions.CouldNotFoundPassenger;
import travelAgency.exceptions.CouldNotSavePassenger;
import travelAgency.exceptions.PassengerNotFoundException;
import travelAgency.model.passenger.Passenger;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerRepositoryImpl;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.use_case.fake.FakePassenger;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class PassengerRepositoryShould {

    private PassengerRepositoryImpl api;
    private FakePassenger fakePassenger;

    @BeforeEach
    void setUp() {
        final ConnectionConfiguration configuration =
                ConnectionConfigurationImpl.of(PropertiesReader.of("test-db-config"));
        api = new PassengerRepositoryImpl(new MySQLDbConnection(configuration));
        fakePassenger = new FakePassenger();
    }

    @Test
    void save_passenger_info_and_fetch_passenger_with_id() {
        final Passenger passenger = FakePassenger.passenger()
                .withId(PassengerId.withId("8787684512")).build();

        api.save(passenger);

        final Optional<Passenger> fetchedPassenger = api.findPassengerById(passenger.getId());

       fetchedPassenger.orElseThrow(PassengerNotFoundException::new);

        assertThat(passenger).isEqualTo(fetchedPassenger.get());
    }

    @Test
    void fetch_all_passengers() {
        insertMultiplePassengers();

        assertThat(api.getPassengers().size()).isEqualTo(4);
    }

    @Test
    void throw_CouldNotSavePassenger_when_tried_to_save_duplicate_passenger() {
        final Passenger passenger = fakePassenger.findPassengerById("4444556622").get();
        api.save(passenger);

        assertThatExceptionOfType(CouldNotSavePassenger.class)
                .isThrownBy(()-> api.save(passenger));
    }

    @Test
    void throw_CouldNotFindPassenger_when_provide_not_Exist_passenger_id() {
        assertThatExceptionOfType(CouldNotFoundPassenger.class)
                .isThrownBy(()->api.findPassengerById("notFound"));
    }

    private void insertMultiplePassengers() {
        api.save(fakePassenger.getPassengers());
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }
}
