package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.CouldNotFoundPassenger;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.CouldNotSavePassenger;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerRepositoryImpl;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.passenger.PassengerId;
import travelAgency.use_case.fake.FakePassenger;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PassengerRepositoryShould {

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
    void enroll_a_passenger_with_valid_information_without_throwing_any_exception() {
        final Passenger passenger = FakePassenger.passenger()
                .withId(PassengerId.withId("8787684512")).build();

        api.enroll(passenger);

        final Optional<Passenger> fetchedPassenger = api.findPassengerById(passenger.getId());

       fetchedPassenger.orElseThrow(CouldNotFoundPassenger::new);

        assertThat(passenger).isEqualTo(fetchedPassenger.get());
    }

    @Test
    void not_enroll_a_duplicate_passenger() {
        final Passenger passenger = fakePassenger.findPassengerById("4444556622").get();
        api.enroll(passenger);

        assertThatExceptionOfType(CouldNotSavePassenger.class)
                .isThrownBy(()-> api.enroll(passenger));
    }

    @Test
    void not_find_any_passenger_when_passenger_passenger_number_doesnt_exist() {
        assertThatExceptionOfType(CouldNotFoundPassenger.class)
                .isThrownBy(()->api.findPassengerById("notFound"));
    }

    @Test
    void fetch_all_passengers() {
        insertMultiplePassengers();

        assertThat(api.getPassengers().size()).isEqualTo(4);
    }

    private void insertMultiplePassengers() {
        api.enroll(fakePassenger.getPassengers());
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }
}
