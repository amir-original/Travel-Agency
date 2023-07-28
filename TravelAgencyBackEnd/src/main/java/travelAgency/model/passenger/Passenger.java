package travelAgency.model.passenger;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record Passenger(@NotNull PassengerId passengerId,
                        @NotNull FullName fullName,
                        @NotNull Birthdate birthdate,
                        @NotNull ResidentialAddress residential,
                        @NotNull PhoneNumber phoneNumber) {

    public boolean canMatchWith(String firstName, LocalDate birthdate) {
        Birthdate searchBirthdate = Birthdate.of(birthdate);
        
        return fullName().hasSameFirstName(firstName)
                && birthdate().equals(searchBirthdate);
    }

    public String getId() {
        return passengerId.getId();
    }
}
