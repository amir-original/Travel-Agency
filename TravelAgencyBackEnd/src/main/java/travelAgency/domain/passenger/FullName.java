package travelAgency.domain.passenger;

import java.util.Objects;

public class FullName {
    private final String firstName;
    private final String lastName;

    private FullName(String fName, String lName) {
        if (isNull(fName, lName)) {
            throw new IllegalArgumentException("firstName and lastName should not be null!");
        }
        if (fName.isBlank() || lName.isBlank())
            throw new IllegalArgumentException("the name must not be null or empty!");

        this.firstName = fName;
        this.lastName = lName;
    }

    private boolean isNull(String firstName, String lastName) {
        return Objects.isNull(firstName) || Objects.isNull(lastName);
    }

    public static FullName of(String firstName, String lastName) {
        return new FullName(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toText() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName) && Objects.equals(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "FullName{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
