package travelAgency.domain;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.NumberOfTicketsException;

public record BookingInformation(@NotNull Passenger passenger, int numberOfTickets) {

    void check() {
        if (numberOfTickets() <= 0) throw new NumberOfTicketsException();
        passenger.check();
    }

}
