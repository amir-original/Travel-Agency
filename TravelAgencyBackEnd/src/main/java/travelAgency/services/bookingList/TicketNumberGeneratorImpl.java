package travelAgency.services.bookingList;

import org.jetbrains.annotations.NotNull;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

public class TicketNumberGeneratorImpl implements TicketNumberGenerator {

    public static final String PREFIX = "AA";

    @Override
    public String getTicketNumberFormat() {
        return String.format("%s-%s-%s", PREFIX,
                getRandomNumber(4),
                getRandomNumber(5));
    }

    @NotNull
    private String getRandomNumber(int maxLength) {
        return randomNumeric(maxLength);
    }
}
