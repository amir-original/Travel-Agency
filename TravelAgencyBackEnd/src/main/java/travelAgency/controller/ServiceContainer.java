package travelAgency.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.dao.api.ExchangeRateApi;
import travelAgency.dao.database.db_config.mysql.DbConnection;
import travelAgency.dao.database.db_config.ConnectionConfiguration;
import travelAgency.dao.database.db_config.ConnectionConfigurationImpl;
import travelAgency.dao.database.db_config.mysql.MySQLDbConnection;
import travelAgency.dao.database.flight.FlightRepositoryImpl;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.passenger.PassengerRepositoryImpl;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.reservation.ReservationListRepositoryImpl;
import travelAgency.domain.rate.currency.Currency;
import travelAgency.helper.*;
import travelAgency.services.currency_conversion.CurrencyConverter;
import travelAgency.services.currency_conversion.ExchangeRateProvider;
import travelAgency.services.currency_conversion.ExchangeRates;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.ReservationNumberImpl;

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
