package travelAgency.infrastructure.network;

import com.google.gson.Gson;
import travelAgency.infrastructure.ServiceContainer;

public class Response {
    private final int statusCode;
    private final String body;
    private final Gson gson;

    private Response(int statusCode, String body) {
        final ServiceContainer serviceContainer = new ServiceContainer();
        this.statusCode = statusCode;
        this.body = body;
        this.gson = serviceContainer.getGson();
    }

    public static Response of(int statusCode, String body) {
        return new Response(statusCode, body);
    }

    public int status() {
        return statusCode;
    }

    public <T> T readEntity(Class<T> entityType) {
        return gson.fromJson(body, entityType);
    }
}
