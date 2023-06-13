package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.vo.Phone;

import java.time.LocalDate;

public record Passenger(@NotNull String id,
                        @NotNull String fName,
                        @NotNull String lName,
                        @NotNull LocalDate birthday,
                        @NotNull String city,
                        @NotNull String address,
                        @NotNull String zipcode,
                        Phone phoneNumber) {

    public Passenger(@NotNull String id,
                     @NotNull String fName,
                     @NotNull String lName,
                     @NotNull LocalDate birthday,
                     @NotNull String city,
                     @NotNull String address,
                     @NotNull String zipcode,
                     Phone phoneNumber) {
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
