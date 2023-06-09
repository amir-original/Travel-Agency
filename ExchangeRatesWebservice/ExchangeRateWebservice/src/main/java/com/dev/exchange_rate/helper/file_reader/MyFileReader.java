package com.dev.exchange_rate.helper.file_reader;

import com.dev.exchange_rate.domain.ExchangeRate;

import java.lang.reflect.Type;
import java.util.List;

public interface MyFileReader {
    <T> T read(String fileName, Type type);
}
