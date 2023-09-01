package travelAgency.infrastructure.persistence.jdbc_mysql.flight;

public class CouldNotLoadFlights extends RuntimeException{

    public CouldNotLoadFlights() {
    }

    public CouldNotLoadFlights(String message) {
        super(message);
    }

    public static CouldNotLoadFlights becauseOf(String message)
    throws CouldNotLoadFlights{
        return new CouldNotLoadFlights(message);
    }
}
