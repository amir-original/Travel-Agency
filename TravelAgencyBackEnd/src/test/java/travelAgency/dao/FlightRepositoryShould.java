package travelAgency.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.*;
import travelAgency.domain.city.City;
import travelAgency.repository.FlightRepository;
import travelAgency.repository.FlightRepositoryImpl;
import travelAgency.repository.mysq.MySQLDbConnection;

import java.sql.*;
import java.time.LocalDate;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;

public class FlightRepositoryShould {


    private static final String HOST = "jdbc:mysql://localhost:3306/travel_agency";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DELETE_SQL = "delete from flights order by id desc limit 1";
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository = new FlightRepositoryImpl(new MySQLDbConnection());
    }

    @Test
    void connect_to_db() {
        try (final Connection conn = DriverManager.getConnection(HOST, USER, PASS)) {
            if (conn == null)
                Assertions.fail("can't connect to database");
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail("can't connect to database");
        }
    }

    @Test
    void do_crud_action() {
        final Flight flight = new FlightBuilder()
                .withSerialNumber("fly580")
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(of(2023, 3, 3))
                .arrivalAt(of(2023, 3, 6))
                .withPrice(47500).build();

        flightRepository.addFlight(flight);
    }

    @AfterEach
    void tearDown() {
        try (final Connection conn = DriverManager.getConnection(HOST, USER, PASS);
        final PreparedStatement delete = conn.prepareStatement(DELETE_SQL)) {
         // delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail("can't connect to database");
        }
    }
}
