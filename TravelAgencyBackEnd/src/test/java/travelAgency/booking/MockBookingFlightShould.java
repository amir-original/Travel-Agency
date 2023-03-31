package travelAgency.booking;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.passenger.Passenger;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.bookingList.BookingListServiceImpl;
import travelAgency.services.flights.FlightAvailabilityImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.fake.FakeFlightTicketBuilder.flightTicket;
import static travelAgency.fake.FakeBookingInformationBuilder.bookingInformation;

public class MockBookingFlightShould {

    private static final String TICKET_NUMBER = "56472514";
    public static final String NOT_FOUND_FLIGHT_NUMBER = "Not Found Flight Number";
    private BookingFlightTicket app;
    private FlightAvailabilityImpl flightAvailability;
    private PassengerRepository passengers;
    private BookingListService ticketService;

    @BeforeEach
    void setUp() {
        passengers = createPassengerRepository();
        ticketService = createTicketService();
        flightAvailability = createFindFlightsRepository();
        app = new BookingFlightTicket(ticketService, flightAvailability, passengers);
    }

    @Test
    void be_do_actions_in_order_when_booking_a_flight() {
        final BookingInformation bookingInformation = bookingInformation().build();

        final FlightTicket ticket = app.book(bookingInformation);
        assertThat(ticket.bookingInformation()).isEqualTo(bookingInformation);

        final InOrder inOrder = inOrder(flightAvailability, passengers, ticketService);

        final String flightNumber = bookingInformation.flightNumber();

        inOrder.verify(flightAvailability).checkingFlight(flightNumber,
                bookingInformation.numberOfTickets());

        inOrder.verify(passengers).save(bookingInformation.passenger());
        inOrder.verify(ticketService).book(bookingInformation);
    }

    @NotNull
    private BookingListService createTicketService() {
        final BookingListService ticketService = mock(BookingListServiceImpl.class);
        final FlightTicket flightTicket = flightTicket().withFlightTicketNumber(TICKET_NUMBER).build();
        when(ticketService.book(any())).thenReturn(flightTicket);
        return ticketService;
    }

    private PassengerRepository createPassengerRepository() {
        final PassengerRepository mock = mock(PassengerRepository.class);
        doNothing().when(mock).save(any(Passenger.class));
        return mock;
    }

    private FlightAvailabilityImpl createFindFlightsRepository() {
        final FlightAvailabilityImpl mock = mock(FlightAvailabilityImpl.class);
        doNothing().when(mock).checkingFlight(anyString(),anyInt());
        return mock;
    }

}
