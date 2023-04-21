package travelAgency.helper;

public interface HttpRequestApi {

    HttpRequestHandler target(String baseUri);
    <T> T GET(Class<T> responseType);
}
