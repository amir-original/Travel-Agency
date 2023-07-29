package travelAgency.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateDAO;
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
import travelAgency.model.rate.Currency;
import travelAgency.infrastructure.libraries.*;
import travelAgency.infrastructure.user_interface.web.controller.*;
import travelAgency.infrastructure.libraries.currency_converter.CurrencyConverter;
import travelAgency.infrastructure.libraries.currency_converter.ExchangeRateProvider;
import travelAgency.infrastructure.libraries.currency_converter.FindExchangeRate;
import travelAgency.application.use_case.FindFlight;
import travelAgency.application.use_case.SearchReservation;
import travelAgency.infrastructure.persistence.jdbc_mysql.reservation.ReservationNumberImpl;

import java.time.LocalDate;

public class ServiceContainer {

    public ReservationOperations reservationController(){
        return new ReservationController(
                reservationsRepository(),
                passengerRepository(),
                FindReservationService(),
                getTicketNumberGenerator()
                ,getFlightListService());
    }

    public ExchangeRateOperations exchangeRateController(){
        return new ExchangeRateController(getExchangeRateConverter());
    }

    public FlightOperations flightController() {
        return new FindFlightController(getFlightListService(), FindReservationService());
    }

    private PassengerRepository passengerRepository() {
        return new PassengerRepositoryImpl(getConnection());
    }

    private ReservationRepository reservationsRepository() {
        return new ReservationRepositoryImpl(getConnection());
    }

    @NotNull
    private SearchReservation FindReservationService() {
        return new SearchReservation(reservationsRepository(), getFlightListService());
    }

    @NotNull
    private FindFlight getFlightListService() {
        return new FindFlight(getFlightRepository());
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
        return new FindExchangeRate(getExchangeRateDAO());
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
