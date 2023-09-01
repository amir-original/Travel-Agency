package travelAgency.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.model.passenger.*;
import travelAgency.application.use_case.BookingReservation;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.FindFlight;
import travelAgency.application.use_case.SearchReservation;
import travelAgency.application.use_case.ReservationNumberGenerator;
import travelAgency.model.reservation.InvalidNumberOfTicketsException;
import travelAgency.use_case.fake.FakeReservation;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeReservationNumber;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class ShouldNotBookReservation {
    private BookingReservation app;

    @BeforeEach
    void setUp() {
        ReservationNumberGenerator reservationNumber = new FakeReservationNumber();
        FakeReservation fakeBookingList = new FakeReservation();
        final FindFlightService flights = new FindFlight(new FakeFlight());
        final SearchReservation searchReservation = new SearchReservation(new FakeReservation(),flights);
        FakePassenger passengers = new FakePassenger();

        app = new BookingReservation(fakeBookingList, passengers, searchReservation, reservationNumber);
    }

    @Test
    void when_a_flight_without_passenger() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation().withFlight(null).build()))
        );
    }

    @Test
    void when_travelers_are_less_than_or_equal_to_zero() {
        assertThatExceptionOfType(InvalidNumberOfTicketsException.class)
                .isThrownBy(() -> app.book(reservationInformation().withTravelers(0).build()));
    }

    @Test
    void when_passenger_first_name_is_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of("","rahmani")).build())
                                .build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of("   ","rahmani")).build())
                                .build()))
        );
    }

    @Test
    void when_passenger_last_name_is_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of("ali","")).build())
                                .build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of("ali","   ")).build())
                                .build()))
        );
    }


    @Test
    void when_passenger_name_is_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of(null,"rahmani")).build())
                                .build())),

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation()
                                .withPassenger(passenger().withFullName(FullName.of("ali",null)).build())
                                .build()))
        );
    }

    @Test
    void when_passenger_birthday_is_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withPassenger(passenger().birthdate(null).build())
                        .build()));
    }

    @Test
    void when_passenger_zipcode_is_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            final ResidentialAddress residential =
                                    ResidentialAddress.of("Tehran","tehran,pruzi",null);
                            app.book(reservationInformation().
                                    withPassenger(passenger().withResidential(residential).build())
                                    .build());
                        }),

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            final ResidentialAddress of =
                                    ResidentialAddress.of("Tehran","tehran,pruzi","");
                            app.book(reservationInformation()
                                    .withPassenger(passenger().withResidential(of).build())
                                    .build());
                        })
        );
    }

    @Test
    void when_passengers_address_is_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            final ResidentialAddress residential =
                                    ResidentialAddress.of("Tehran",null,"1234567890");
                            app.book(reservationInformation().
                                    withPassenger(passenger().withResidential(residential).build())
                                    .build());
                        }),

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> {
                            final ResidentialAddress address =
                                    ResidentialAddress.of("Tehran"," ","1234567890");
                            app.book(reservationInformation()
                                    .withPassenger(passenger().withResidential(address).build())
                                    .build());
                        })
        );
    }

    @Test
    void when_phone_number_is_null_or_empty() {
        assertAll(
                () -> assertThat(IllegalArgumentException.class,null),
                () -> assertThat(IllegalArgumentException.class,"")
        );

    }

    @Test
    void when_phone_number_is_invalid_format() {
        assertAll(
                () -> assertThat(InvalidPhoneNumberException.class,"09124568"),
                () -> assertThat(InvalidPhoneNumberException.class,"0911145235675")
        );
    }

    private <T extends Throwable> void assertThat(final Class<T> exceptionType, String s) {
        assertThatExceptionOfType(exceptionType).isThrownBy(() -> getReservationWithPhoneNumber(s));
    }

    private void getReservationWithPhoneNumber(String phoneNumber) {
        final Passenger passenger = passenger().withPhoneNumber(PhoneNumber.of(phoneNumber)).build();
        app.book(reservationInformation().withPassenger(passenger).build());
    }
}
