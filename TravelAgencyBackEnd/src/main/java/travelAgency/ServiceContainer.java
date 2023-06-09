package travelAgency;

import org.jetbrains.annotations.NotNull;
import travelAgency.controller.ReservationController;
import travelAgency.controller.ExchangeRateController;
import travelAgency.controller.FlightController;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.dao.database.db_config.DbConnection;
import travelAgency.dao.database.db_config.mysq.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.services.currency_conversion.CurrencyConverter;
import travelAgency.services.currency_conversion.ExchangeRateService;
import travelAgency.services.currency_conversion.ExchangeRateServiceImpl;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.TicketNumberGeneratorImpl;

public class ServiceContainer {

    public ReservationController reservationController(){
        return new ReservationController(
                reservationsRepository(),
                passengerRepository(),
                getFlightAvailability(),
                getTicketNumberGenerator()
                ,getFlightListService());
    }

    public ExchangeRateController exchangeRateController(){
        return new ExchangeRateController(getExchangeRateConverter());
    }

    public FlightController flightController() {
        return new FlightController(getFlightListService(), getReservationListService());
    }

    private PassengerRepository passengerRepository() {
        return new PassengerRepositoryImpl(getConnection());
    }

    private ReservationListRepository reservationsRepository() {
        return new ReservationListRepositoryImpl(getConnection());
    }

    @NotNull
    private FlightAvailability getFlightAvailability() {
        return new FlightAvailability(getReservationListService());
    }

    @NotNull
    private ReservationListServiceImpl getReservationListService() {
        return new ReservationListServiceImpl(reservationsRepository(), getFlightListService());
    }

    @NotNull
    private FlightListServiceImpl getFlightListService() {
        return new FlightListServiceImpl(getFlightRepository());
    }

    @NotNull
    private FlightRepositoryImpl getFlightRepository() {
        return new FlightRepositoryImpl(getConnection());
    }

    @NotNull
    private TicketNumberGeneratorImpl getTicketNumberGenerator() {
        return new TicketNumberGeneratorImpl();
    }

    @NotNull
    private DbConnection getConnection() {
        return new MySQLDbConnection();
    }

    @NotNull
    private CurrencyConverter getExchangeRateConverter() {
        return new CurrencyConverter(getExchangeRateService());
    }

    @NotNull
    private ExchangeRateService getExchangeRateService() {
        return new ExchangeRateServiceImpl(getExchangeRateDAO());
    }

    @NotNull
    private ExchangeRateDAO getExchangeRateDAO() {
        return new ExchangeRateDAOImpl();
    }
}
