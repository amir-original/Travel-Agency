package travelAgency.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.domain.exceptions.*;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.fake.FakeBookingList;
import travelAgency.fake.FakeFlight;
import travelAgency.fake.FakePassenger;
import travelAgency.fake.FakeTicketGenerator;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.bookingList.TicketGenerator;
import travelAgency.services.flights.FlightAvailabilityImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.fake.FakeFlight.flight;
import static travelAgency.fake.FakeFlightPlanBuilder.flightPlan;
import static travelAgency.fake.FakePassenger.passenger;

public class BookingFlightShould {

    private BookingFlightTicket app;

    @BeforeEach
    void setUp() {
        TicketGenerator ticketGenerator = new FakeTicketGenerator();
        FakeBookingList fakeBookingList = new FakeBookingList();

        app = new BookingFlightTicket(fakeBookingList,
                new FlightAvailabilityImpl(new FakeFlight(), fakeBookingList),
                new FakePassenger(),
                ticketGenerator
        );
    }



    @Test
    void book_a_flight_without_throw_any_exception() {
        assertThatNoException().isThrownBy(() -> app.book(bookingInformation().build()));
    }

    @Test
    void throw_IllegalArgumentException_when_book_flight_without_passenger() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation().withPassenger(null).build())),
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation().withFlight(null).build()))
        );
    }

    @Test
    void the_number_of_travelers_must_be_valid_and_greeter_than_zero() {
        assertThatExceptionOfType(NumberOfTravelersException.class)
                .isThrownBy(() -> app.book(bookingInformation().withTravelers(0).build()));
    }

    @Test
    void throw_NotFindAnyFlightException_when_there_is_not_find_flight_with_entered_information() {
        assertThatExceptionOfType(NotFindAnyFlightException.class)
                .isThrownBy(() -> app.book(bookingInformation().withNotFoundFlight().build()));
    }

    @Test
    void throw_PassengerNameException_when_enter_passenger_name_empty_or_blank() {
        assertAll(
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().firstName("").build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerNameException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().firstName("   ").build())
                                .build())),
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
    void throw_IllegalArgumentException_when_enter_passenger_name_null() {
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
    void throw_PassengerBirthdayNotNullException_when_enter_birthday_null() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withPassenger(passenger().withBirthday(null).build())
                        .build()));
    }

    @Test
    void throw_PassengerZipCodeNotNullException_when_enter_passenger_null_or_empty() {
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
    void throw_PassengerAddressNotNullException_when_enter_passenger_empty() {

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
    void throw_PassengerPhoneNumbersNotNullException_when_enter_null_or_empty() {
        assertAll(
                () -> assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().withPhoneNumber(null).build())
                                .build())),
                () -> assertThatExceptionOfType(PassengerPhoneNumbersNotEmptyException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger().withPhoneNumber("").build())
                                .build()))
        );

    }

    @Test
    void throw_PhoneNumberLengthException_when_enter_phone_number_less_or_more_than_12() {
        assertAll(
                () -> assertThatExceptionOfType(PhoneNumberLengthException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger()
                                        .withPhoneNumber("09124568")
                                        .build())
                                .build())
                        ),
                () -> assertThatExceptionOfType(PhoneNumberLengthException.class)
                        .isThrownBy(() -> app.book(bookingInformation()
                                .withPassenger(passenger()
                                        .withPhoneNumber("0911145235675")
                                        .build())
                                .build()))
        );
    }


    @Test
    void throw_FlightNumberException_when_entered_empty_or_less_than_3_length() {
        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("").build())
                        .build()));

        assertThatExceptionOfType(FlightNumberException.class)
                .isThrownBy(() -> app.book(bookingInformation()
                        .withFlight(flight().withFlightNumber("45").build())
                        .build()));
    }

    @Test
    void throw_FlightScheduleException_when_book_flight_with_past_departure_and_arrival() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        assertThatExceptionOfType(FlightScheduleMostNotBePastException.class)
                .isThrownBy(() -> {
                    app.book(bookingInformation().withFlight(flight().departureAt(yesterday).build()).build());
                });

        assertThatExceptionOfType(FlightScheduleMostNotBePastException.class)
                .isThrownBy(() -> {
                    final FlightPlan flightPlan = flightPlan().arrivalAt(yesterday).build();
                    final Flight flight = flight().arrivalAt(yesterday).build();
                    app.book(bookingInformation().withFlight(flight).build());
                });
    }

    @Test
    void throw_FlightLocationException_when_enter_origin_and_destination_the_same() {
        assertThatExceptionOfType(FlightLocationException.class)
                .isThrownBy(() -> {
                    app.book(bookingInformation()
                            .withFlight(flight().from(TEHRAN).to(TEHRAN).build())
                            .build());
                });
    }

    @Test
    void check_the_availability_of_flight_for_booking() {
        BookedAllSeatsOfFlight();

        assertThatExceptionOfType(FullyBookedException.class)
                .isThrownBy(() -> app.book(bookingInformation().build()));
    }

    @Test
    void throw_an_exception_when_there_are_not_enough_seats_available_on_the_flight() {
        insertTenBookingFlight();
        insertTenBookingFlight();
        insertTenBookingFlight();

        assertThatExceptionOfType(NotEnoughCapacityException.class)
                .isThrownBy(() -> app.book(bookingInformation().withTravelers(5).build()));
    }

    private void BookedAllSeatsOfFlight() {
        insertTenBookingFlight();
        insertTenBookingFlight();
        insertTenBookingFlight();
        app.book(bookingInformation().withTravelers(4).build());
    }

    private void insertTenBookingFlight() {
        app.book(bookingInformation().withTravelers(4).build());
        app.book(bookingInformation().withPassenger(passenger("se478")).withTravelers(2).build());
        app.book(bookingInformation().withPassenger(passenger("mes784")).withTravelers(1).build());
        app.book(bookingInformation().withPassenger(passenger("ew471")).withTravelers(3).build());

    }
}
