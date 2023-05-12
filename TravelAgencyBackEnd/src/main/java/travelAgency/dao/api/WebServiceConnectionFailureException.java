package travelAgency.dao.api;

public class WebServiceConnectionFailureException extends RuntimeException {

    public WebServiceConnectionFailureException(String message) {
        super(message);
    }
}
