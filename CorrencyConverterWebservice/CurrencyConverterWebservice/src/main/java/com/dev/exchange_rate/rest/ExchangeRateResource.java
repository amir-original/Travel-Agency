package com.dev.exchange_rate.rest;

import com.dev.exchange_rate.dao.ConnectionConfigurationImpl;
import com.dev.exchange_rate.dao.MySQLConnectionGateway;
import com.dev.exchange_rate.dao.MySQLExchangeRateRepository;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.helper.file_reader.PropertiesReader;
import com.dev.exchange_rate.service.ExchangeRateService;
import com.dev.exchange_rate.service.ExchangeRateServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/base_currency")
public class ExchangeRateResource {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateResource() {
        PropertiesReader propertiesReader = new PropertiesReader("db_config.properties");
        ConnectionConfigurationImpl configuration = new ConnectionConfigurationImpl(propertiesReader);
        MySQLConnectionGateway connection = new MySQLConnectionGateway(configuration);
        MySQLExchangeRateRepository exchangeRateRepository = new MySQLExchangeRateRepository(connection);
        this.exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{base_currency}")
    public Response getExchangeRates(@PathParam("base_currency") String baseCurrency) throws ExchangeRateNotFoundException {
        Currency currency = Currency.valueOf(baseCurrency);

        ExchangeRate exchangeRate = exchangeRateService.retrieveExchangeRate(currency);

        return Response.ok(exchangeRate).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRateService.addExchangeRate(exchangeRate);
        return Response.status(Response.Status.CREATED).build();
    }
}