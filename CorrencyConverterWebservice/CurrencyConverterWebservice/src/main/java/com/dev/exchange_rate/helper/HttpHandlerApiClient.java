package com.dev.exchange_rate.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHandlerApiClient implements HttpApiClient {

    private final Gson gson;

    private HttpRequest.Builder method;

    public HttpHandlerApiClient(Gson gson) {
        this.gson = gson;
    }

    public HttpHandlerApiClient() {
        this.gson = new Gson();
    }

    @Override
    public HttpHandlerApiClient target(String baseUri) {
        try {
            requestBuilder().uri(new URI(baseUri));
            return this;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("your base uri is incorrect! " + baseUri);
        }
    }

    @Override
    public <T> T GET(Class<T> responseType) {
        final HttpRequest httpRequest = method.GET().build();
        final String body = getHttpResponse(httpRequest).body();
        return getResponse(body, responseType);
    }

    @Override
    public HttpResponse<String> POST(String jsonData) {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(jsonData);
        HttpRequest request = method.header("content-type","application/json").POST(bodyPublisher).build();
        return getHttpResponse(request);
    }

    public <T> T getResponse(String json, Type responseType) {
        return gson.fromJson(json, responseType);
    }

    public HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> build() {
        HttpRequest request = method.build();
        return getHttpResponse(request);
    }

    private HttpRequest.Builder requestBuilder() {
        method = HttpRequest.newBuilder();
        return method;
    }
}
