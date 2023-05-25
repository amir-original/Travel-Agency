package com.dev.exchange_rate.exceptions;

import com.dev.exchange_rate.exceptions.ExchangeRateNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExchangeRateNotFoundExceptionMapper implements ExceptionMapper<ExchangeRateNotFoundException> {


    @Override
    public Response toResponse(ExchangeRateNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
