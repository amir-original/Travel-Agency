package com.dev.exchange_rate.repository;

public class ExchangeRateSQL {

    public static final String SELECT_ALL_EXCHANGE_RATES
            = "select * from exchange_rate";
    public static final String SELECT_EXCHANGE_RATE
            = "select * from exchange_rate where base_currency=?";
    public static final String INSERT_NEW_EXCHANGE_RATE
            = "INSERT INTO exchange_rate(id,base_currency,date,rates)" + " values (?,?,?,?)";
    public static final String DELETE_EXCHANGE_RATE
            = "delete from exchange_rate where base_currency=?";
    public static final String CREATE_EXCHANGE_RATE_TABLE_IF_NOT_EXIST = """
            CREATE TABLE IF NOT EXISTS exchange_rate (
                passengerId INT PRIMARY KEY AUTO_INCREMENT,
                base_currency VARCHAR(255),
                date DATE,
                rates json
            );
            """;
}
