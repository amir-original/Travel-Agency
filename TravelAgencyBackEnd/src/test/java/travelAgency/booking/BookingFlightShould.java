package travelAgency.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.*;
import travelAgency.fakeData.FakeFindFlight;
import travelAgency.fakeData.FakePassenger;
import travelAgency.repository.booking.BookingFlightRepository;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.booking.BookingTicketServiceImpl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class BookingFlightShould {

    private BookingFlightTicket app;

    @BeforeEach
    void setUp() {
        app = new BookingFlightTicket(
                new BookingTicketServiceImpl(new FakeBookingFlight()),
                new FakeFindFlight(),
                new FakePassenger()
        );
    }

    @Test
    void book_a_flight_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> app.book(flightTicketInfo().build()));
    }

    @Test
    void throw_IllegalArgumentException_when_book_flight_without_passenger() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo().withFlight(null).build()))
        );

    }

    @Test
    void throw_NumberOfTicketsException_when_book_flight_with_number_of_ticket_zero_or_less() {
        assertThatExceptionOfType(NumberOfTicketsException.class)
                .isThrownBy(() -> app.book(flightTicketInfo().withNumbers(0).build()));
    }

    @Test
    void throw_NotFindAnyFlightException_when_there_is_not_find_flight_with_entered_information() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(() -> app.book(flightTicketInfo().withNotFoundFlight().build()));
    }

    @Test
    void throw_PassengerNameException_when_enter_passenger_name_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().firstName("").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().firstName("   ").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().lastName("").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().lastName("   ").build())
                                .build()))
        );
    }

    @Test
    void throw_IllegalArgumentException_when_enter_passenger_name_null() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().lastName(null).build())
                                .build())),

                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().firstName(null).build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerBirthdayNotNullException_when_enter_birthday_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> app.book(flightTicketInfo()
                        .withPassenger(passenger().withBirthday(null).build())
                        .build()));
    }

    @Test
    void throw_PassengerZipCodeNotNullException_when_enter_passenger_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo().
                                withPassenger(passenger().withZipcode(null).build())
                                .build())),

                () -> assertThatExceptionOfType(PassengerZipCodeException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().withZipcode("").build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerAddressNotNullException_when_enter_passenger_empty() {

        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().withAddress(null).build())
                                .build())),

                () -> assertThatExceptionOfType(PassengerAddressException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().withAddress("").build())
                                .build()))
        );
    }

    @Test
    void throw_PassengerPhoneNumbersNotNullException_when_enter_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().withPhoneNumber(null).build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerPhoneNumbersNotEmptyException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger().withPhoneNumber("").build())
                                .build()))
        );
    }

    @Test
    void throw_PhoneNumberLengthException_when_enter_phone_number_less_or_more_than_12() {
        assertAll(
                () -> assertThatExceptionOfType(PhoneNumberLengthException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger()
                                        .withPhoneNumber("09124568")
                                        .build())
                                .build())
                        ),
                () -> assertThatExceptionOfType(PhoneNumberLengthException.class)
                        .isThrownBy(() -> app.book(flightTicketInfo()
                                .withPassenger(passenger()
                                        .withPhoneNumber("0911145235675").build())
                                .build()))
        );
    }

    private static class FakeBookingFlight implements BookingFlightRepository {

        @Override
        public void book(FlightTicket flightTicket) {

        }

        @Override
        public void truncate() {

        }

    }

}
