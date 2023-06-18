package travelAgency.exceptions;

public class CouldNotDeleteFlight extends RuntimeException{
    public CouldNotDeleteFlight() {
    }

    public CouldNotDeleteFlight(String message) {
        super(message);
    }

    public static CouldNotDeleteFlight withFlightNumber(String flightNumber)
        throws CouldNotDeleteFlight
    {
        return new CouldNotDeleteFlight("Could not Delete flight with this flight Number :"+flightNumber);
    }
}
