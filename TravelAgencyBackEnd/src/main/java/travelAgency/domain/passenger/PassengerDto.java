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

    public String getNationalCode() {
        return nationalCode;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
