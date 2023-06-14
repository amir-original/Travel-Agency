package travelAgency.domain.passenger;

import java.time.LocalDate;

public class PassengerDto {
    private final String nationalCode;
    private final String fName;
    private final String lName;
    private final LocalDate birthday;
    private final String city;
    private final String address;
    private final String zipcode;
    private final String phoneNumber;


    public PassengerDto(String nationalCode,
                        String fName,
                        String lName,
                        LocalDate birthday,
                        String city,
                        String address,
                        String zipcode,
                        String phoneNumber) {
        this.nationalCode = nationalCode;
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
    }
}
