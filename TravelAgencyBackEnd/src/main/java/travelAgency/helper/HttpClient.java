package travelAgency.helper;

import java.net.URI;

public interface HttpClient {
    Response get(URI uri);
}
