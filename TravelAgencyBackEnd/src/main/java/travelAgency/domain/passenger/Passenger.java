package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.*;

import java.time.LocalDate;
import java.util.regex.Pattern;

public record Passenger(@NotNull String id,
                        @NotNull String fName,
                        @NotNull String lName,
                        @NotNull LocalDate birthday,
                        @NotNull String city,
                        @NotNull String address,
                        @NotNull String zipcode,
                        @NotNull String phoneNumber) {

    public Passenger(@NotNull String id,
                     @NotNull String fName,
                     @NotNull String lName,
                     @NotNull LocalDate birthday,
                     @NotNull String city,
                     @NotNull String address,
                     @NotNull String zipcode,
                     @NotNull String phoneNumber) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        final PassengerValidator passengerValidator = new PassengerValidator(this);
        passengerValidator.validatePassengerInformation();
    }

    public boolean canMatchWith(String passengerFirstName, LocalDate PassengerBirthday) {
        return fName().equals(passengerFirstName) && birthday().equals(PassengerBirthday);
    }

    public String fullName() {
        return fName + " " + lName;
    }
}
