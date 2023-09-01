package travelAgency.infrastructure.persistence.jdbc_mysql.flight;

public class CouldNotFoundFlight extends RuntimeException {

    public CouldNotFoundFlight() {
    }

    public CouldNotFoundFlight(String message) {
        super(message);
    }

    public static CouldNotFoundFlight withFlightNumber(String flightNumber)
            throws CouldNotFoundFlight
    {
        return new CouldNotFoundFlight("Could not Found Flight" +
                " With {flightNumber} :" + flightNumber);
    }
}
