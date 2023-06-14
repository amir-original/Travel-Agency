package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record Passenger(@NotNull PassengerId passengerId,
                        @NotNull FullName fullName,
                        @NotNull LocalDate birthday,
                        @NotNull ResidentialAddress residential,
                        @NotNull PhoneNumber phoneNumber) {

    public boolean canMatchWith(String firstName, LocalDate birthday) {
        return fullName().getFirstName().equals(firstName)
                && birthday().equals(birthday);
    }

    public String getId() {
        return passengerId.getId();
    }
}
