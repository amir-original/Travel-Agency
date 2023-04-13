package travelAgency.use_case.booking;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.bookingList.TicketNumberGenerator;
import travelAgency.services.flights.FlightAvailabilityImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.use_case.fake.FakeBookingInformationBuilder.bookingInformation;
import static travelAgency.use_case.fake.FakeBookingList.flightTicket;

public class MockBookingFlightShould {

    public static final String TICKET_NUMBER = "78456587";
    private BookingFlightTicket app;
    private FlightAvailabilityImpl flightAvailability;
    private PassengerRepository passengers;
    private BookingListRepository bookingLists;
    private TicketNumberGenerator ticketNumberGenerator;

    @BeforeEach
    void setUp() {
        passengers = createPassengerRepository();
        bookingLists = createTicketService();
        flightAvailability = createFindFlightsRepository();
        ticketNumberGenerator = createMockTicketGenerator();
        app = new BookingFlightTicket(bookingLists, flightAvailability, passengers, ticketNumberGenerator);
    }

    @Test
    void be_do_actions_in_order_when_booking_a_flight() {
        final BookingInformation bookingInformation = bookingInformation().build();

        final FlightTicket ticket = app.book(bookingInformation);
        assertThat(ticket.bookingInformation()).isEqualTo(bookingInformation);


        final InOrder inOrder = inOrder(flightAvailability, passengers,
                bookingLists, ticketNumberGenerator);

        inOrder.verify(flightAvailability).checkFlight(bookingInformation);

        final FlightTicket flightTicket = bookingInformation.getFlightTicket(ticketNumberGenerator.generateTicketNumber());

        inOrder.verify(passengers).save(bookingInformation.passenger());
        inOrder.verify(bookingLists).book(flightTicket);
    }

    @NotNull
    private FlightTicket createTicket() {
        return flightTicket(TICKET_NUMBER);
    }

    @NotNull
    private BookingListRepository createTicketService() {
        final BookingListRepository bookings = mock(BookingListRepository.class);
        final FlightTicket flightTicket = createTicket();
        doNothing().when(bookings).book(flightTicket);
        return bookings;
    }

    private PassengerRepository createPassengerRepository() {
        final PassengerRepository mock = mock(PassengerRepository.class);
        doNothing().when(mock).save(any(Passenger.class));
        return mock;
    }

    private FlightAvailabilityImpl createFindFlightsRepository() {
        final FlightAvailabilityImpl mock = mock(FlightAvailabilityImpl.class);
        doNothing().when(mock).checkFlight(any());
        return mock;
    }

    @NotNull
    private TicketNumberGenerator createMockTicketGenerator() {
        TicketNumberGenerator ticketNumberGenerator = mock(TicketNumberGenerator.class);
        when(ticketNumberGenerator.generateTicketNumber()).thenReturn("56472514");
        //when(ticketNumberGenerator.getFlightTicket(any())).thenReturn(createTicket());
        return ticketNumberGenerator;
    }

}
