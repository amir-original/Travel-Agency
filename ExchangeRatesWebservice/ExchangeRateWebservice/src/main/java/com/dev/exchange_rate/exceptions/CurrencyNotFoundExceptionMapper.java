package com.dev.exchange_rate.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CurrencyNotFoundExceptionMapper implements ExceptionMapper<CurrencyNotFoundException> {


    @Override
    public Response toResponse(CurrencyNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
