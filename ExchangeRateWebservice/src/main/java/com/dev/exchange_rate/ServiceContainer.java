package com.dev.exchange_rate;

import com.dev.exchange_rate.controller.ExchangeRateController;
import com.dev.exchange_rate.repository.*;
import com.dev.exchange_rate.helper.file_reader.PropertiesReader;

public class ServiceContainer {

    public ExchangeRateController exchangeRateController(){
        return new ExchangeRateController(exchangeRateRepository());
    }

    private ExchangeRateRepository exchangeRateRepository() {
        return new MySQLExchangeRateRepository(getConnection());
    }

    private DbConnection getConnection() {
        return new MySQLConnectionGateway(configurationConnection());
    }

    private ConnectionConfiguration configurationConnection() {
        PropertiesReader propertiesReader = getPropertiesReader();
        return new ConnectionConfigurationImpl(propertiesReader);
    }

    private PropertiesReader getPropertiesReader() {
        return new PropertiesReader("db_config.properties");
    }
}
