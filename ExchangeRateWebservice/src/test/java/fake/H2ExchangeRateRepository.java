package fake;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.helper.CurrencySerializer;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.dev.exchange_rate.repository.DbConnection;
import com.dev.exchange_rate.repository.DuplicatePrimaryKeyException;
import com.dev.exchange_rate.repository.ExchangeRateRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.exchange_rate.repository.ExchangeRateSQL.*;

public class H2ExchangeRateRepository implements ExchangeRateRepository {
    private final DbConnection connection;
    private final Gson gson;


    public H2ExchangeRateRepository(DbConnection connection) {
        this.connection = connection;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,new LocalDateTypeAdapter())
                .registerTypeAdapter(Currency.class,new CurrencySerializer())
                .setPrettyPrinting().create();
        createTableIfNotExist();
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        List<ExchangeRate> result = new LinkedList<>();
        try (PreparedStatement query = createQuery(SELECT_ALL_EXCHANGE_RATES)) {
            ResultSet resultSet = query.executeQuery();
            populateExchangeRates(result, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void populateExchangeRates(List<ExchangeRate> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            ExchangeRate exchangeRate = createExchangeRate(resultSet);
            result.add(exchangeRate);
        }
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency) {
        ExchangeRate result;
        try (PreparedStatement query = createQuery(SELECT_EXCHANGE_RATE)) {
            query.setString(1, baseCurrency.name());
            ResultSet resultSet = query.executeQuery();
            result = createExchangeRateIfExist(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    private ExchangeRate createExchangeRateIfExist(ResultSet resultSet) throws SQLException {
        ExchangeRate result = new ExchangeRate();
        if (resultSet.next()) {
            result =  createExchangeRate(resultSet);
        }
        return result;
    }
    @Override
    public void addExchangeRate(ExchangeRate exchangeRate) {
        try (PreparedStatement query = createQuery(INSERT_NEW_EXCHANGE_RATE)) {
            setQueryParameter(exchangeRate, query);
            query.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatePrimaryKeyException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setQueryParameter(ExchangeRate exchangeRate, PreparedStatement query) throws SQLException {
        LocalDate date = exchangeRate.getDate();
        Date ld = Date.valueOf(date);
        query.setString(1, exchangeRate.getBaseCurrency().name());
        Map<Currency, Double> rates = exchangeRate.getRates();
        query.setDate(2, ld);
        String jsonRates = gson.toJson(rates);
        query.setString(3, jsonRates);
    }

    @Override
    public void remove(ExchangeRate exchangeRate) {
        try (PreparedStatement query = createQuery(DELETE_EXCHANGE_RATE)) {
            query.setInt(1, exchangeRate.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement createQuery(String sql) throws SQLException {
        return connection.currentConnection().prepareStatement(sql);
    }

    private ExchangeRate createExchangeRate(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String baseCurrency = resultSet.getString("base_currency");
        Currency currency = Currency.valueOf(baseCurrency);
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String rates = resultSet.getString("rates");
        Map<Currency, Double> mapRate = getMapRate(rates);
        return new ExchangeRateBuilder()
                .setId(id)
                .setBaseCurrency(currency)
                .setDate(date)
                .setRates(mapRate)
                .createExchangeRate();
    }

    private Map<Currency, Double> getMapRate(String rates) {
        Object json = gson.fromJson(rates, Object.class);
        TypeToken<Map<Currency, Double>> type = new TypeToken<>() {};
        return gson.fromJson((String) json, type.getType());
    }

    public void createTableIfNotExist() {
        try (PreparedStatement preparedStatement = createQuery(CREATE_EXCHANGE_RATE_TABLE_IF_NOT_EXIST)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
