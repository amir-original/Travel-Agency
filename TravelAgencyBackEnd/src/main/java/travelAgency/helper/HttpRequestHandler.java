package travelAgency.helper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestHandler implements HttpClient {


    public HttpRequestHandler() {
    }

    @Override
    public Response get(URI uri) {
        final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("content-type","application/json")
                .build();

        final HttpResponse<String> httpResponse = getHttpResponse(httpRequest);
        return Response.of(httpResponse.statusCode(),httpResponse.body());
    }

    private HttpResponse<String> getHttpResponse(HttpRequest request) {
        try {
            return java.net.http.HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
