package travelAgency.application.dto;

import java.time.LocalDate;

public record PassengerDto(String nationalCode, String fName, String lName, LocalDate birthday, String city,
                           String address, String zipcode, String phoneNumber) {
}
