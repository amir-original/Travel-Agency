package travelAgency.dao.database.db_config;

public interface ConnectionConfiguration {
    String url();
    String driver();
    String username();
    String password();
    String getConfig(String key);
}
