package travelAgency.use_case.fake;

import travelAgency.model.passenger.*;

import java.time.LocalDate;

public class PassengerBuilder {
    private PassengerId passengerId;
    private FullName fullName;
    private LocalDate birthdate;
    private ResidentialAddress residential;
    private PhoneNumber phoneNumber;

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

    public PassengerBuilder birthdate(LocalDate date){
        this.birthdate = date;
        return this;
    }

    public PassengerBuilder withResidential(ResidentialAddress residential){
        this.residential = residential;
        return this;
    }

    public PassengerBuilder withPhoneNumber(PhoneNumber phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Passenger build(){
        Birthdate bd = Birthdate.of(birthdate);
        return new Passenger(passengerId,fullName, bd,residential,phoneNumber);
    }
}
