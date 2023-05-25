package com.dev.exchange_rate.rest;

import com.dev.exchange_rate.dao.JsonExchangeRateDao;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/base_currency")
public class ExchangeRateResource {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateResource() {
        this.exchangeRateService = new ExchangeRateServiceImpl(new JsonExchangeRateDao());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{base_currency}")
    public Response getExchangeRates(@PathParam("base_currency") String baseCurrency) throws ExchangeRateNotFoundException {
        Currency currency = Currency.valueOf(baseCurrency);

        ExchangeRate exchangeRate = exchangeRateService.retrieveExchangeRate(currency);

        return Response.ok(exchangeRate).build();
    }
}