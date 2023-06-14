package travelAgency.domain.passenger;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import static travelAgency.domain.passenger.ResidentialAddress.of;

public class PassengerMapper{


    public Passenger toEntity(@NotNull PassengerDto dto) {
        final String nationalCode = dto.getNationalCode();
        final PassengerId passengerId = PassengerId.withId(nationalCode);
        final ResidentialAddress residentialAddress = of(dto.getCity(), dto.getAddress(), dto.getZipcode());
        final FullName fullName = FullName.of(dto.getfName(), dto.getlName());
        final LocalDate birthday = dto.getBirthday();
        final PhoneNumber phoneNumber = PhoneNumber.of(dto.getPhoneNumber());
        return new Passenger(passengerId,fullName, birthday,residentialAddress, phoneNumber);
    }

    public PassengerDto toViewDto(@NotNull Passenger entity) {
        return PassengerDtoBuilder.passenger()
                .withNationalCode(entity.getId())
                .firstName(entity.fullName().getFirstName())
                .lastName(entity.fullName().getLastName())
                .ofCity(entity.residential().getCity())
                .withAddress(entity.residential().getAddress())
                .withZipcode(entity.residential().getZipcode())
                .withBirthday(entity.birthday())
                .withPhoneNumber(entity.phoneNumber().getNumber())
                .build();
    }
}
