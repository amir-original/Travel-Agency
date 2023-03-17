package travelAgency.domain;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.TicketNumberNotZeroException;

public record BookingInformation(@NotNull Passenger passenger, int numberOfTickets) {


    void check() {
        if (numberOfTickets() <= 0)
            throw new TicketNumberNotZeroException();
    }
}
