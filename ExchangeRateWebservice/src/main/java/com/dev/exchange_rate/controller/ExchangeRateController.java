package com.dev.exchange_rate.controller;

import com.dev.exchange_rate.ServiceContainer;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.exceptions.CouldNotFoundCurrency;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import com.dev.exchange_rate.service.ExchangeRateService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/latest")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController() {
        ServiceContainer serviceContainer = new ServiceContainer();
        this.exchangeRateService = serviceContainer.exchangeRateService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{base_currency}")
    public Response retrieveExchangeRates(@PathParam("base_currency") Currency baseCurrency)
            throws ExchangeRateNotFoundException, CouldNotFoundCurrency {
        ExchangeRateDto exchangeRateDto = exchangeRateService.retrieveExchangeRate(baseCurrency);

        return Response.ok(exchangeRateDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addExchangeRate(ExchangeRateDto exchangeRateDto) {
        exchangeRateService.addExchangeRate(exchangeRateDto);
        return Response.status(Response.Status.CREATED).build();
    }
}