package travelAgency.domain.vo;

import travelAgency.domain.passenger.Passenger;
import travelAgency.exceptions.InvalidPassengerIdException;

import java.util.Objects;

public class PassengerId {

    private final String id;

    private PassengerId(String passengerId) {
        if (passengerId == null) {
            throw new InvalidPassengerIdException("passenger passengerId should not be null!");
        }
        if (isValidLength(passengerId)){
            throw new InvalidPassengerIdException();
        }

        if (isDigits(passengerId)){
            throw new InvalidPassengerIdException();
        }

            this.id = passengerId;
    }

    private boolean isValidLength(String passengerId) {
        return passengerId.length() != 10;
    }

    private boolean isDigits(String passengerId) {
        return passengerId.matches("(\\d){9}");
    }

    public static PassengerId of(String id){
        return new PassengerId(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerId that = (PassengerId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PassengerId{" +
                "id='" + id + '\'' +
                '}';
    }
}
