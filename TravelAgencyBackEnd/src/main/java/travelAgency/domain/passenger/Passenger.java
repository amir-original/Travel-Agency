package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;
import travelAgency.domain.vo.ResidentialAddress;

import java.time.LocalDate;

public record Passenger(@NotNull PassengerId passengerId,
                        @NotNull FullName fullName,
                        @NotNull LocalDate birthday,
                        @NotNull ResidentialAddress residential,
                        @NotNull Phone phoneNumber) {

    public boolean canMatchWith(String passengerFirstName, LocalDate PassengerBirthday) {
        return fullName().getFirstName().equals(passengerFirstName) && birthday().equals(PassengerBirthday);
    }

    public String getId() {
        return passengerId.getId();
    }
}
