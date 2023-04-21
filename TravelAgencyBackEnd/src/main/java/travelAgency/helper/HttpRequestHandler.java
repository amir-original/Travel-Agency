package travelAgency.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestHandler implements HttpRequestApi {

    private final Gson gson = new Gson();

    private HttpRequest.Builder method;

    @Override
    public HttpRequestHandler target(String baseUri) {
        try {
            requestBuilder().uri(new URI(baseUri));
            return this;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T GET(Class<T> responseType) {
        final HttpRequest httpRequest = method.GET().build();
        final String body = getHttpResponse(httpRequest).body();
        return getResponse(body,responseType);
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
