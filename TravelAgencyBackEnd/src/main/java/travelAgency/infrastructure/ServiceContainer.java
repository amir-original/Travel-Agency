package travelAgency.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import travelAgency.application.exchange_rates.ExchangeRateDAO;
import travelAgency.infrastructure.io.PropertiesReader;
import travelAgency.infrastructure.network.HttpClient;
import travelAgency.infrastructure.network.HttpRequestHandler;
import travelAgency.infrastructure.user_interface.web.api.ExchangeRateApi;
import travelAgency.infrastructure.db.DbConnection;
import travelAgency.infrastructure.db.ConnectionConfiguration;
import travelAgency.infrastructure.db.ConnectionConfigurationImpl;
import travelAgency.infrastructure.db.MySQLDbConnection;
import travelAgency.infrastructure.persistence.jdbc_mysql.flight.FlightRepositoryImpl;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.passenger.PassengerRepositoryImpl;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.infrastructure.persistence.jdbc_mysql.reservation.ReservationRepositoryImpl;
import travelAgency.model.rate.currency.Currency;
import travelAgency.infrastructure.libraries.*;
import travelAgency.infrastructure.user_interface.web.controller.*;
import travelAgency.application.exchange_rates.CurrencyConverter;
import travelAgency.application.exchange_rates.ExchangeRateProvider;
import travelAgency.application.exchange_rates.ExchangeRates;
import travelAgency.application.flight.FlightAvailability;
import travelAgency.application.flight.FlightListServiceImpl;
import travelAgency.application.reservation.ReservationListServiceImpl;
import travelAgency.application.reservation.ReservationNumberImpl;

import java.time.LocalDate;

public class ServiceContainer {

    public ReservationOperations reservationController(){
        return new ReservationController(
                reservationsRepository(),
                passengerRepository(),
                getFlightAvailability(),
                getTicketNumberGenerator()
                ,getFlightListService());
    }

    public ExchangeRateOperations exchangeRateController(){
        return new ExchangeRateController(getExchangeRateConverter());
    }

    public FlightOperations flightController() {
        return new FlightController(getFlightListService(), getReservationListService());
    }

    private PassengerRepository passengerRepository() {
        return new PassengerRepositoryImpl(getConnection());
    }

    private ReservationRepository reservationsRepository() {
        return new ReservationRepositoryImpl(getConnection());
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
    private ReservationNumberImpl getTicketNumberGenerator() {
        return new ReservationNumberImpl();
    }

    @NotNull
    private DbConnection getConnection() {
        return new MySQLDbConnection(getConfiguration());
    }

    private ConnectionConfiguration getConfiguration() {
        return ConnectionConfigurationImpl.of(getPropertiesReader());
    }

    private PropertiesReader getPropertiesReader() {
        return PropertiesReader.of("db-config");
    }

    @NotNull
    private CurrencyConverter getExchangeRateConverter() {
        return new CurrencyConverter(getExchangeRateService());
    }

    @NotNull
    private ExchangeRateProvider getExchangeRateService() {
        return new ExchangeRates(getExchangeRateDAO());
    }

    @NotNull
    public ExchangeRateDAO getExchangeRateDAO() {
        return new ExchangeRateApi(htppClientApi());
    }

    private HttpClient htppClientApi() {
        return new HttpRequestHandler();
    }

    @NotNull
    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Currency.class, new CurrencySerializer())
                .setPrettyPrinting().create();
    }
}
