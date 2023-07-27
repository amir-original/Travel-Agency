package travelAgency.use_case.fake;

import travelAgency.model.reservation.ReservationNumber;
import travelAgency.application.use_case.ReservationNumberGenerator;

public class FakeReservationNumber implements ReservationNumberGenerator {

    @Override
    public ReservationNumber generateReservationNumber() {
        return ReservationNumber.ofString("AA-7845-65874");
    }
}
