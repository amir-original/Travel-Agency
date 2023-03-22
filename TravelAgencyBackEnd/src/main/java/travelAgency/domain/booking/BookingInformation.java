package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.NumberOfTicketsException;
import travelAgency.domain.passenger.Passenger;

public record BookingInformation(@NotNull Passenger passenger, int numberOfTickets) {

    void check() {
        if (numberOfTickets() <= 0) throw new NumberOfTicketsException();
        passenger.check();
    }

}
