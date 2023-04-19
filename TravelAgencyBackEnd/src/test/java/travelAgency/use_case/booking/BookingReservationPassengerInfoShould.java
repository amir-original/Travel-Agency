package travelAgency.use_case.booking;

import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.booking.Reservation;
import travelAgency.domain.exceptions.*;
import travelAgency.services.BookingReservation;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flights.FlightAvailabilityImpl;
import travelAgency.use_case.fake.FakeBookingList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeTicketNumberGenerator;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.use_case.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class BookingReservationPassengerInfoShould {
    private BookingReservation app;

    @BeforeEach
    void setUp() {
        TicketNumberGenerator ticketNumberGenerator = new FakeTicketNumberGenerator();
        FakeBookingList fakeBookingList = new FakeBookingList();
        final FlightAvailabilityImpl flightAvailability =
                new FlightAvailabilityImpl(new FakeFlight(), fakeBookingList);
        app = new BookingReservation(fakeBookingList, flightAvailability,
                new FakePassenger(), ticketNumberGenerator);
    }

    @Test
    void throw_IllegalArgumentException_when_book_a_flight_without_passenger() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation().withFlight(null).build()))
        );
    }

    @Test
    void throw_NumberOfTravelersException_when_travelers_are_less_than_or_equal_to_zero() {
        assertThatExceptionOfType(NumberOfTravelersException.class)
                .isThrownBy(() -> app.book(bookingInformation().withTravelers(0).build()));
    }

    @Test
    void throw_FlightNotFoundException_when_there_is_not_any_flight_with_entered_information() {
        assertThatExceptionOfType(FlightNotFoundException.class)
                .isThrownBy(() -> app.book(bookingInformation().withNotFoundFlight().build()));
    }

    @Test
    void throw_PassengerNameException_when_passenger_first_name_is_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().firstName("").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().firstName("   ").build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerNameException_when_passenger_last_name_is_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().lastName("").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().lastName("   ").build())
                                .build()))
        );
    }


    @Test
    void throw_IllegalArgumentException_when_passenger_name_is_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().lastName(null).build())
                                .build())),

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().firstName(null).build())
                                .build()))
        );
    }

    @Test
    void throw_IllegalArgumentException_when_passenger_birthday_is_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withPassenger(passenger().withBirthday(null).build())
                        .build()));
    }

    @Test
    void throw_IllegalArgumentException_when_passenger_zipcode_is_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation().
                                withPassenger(passenger().withZipcode(null).build())
                                .build())),

                () -> assertThatExceptionOfType(PassengerZipCodeException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().withZipcode("").build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerAddressException_when_passengers_address_is_empty() {

        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().withAddress(null).build())
                                .build())),

                () -> assertThatExceptionOfType(PassengerAddressException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().withAddress("").build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerPhoneNumberNotNullException_when_phone_number_is_null_or_empty() {
        assertAll(
                () -> assertThat(IllegalArgumentException.class,null),
                () -> assertThat(PhoneNumberNotEmptyException.class,"")
        );

    }

    @Test
    void throw_InvalidPhoneNumberException_when_phone_number_is_invalid_format() {
        assertAll(
                () -> assertThat(InvalidPhoneNumberException.class,"09124568"),
                () -> assertThat(InvalidPhoneNumberException.class,"0911145235675")
        );
    }

    private <T extends Throwable> ThrowableAssertAlternative<T> assertThat(final Class<T> exceptionType, String s) {
        return assertThatExceptionOfType(exceptionType).isThrownBy(() -> getReservationWithPhoneNumber(s));
    }

    private Reservation getReservationWithPhoneNumber(String phoneNumber) {
        return app.book(bookingInformation()
                .withPassenger(passenger().withPhoneNumber(phoneNumber).build())
                .build());
    }

}
