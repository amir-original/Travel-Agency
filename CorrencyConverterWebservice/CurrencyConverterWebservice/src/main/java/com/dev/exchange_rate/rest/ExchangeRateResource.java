package com.dev.exchange_rate.rest;

import com.dev.exchange_rate.ServiceContainer;
import com.dev.exchange_rate.controller.ExchangeRateController;
import com.dev.exchange_rate.domain.ExchangeRate;
import com.dev.exchange_rate.domain.ExchangeRateDto;
import com.dev.exchange_rate.exceptions.CurrencyNotFoundException;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/base_currency")
public class ExchangeRateResource {

    private final ExchangeRateController exchangeRateService;

    public ExchangeRateResource() {
        ServiceContainer serviceContainer = new ServiceContainer();
        this.exchangeRateService = serviceContainer.exchangeRateController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{base_currency}")
    public Response getExchangeRates(@PathParam("base_currency") String baseCurrency)
            throws ExchangeRateNotFoundException, CurrencyNotFoundException {
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