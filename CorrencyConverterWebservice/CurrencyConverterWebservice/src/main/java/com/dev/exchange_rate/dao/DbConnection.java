package com.dev.exchange_rate.dao;

import java.sql.Connection;

public interface DbConnection {

    Connection currentConnection();
}
