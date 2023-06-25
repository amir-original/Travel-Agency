package com.dev.exchange_rate.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CurrencyNotFoundExceptionMapper implements ExceptionMapper<CouldNotFoundCurrency> {


    @Override
    public Response toResponse(CouldNotFoundCurrency exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
