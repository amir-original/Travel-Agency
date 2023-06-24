package travelAgency.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestHandler implements HttpClient {

    private final Gson gson;

    private HttpRequest.Builder method;

    public HttpRequestHandler() {
        this.gson = new Gson();
    }

    public HttpRequestHandler(Gson gson) {
        this.gson = gson;
    }

    @Override
    public HttpRequestHandler target(String baseUri) {
        try {
            requestBuilder().uri(new URI(baseUri));
            return this;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("your base uri is incorrect! " + baseUri);
        }
    }

    @Override
    public <T> T GET(Class<T> responseType) {
        final HttpRequest httpRequest = method.GET().header("content-type","application/json").build();
        final String body = getHttpResponse(httpRequest).body();
        return getResponse(body, responseType);
    }

    public <T> T getResponse(String json, Type responseType) {
        return gson.fromJson(json, responseType);
    }

    public HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            return java.net.http.HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
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
