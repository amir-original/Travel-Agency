package com.dev.exchange_rate.repository;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.domain.ExchangeRateBuilder;
import com.dev.exchange_rate.helper.file_reader.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MySQLExchangeRateRepository implements ExchangeRateRepository {

    private final DbConnection connection;
    private final Gson gson;


    public MySQLExchangeRateRepository(DbConnection connection) {
        this.connection = connection;
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateTypeAdapter())
                .setPrettyPrinting().create();
        createTable();
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        List<ExchangeRate> result = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.currentConnection().prepareStatement("select * from exchange_rate")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ExchangeRate exchangeRate = createExchangeRate(resultSet);
                result.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency) {
        ExchangeRate result = null;
        try (PreparedStatement preparedStatement = connection.currentConnection()
                .prepareStatement("select * from exchange_rate where base_currency=?")) {
            preparedStatement.setString(1, baseCurrency.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = createExchangeRate(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void addExchangeRate(ExchangeRate exchangeRate) {
        try (PreparedStatement preparedStatement = connection.currentConnection()
                .prepareStatement("INSERT INTO exchange_rate(id,base_currency,date,rates)" +
                        " values (?,?,?,?)")) {
            preparedStatement.setInt(1, exchangeRate.getId());
            preparedStatement.setString(2, exchangeRate.getBaseCurrency().name());
            Map<Currency, Double> rates = exchangeRate.getRates();
            String jsonRates = gson.toJson(rates);
            LocalDate date = exchangeRate.getDate();
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.setString(4, jsonRates);
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatePrimaryKeyException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(ExchangeRate exchangeRate) {
        try (PreparedStatement preparedStatement = connection.currentConnection()
                .prepareStatement("delete from exchange_rate where base_currency=?")) {
            preparedStatement.setInt(1, exchangeRate.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate createExchangeRate(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String baseCurrency = resultSet.getString("base_currency");
        Currency currency = Currency.valueOf(baseCurrency);
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String rates = resultSet.getString("rates");
        TypeToken<Map<Currency, Double>> type = new TypeToken<>() {
        };
        Map<Currency, Double> mapRate = gson.fromJson(rates, type.getType());
        return new ExchangeRateBuilder().setId(id).setBaseCurrency(currency).setDate(date).setRates(mapRate).createExchangeRate();
    }

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS exchange_rate (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    base_currency VARCHAR(255),
                    date DATE,
                    rates json
                );
                """;
        try (PreparedStatement preparedStatement = connection.currentConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
