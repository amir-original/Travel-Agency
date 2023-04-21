package travelAgency.domain.reservation;

import travelAgency.domain.exceptions.InvalidNumberOfTicketsException;

public class ReservationInformationValidator {

    private final ReservationInformation reservationInformation;

    public ReservationInformationValidator(ReservationInformation reservationInformation) {
        this.reservationInformation = reservationInformation;
    }

    public void validate() {
        if (hasNoTickets())
            throw new InvalidNumberOfTicketsException();
    }

    private boolean hasNoTickets() {
        return this.reservationInformation.numberOfTickets() <= 0;
    }
}
