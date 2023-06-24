package travelAgency.dao.database.db_config;

import travelAgency.helper.PropertiesReader;

import static java.util.Objects.requireNonNull;

public class ConnectionConfigurationImpl implements ConnectionConfiguration {

    private final PropertiesReader propertiesReader;

    private ConnectionConfigurationImpl(PropertiesReader propertiesReader) {
        this.propertiesReader = requireNonNull(propertiesReader);
    }

    public static ConnectionConfiguration of(PropertiesReader propertiesReader){
        return new ConnectionConfigurationImpl(propertiesReader);
    }

    @Override
    public String url() {
        return getConfig("app.datasource.url");
    }

    @Override
    public String driver() {
        return getConfig("app.datasource.driver");
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

    @Override
    public String toString() {
        return "ConnectionConfigurationImpl{" +
                "propertiesReader=" + propertiesReader +
                '}';
    }
}
