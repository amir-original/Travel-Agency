package com.dev.exchange_rate.rest;

import com.dev.exchange_rate.ServiceContainer;
import com.dev.exchange_rate.controller.ExchangeRateController;
import com.dev.exchange_rate.domain.Currency;
import com.dev.exchange_rate.dto.ExchangeRateDto;
import com.dev.exchange_rate.exceptions.CouldNotFoundCurrency;
import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/base_currency")
public class ExchangeRateResource {

    private final ExchangeRateController controller;

    public ExchangeRateResource() {
        ServiceContainer serviceContainer = new ServiceContainer();
        this.controller = serviceContainer.exchangeRateController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{base_currency}")
    public Response retrieveExchangeRates(@PathParam("base_currency") Currency baseCurrency)
            throws ExchangeRateNotFoundException, CouldNotFoundCurrency {
        ExchangeRateDto exchangeRateDto = controller.retrieveExchangeRate(baseCurrency);

        return Response.ok(exchangeRateDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addExchangeRate(ExchangeRateDto exchangeRateDto) {
        controller.addExchangeRate(exchangeRateDto);
        return Response.status(Response.Status.CREATED).build();
    }
}