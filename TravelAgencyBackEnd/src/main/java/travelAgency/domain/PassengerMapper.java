package travelAgency.domain;

import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerDto;
import travelAgency.domain.passenger.PassengerDtoBuilder;
import travelAgency.domain.vo.FullName;
import travelAgency.domain.vo.PassengerId;
import travelAgency.domain.vo.Phone;
import travelAgency.domain.vo.ResidentialAddress;

import java.time.LocalDate;

import static travelAgency.domain.vo.ResidentialAddress.of;

public class PassengerMapper{


    public Passenger toEntity(PassengerDto dto) {
        final String nationalCode = dto.getNationalCode();
        final PassengerId passengerId = PassengerId.of(nationalCode);
        final ResidentialAddress residentialAddress = of(dto.getCity(), dto.getAddress(), dto.getZipcode());
        final FullName fullName = FullName.of(dto.getfName(), dto.getlName());
        final LocalDate birthday = dto.getBirthday();
        final Phone phone = Phone.of(dto.getPhoneNumber());
        return new Passenger(passengerId,fullName, birthday,residentialAddress,phone);
    }

    public PassengerDto toViewDto(Passenger entity) {
        return PassengerDtoBuilder.passenger()
                .withId(entity.getId())
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
