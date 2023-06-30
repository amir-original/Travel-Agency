package com.dev.exchange_rate;

import com.dev.exchange_rate.repository.*;
import com.dev.exchange_rate.helper.file_reader.PropertiesReader;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;

public class ServiceContainer {


    public ExchangeRateService exchangeRateService(){
        return new ExchangeRateServiceImpl(exchangeRateRepository());
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
