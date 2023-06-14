package travelAgency.use_case.fake;

import travelAgency.domain.reservation.ReservationNumber;
import travelAgency.services.reservation.ReservationNumberGenerator;

public class FakeReservationNumber implements ReservationNumberGenerator {

    @Override
    public ReservationNumber generateReservationNumber() {
        return ReservationNumber.ofString("AA-7845-65874");
    }
}
