package travelAgency.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestHandler implements HttpApi {

    private String uri;
    private final Gson gson = new Gson();

    private HttpRequest.Builder method;

    @Override
    public HttpRequestHandler target(String baseUri) {
        uri = baseUri;
        try {
            builder().uri(new URI(uri));
            return this;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpRequestHandler path(String endpoint) {
        target(uri + endpoint);
        return this;
    }

    @Override
    public HttpRequestHandler GET() {
        method = method.GET();
        return this;
    }

    @Override
    public HttpRequestHandler POST(Object entity) {
        method = method.POST(bodyPublisher(entity));
        return this;
    }

    @Override
    public HttpRequestHandler PUT(Object entity) {
        method.PUT(bodyPublisher(entity));
        return this;
    }

    @Override
    public HttpRequestHandler DELETE() {
        method = method.DELETE();
        return this;
    }

    @Override
    public HttpRequestHandler mediaType(String type) {
        method = method.header("content-type", type);
        return this;
    }

    @Override
    public HttpRequestHandler header(String key,String value) {
        method = method.header(key, value);
        return this;
    }

    public <T> T getResponse(HttpResponse<String> response, Type responseType) {
        return gson.fromJson(response.body(), responseType);
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

    private String toJson(Object entity) {
        return gson.toJson(entity);
    }

    private HttpRequest.BodyPublisher bodyPublisher(Object entity) {
        return HttpRequest.BodyPublishers.ofString(toJson(entity));
    }

    private HttpRequest.Builder builder() {
        method = HttpRequest.newBuilder();
        return method;
    }


}
