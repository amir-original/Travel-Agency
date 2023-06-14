package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;
import travelAgency.domain.vo.ResidentialAddress;

import java.time.LocalDate;

public class PassengerBuilder {
    private PassengerId passengerId;
    private FullName fullName;
    private LocalDate birthday;
    private ResidentialAddress residential;
    private Phone phoneNumber;

    public static PassengerBuilder passenger() {
        return new PassengerBuilder();
    }

    public PassengerBuilder withId(PassengerId id) {
        this.passengerId = id;
        return this;
    }

    public PassengerBuilder withFullName(FullName fullName){
        this.fullName = fullName;
        return this;
    }

    public PassengerBuilder birthday(LocalDate date){
        this.birthday = date;
        return this;
    }

    public PassengerBuilder withResidential(ResidentialAddress residential){
        this.residential = residential;
        return this;
    }

    public PassengerBuilder withPhoneNumber(Phone phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Passenger build(){
        return new Passenger(passengerId,fullName,birthday,residential,phoneNumber);
    }
}
