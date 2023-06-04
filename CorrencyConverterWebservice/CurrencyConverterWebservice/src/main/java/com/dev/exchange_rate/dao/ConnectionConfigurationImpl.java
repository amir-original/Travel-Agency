package com.dev.exchange_rate.dao;

import com.dev.exchange_rate.helper.file_reader.PropertiesReader;

public class ConnectionConfigurationImpl implements ConnectionConfiguration {

    private final PropertiesReader propertiesReader;

    public ConnectionConfigurationImpl(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }


    @Override
    public String url() {
        return getConfig("app.datasource.url");
    }

    @Override
    public String username() {
        return getConfig("app.datasource.username");
    }

    @Override
    public String password() {
        return getConfig("app.datasource.password");
    }

    @Override
    public String getConfig(String key) {
        return propertiesReader.getProperty(key);
    }
}
