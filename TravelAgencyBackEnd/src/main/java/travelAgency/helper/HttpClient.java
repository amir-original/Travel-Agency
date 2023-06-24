package travelAgency.helper;

public interface HttpClient {
    HttpRequestHandler target(String baseUri);
    <T> T GET(Class<T> responseType);
}
