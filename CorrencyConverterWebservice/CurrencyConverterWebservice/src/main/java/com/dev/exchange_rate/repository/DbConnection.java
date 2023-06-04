package com.dev.exchange_rate.repository;

import java.sql.Connection;

public interface DbConnection {

    Connection currentConnection();
}
