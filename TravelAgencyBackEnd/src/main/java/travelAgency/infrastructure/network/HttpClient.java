package travelAgency.infrastructure.network;

import java.net.URI;

public interface HttpClient {
    Response get(URI uri);
}
