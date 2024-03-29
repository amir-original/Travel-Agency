package travelAgency.infrastructure.persistence.jdbc_mysql.flight;

public class CouldNotStoreFlight extends RuntimeException {

    public CouldNotStoreFlight(String message) {
        super(message);
    }

    public static CouldNotStoreFlight becauseItIsDuplicate()
    throws CouldNotStoreFlight{
        return new CouldNotStoreFlight("Could not Store Flight Because it is Duplicate");
    }
}
