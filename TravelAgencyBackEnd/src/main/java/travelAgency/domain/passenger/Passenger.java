package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;

import java.time.LocalDate;

public record Passenger(@NotNull PassengerId passengerId,
                        @NotNull FullName fullName,
                        @NotNull LocalDate birthday,
                        @NotNull String city,
                        @NotNull String address,
                        @NotNull String zipcode,
                        @NotNull Phone phoneNumber) {

    public Passenger(@NotNull PassengerId passengerId,
                     @NotNull FullName fullName,
                     @NotNull LocalDate birthday,
                     @NotNull String city,
                     @NotNull String address,
                     @NotNull String zipcode,
                     @NotNull Phone phoneNumber) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.birthday = birthday;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
        final PassengerValidator passengerValidator = new PassengerValidator(this);
        passengerValidator.validatePassengerInformation();
    }

    public boolean canMatchWith(String passengerFirstName, LocalDate PassengerBirthday) {
        return fullName().getFirstName().equals(passengerFirstName) && birthday().equals(PassengerBirthday);
    }

    public String getId() {
        return passengerId.getId();
    }
}
