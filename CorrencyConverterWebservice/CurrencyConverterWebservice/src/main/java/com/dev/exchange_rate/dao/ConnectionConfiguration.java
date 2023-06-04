package com.dev.exchange_rate.dao;

public interface ConnectionConfiguration {
    String url();
    String username();
    String password();
    String getConfig(String key);
}
