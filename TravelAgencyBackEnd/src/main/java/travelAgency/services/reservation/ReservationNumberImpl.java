package travelAgency.services.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.ReservationNumber;

import static java.lang.String.format;
import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

public final class ReservationNumberImpl implements ReservationNumberGenerator {

    public static final String PREFIX = "AA";

    @Override
    public ReservationNumber generateReservationNumber() {
        final String middleNumbers = getRandomNumber(4);
        final String endNumbers = getRandomNumber(5);
        final String formatNumber = format("%s-%s-%s", PREFIX, middleNumbers, endNumbers);

        return ReservationNumber.ofString(formatNumber);
    }

    @NotNull
    private String getRandomNumber(int maxLength) {
        return randomNumeric(maxLength);
    }
}
