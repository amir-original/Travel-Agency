package travelAgency.helper;

public interface HttpApiClient {
    HttpRequestHandler target(String baseUri);
    <T> T GET(Class<T> responseType);
}
