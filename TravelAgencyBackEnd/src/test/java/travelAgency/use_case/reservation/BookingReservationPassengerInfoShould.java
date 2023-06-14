package travelAgency.use_case.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.FullName;
import travelAgency.domain.passenger.PhoneNumber;
import travelAgency.domain.passenger.ResidentialAddress;
import travelAgency.exceptions.*;
import travelAgency.services.BookingReservation;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.ReservationNumberGenerator;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeReservationNumber;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.use_case.fake.FakeReservationInformationBuilder.reservationInformation;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class BookingReservationPassengerInfoShould {
    private BookingReservation app;

    @BeforeEach
    void setUp() {
        ReservationNumberGenerator reservationNumber = new FakeReservationNumber();
        FakeReservationList fakeBookingList = new FakeReservationList();
        final FlightListService flights = new FlightListServiceImpl(new FakeFlight());
        final FlightAvailability flightAvailability =
                new FlightAvailability(new ReservationListServiceImpl(fakeBookingList,flights));
        app = new BookingReservation(fakeBookingList, new FakePassenger(), flightAvailability,
                reservationNumber);
    }

    @Test
    void throw_IllegalArgumentException_when_book_a_flight_without_passenger() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(reservationInformation().withFlight(null).build()))
        );
    }

    @Test
    void throw_InvalidNumberOfTicketsException_when_travelers_are_less_than_or_equal_to_zero() {
        assertThatExceptionOfType(InvalidNumberOfTicketsException.class)
                .isThrownBy(() -> app.book(reservationInformation().withTravelers(0).build()));
    }

    @Test
    void throw_FlightNotFoundException_when_there_is_not_any_flight_with_entered_information() {
        assertThatExceptionOfType(FlightNotFoundException.class)
                .isThrownBy(() -> app.book(reservationInformation().withNotFoundFlight().build()));
    }

    @Test
    void throw_IllegalArgumentException_when_passenger_first_name_is_empty_or_blank() {
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
    void throw_IllegalArgumentException_when_passenger_last_name_is_empty_or_blank() {
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
    void throw_IllegalArgumentException_when_passenger_name_is_null() {
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
    void throw_IllegalArgumentException_when_passenger_birthday_is_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withPassenger(passenger().birthday(null).build())
                        .build()));
    }

    @Test
    void throw_IllegalArgumentException_when_passenger_zipcode_is_null_or_empty() {
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
    void throw_IllegalArgumentException_when_passengers_address_is_empty() {
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
    void throw_IllegalArgumentException_when_phone_number_is_null_or_empty() {
        assertAll(
                () -> assertThat(IllegalArgumentException.class,null),
                () -> assertThat(IllegalArgumentException.class,"")
        );

    }

    @Test
    void throw_InvalidPhoneNumberException_when_phone_number_is_invalid_format() {
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
