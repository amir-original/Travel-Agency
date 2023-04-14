package travelAgency.repository.flight;

public class SQLFlightInsertionException extends RuntimeException {

    public SQLFlightInsertionException() {
    }

    public SQLFlightInsertionException(String message) {
        super(message);
    }
}
