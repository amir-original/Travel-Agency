package travelAgency.unit;

import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.*;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class PassengerShould {

    @Test
    void throw_PassengerNameException_when_enter_passenger_name_null_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> (passenger().firstName(null)).build()),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> (passenger().firstName("")).build()),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> (passenger().firstName("   ")).build()),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> (passenger().lastName(null)).build()),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> (passenger().lastName("")).build()),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> (passenger().lastName("   ")).build())
        );
    }

    @Test
    void throw_PassengerBirthdayNotNullException_when_enter_birthday_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> (passenger().withBirthday(null)).build());
    }

    @Test
    void throw_PassengerZipCodeNotNullException_when_enter_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> (passenger().withZipcode(null)).build()),
                () -> assertThatExceptionOfType(PassengerZipCodeNotNullException.class)
                        .isThrownBy(() -> (passenger().withZipcode("")).build())
        );
    }

    @Test
    void throw_PassengerAddressNotNullException_when_enter_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> (passenger().address(null)).build()),
                () -> assertThatExceptionOfType(PassengerAddressNotNullException.class)
                        .isThrownBy(() -> (passenger().address("")).build())
        );
    }

    @Test
    void throw_PassengerPhoneNumbersNotNullException_when_enter_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> (passenger().withPhoneNumber(null)).build()),
                () -> assertThatExceptionOfType(PassengerPhoneNumbersNotEmptyException.class)
                        .isThrownBy(() -> (passenger().withPhoneNumber("")).build())
        );
    }

    @Test
    void throw_PhoneNumberLengthSizeException_when_enter_phone_number_less_or_more_than_12() {
        assertAll(
                () -> assertThatExceptionOfType(PhoneNumberLengthSizeException.class)
                        .isThrownBy(() -> (passenger().withPhoneNumber("09124568")).build()),
                () -> assertThatExceptionOfType(PhoneNumberLengthSizeException.class)
                        .isThrownBy(() -> (passenger().withPhoneNumber("0911145235675")).build())
        );
    }
}
