package com.dev.exchange_rate.dao;

import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.helper.file_reader.JsonMyFileReader;
import com.dev.exchange_rate.helper.file_reader.MyFileReader;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class JsonExchangeRateDao implements ExchangeRateDao {
    private static final String FILE_NAME = "rates.json";
    private final MyFileReader fileReader;

    public JsonExchangeRateDao() {
        fileReader = new JsonMyFileReader();
    }

    @Override
    public List<ExchangeRate> retrieveExchangeRates() {
        Type type = new TypeToken<List<ExchangeRate>>(){}.getType();
        return fileReader.read(FILE_NAME,type);
    }

    @Override
    public Optional<ExchangeRate> retrieveExchangeRate(Currency baseCurrency) {
        return retrieveExchangeRates().stream()
                .filter(exchangeRate -> exchangeRate.hasSameBaseCurrency(baseCurrency))
                .findFirst();
    }

}
