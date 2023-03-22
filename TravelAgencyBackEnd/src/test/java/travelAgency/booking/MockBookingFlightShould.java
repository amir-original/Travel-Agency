package travelAgency.booking;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.booking.BookingFlightRepository;
import travelAgency.repository.flight.FindFlightRepository;
import travelAgency.repository.passenger.PassengerRepository;
import travelAgency.services.BookingFlightTicket;
import travelAgency.services.booking.BookingTicketServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static travelAgency.fakeData.FakeFlightTicketBuilder.flightTicket;
import static travelAgency.fakeData.FakeFlightTicketInfoBuilder.flightTicketInfo;

public class MockBookingFlightShould {

    private static final String TICKET_NUMBER = "56472514";
    private BookingFlightTicket app;
    private FindFlightRepository findFlights;
    private PassengerRepository passengers;
    private BookingTicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        findFlights = createFindFlightsRepository();
        passengers = createPassengerRepository();
        ticketService = createTicketService();
        app = new BookingFlightTicket(ticketService, findFlights, passengers);
    }

    @NotNull
    private BookingTicketServiceImpl createTicketService() {
        final BookingTicketServiceImpl ticketService = mock(BookingTicketServiceImpl.class);
        final FlightTicket flightTicket = flightTicket().withFlightTicketNumber(TICKET_NUMBER).build();
        when(ticketService.book(any())).thenReturn(flightTicket);
        return ticketService;
    }

    @Test
    void should_be_do_actions_in_order_when_booking_a_flight() {
        final FlightTicketInfo flightTicketInfo = flightTicketInfo().build();

        final FlightTicket ticket = app.book(flightTicketInfo);
        assertThat(ticket.flightTicketInfo()).isEqualTo(flightTicketInfo);

        final InOrder inOrder = inOrder(findFlights, passengers, ticketService);

        inOrder.verify(findFlights).checkExistenceFlightWith(flightTicketInfo.flightNumber());
        inOrder.verify(passengers).save(flightTicketInfo.passenger());
        inOrder.verify(ticketService).book(flightTicketInfo);
    }

    private BookingFlightRepository createBookingFlightRepository() {
        final BookingFlightRepository mock = mock(BookingFlightRepository.class);
        doNothing().when(mock).book(any());
        return mock;
    }

    private PassengerRepository createPassengerRepository() {
        final PassengerRepository mock = mock(PassengerRepository.class);
        doNothing().when(mock).save(any(Passenger.class));
        return mock;
    }

    private FindFlightRepository createFindFlightsRepository() {
        final FindFlightRepository mock = mock(FindFlightRepository.class);
        final String notFoundFlightNumber = "Not Found Flight Number";
        doThrow(NotFindAnyFlightException.class).when(mock).checkExistenceFlightWith(notFoundFlightNumber);
        return mock;
    }

}
