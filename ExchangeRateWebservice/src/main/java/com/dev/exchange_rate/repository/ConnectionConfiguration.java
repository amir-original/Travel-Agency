package com.dev.exchange_rate.repository;

public interface ConnectionConfiguration {
    String url();
    String username();
    String password();
    String driver();
    String getConfig(String key);
}
