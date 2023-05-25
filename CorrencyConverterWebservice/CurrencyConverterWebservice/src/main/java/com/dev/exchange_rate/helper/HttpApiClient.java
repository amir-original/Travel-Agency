package com.dev.exchange_rate.helper;

public interface HttpApiClient {
    HttpHandlerApiClient target(String baseUri);
    <T> T GET(Class<T> responseType);
}
