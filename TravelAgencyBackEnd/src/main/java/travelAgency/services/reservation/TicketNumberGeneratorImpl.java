package travelAgency.services.reservation;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;
import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

public class TicketNumberGeneratorImpl implements TicketNumberGenerator {

    public static final String PREFIX = "AA";

    @Override
    public String generateTicketNumber() {
        final String middleNumbers = getRandomNumber(4);
        final String endNumbers = getRandomNumber(5);
        return format("%s-%s-%s", PREFIX, middleNumbers, endNumbers);
    }

    @NotNull
    private String getRandomNumber(int maxLength) {
        return randomNumeric(maxLength);
    }
}
