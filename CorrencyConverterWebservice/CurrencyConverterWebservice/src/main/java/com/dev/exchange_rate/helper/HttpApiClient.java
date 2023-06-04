package com.dev.exchange_rate.helper;

import java.net.http.HttpResponse;

public interface HttpApiClient {
    HttpHandlerApiClient target(String baseUri);
    <T> T GET(Class<T> responseType);

    HttpResponse<String> POST(String jsonData);
}
