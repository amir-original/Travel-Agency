package travelAgency.infrastructure.db;

public interface ConnectionConfiguration {
    String url();
    String driver();
    String username();
    String password();
    String getConfig(String key);
}
