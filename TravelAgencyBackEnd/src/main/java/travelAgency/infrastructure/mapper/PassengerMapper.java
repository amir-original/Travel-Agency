package travelAgency.infrastructure.mapper;

import org.jetbrains.annotations.NotNull;
import travelAgency.application.dto.PassengerDto;
import travelAgency.application.dto.PassengerDtoBuilder;
import travelAgency.model.passenger.*;

import java.time.LocalDate;

import static travelAgency.model.passenger.ResidentialAddress.of;

public class PassengerMapper{


    public Passenger toEntity(@NotNull PassengerDto dto) {
        final String nationalCode = dto.nationalCode();
        final PassengerId passengerId = PassengerId.withId(nationalCode);
        final ResidentialAddress residentialAddress = of(dto.city(), dto.address(), dto.zipcode());
        final FullName fullName = FullName.of(dto.fName(), dto.lName());
        final LocalDate birthday = dto.birthday();
        final PhoneNumber phoneNumber = PhoneNumber.of(dto.phoneNumber());
        final Birthdate birthdate = Birthdate.of(birthday);
        return new Passenger(passengerId,fullName, birthdate,residentialAddress, phoneNumber);
    }

    public PassengerDto toViewDto(@NotNull Passenger entity) {
        return PassengerDtoBuilder.passenger()
                .withNationalCode(entity.getId())
                .firstName(entity.fullName().getFirstName())
                .lastName(entity.fullName().getLastName())
                .ofCity(entity.residential().getCity())
                .withAddress(entity.residential().getAddress())
                .withZipcode(entity.residential().getZipcode())
                .withBirthday(entity.birthdate().toDate())
                .withPhoneNumber(entity.phoneNumber().getNumber())
                .build();
    }
}
