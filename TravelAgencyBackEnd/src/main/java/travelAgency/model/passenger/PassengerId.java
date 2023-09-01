package travelAgency.model.passenger;

import java.util.Objects;

import static java.util.Objects.isNull;

public final class PassengerId {

    private final String id;

    private PassengerId(String passengerId) {
        if (isNull(passengerId)) {
            throw new InvalidPassengerIdException("passenger passengerId should not be null!");
        }
        if (isValidLength(passengerId)){
            throw new InvalidPassengerIdException();
        }

        if (isNotValidFormat(passengerId)){
            throw new InvalidPassengerIdException();
        }

            this.id = passengerId;
    }

    private boolean isValidLength(String passengerId) {
        return passengerId.length() != 10;
    }

    private boolean isNotValidFormat(String passengerId){
        return !isValidFormat(passengerId);
    }

    private boolean isValidFormat(String passengerId) {
        return passengerId.matches("(\\d){10}");
    }

    public static PassengerId withId(String id){
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
