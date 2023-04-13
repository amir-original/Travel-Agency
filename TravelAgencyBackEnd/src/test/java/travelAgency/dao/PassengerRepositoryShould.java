package travelAgency.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.NotFoundAnyPassengerException;
import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.db.mysq.MySQLDbConnection;
import travelAgency.repository.passenger.PassengerRepositoryImpl;
import travelAgency.use_case.fake.FakePassenger;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class PassengerRepositoryShould {

    private PassengerRepositoryImpl api;
    private FakePassenger fakePassenger;

    @BeforeEach
    void setUp() {
        api = new PassengerRepositoryImpl(new MySQLDbConnection());
        fakePassenger = new FakePassenger();
    }

    @Test
    void save_passenger_info_and_fetch_passenger_with_id() {
        final Passenger passenger = fakePassenger.findPassengerById("se478").get();

        api.save(passenger);

        final Optional<Passenger> fetchedPassenger = api.findPassengerById(passenger.id());

        if (fetchedPassenger.isEmpty()) {
            fail("not found any findPassengerById with this id in db");
        }

        assertThat(passenger).isEqualTo(fetchedPassenger.get());
    }

    @Test
    void fetch_all_passengers() {
        insertMultiplePassengers();

        assertThat(api.getPassengers().size()).isEqualTo(3);
    }

    @Test
    void throw_NotFoundAnyPassengerException_when_passenger_is_not_found() {
        final Optional<Passenger> fetchedPassenger = api.findPassengerById("notFound");

        assertThatExceptionOfType(NotFoundAnyPassengerException.class)
                .isThrownBy(() -> fetchedPassenger.orElseThrow(NotFoundAnyPassengerException::new));

    }

    private void insertMultiplePassengers() {
        api.save(fakePassenger.getPassengers());
    }

    @AfterEach
    void tearDown() {
        api.truncate();
    }
}
