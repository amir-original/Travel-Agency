package travelAgency.infrastructure.persistence.jdbc_mysql.passenger;

public class CouldNotLoadPassengers extends RuntimeException{

    private CouldNotLoadPassengers(String message) {
        super(message);
    }

    public static CouldNotLoadPassengers becauseOf(String reason)
     throws CouldNotLoadPassengers{
        return new CouldNotLoadPassengers(reason);
    }
}
