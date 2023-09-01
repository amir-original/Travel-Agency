package travelAgency.infrastructure.persistence.jdbc_mysql.passenger;

public class CouldNotDropPassengerTable extends RuntimeException{

    private CouldNotDropPassengerTable(String message) {
        super(message);
    }

    public static CouldNotDropPassengerTable becauseOf(String reason)
    throws  CouldNotDropPassengerTable{
        return new CouldNotDropPassengerTable(reason);
    }
}
