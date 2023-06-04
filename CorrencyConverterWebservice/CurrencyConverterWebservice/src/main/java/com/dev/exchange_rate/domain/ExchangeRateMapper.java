package com.dev.exchange_rate.domain;

public class ExchangeRateMapper {

    public ExchangeRate toEntity(ExchangeRateDto exchangeRateDto){
        return new ExchangeRateBuilder()
                .setBaseCurrency(exchangeRateDto.getBaseCurrency())
                .setDate(exchangeRateDto.getDate())
                .setRates(exchangeRateDto.getRates())
                .createExchangeRate();
    }

    public ExchangeRateDto toViewDto(ExchangeRate exchangeRate){
        return new ExchangeRateDtoBuilder()
                .setBaseCurrency(exchangeRate.getBaseCurrency())
                .setDate(exchangeRate.getDate())
                .setRates(exchangeRate.getRates())
                .createExchangeRateDto();
    }
}
