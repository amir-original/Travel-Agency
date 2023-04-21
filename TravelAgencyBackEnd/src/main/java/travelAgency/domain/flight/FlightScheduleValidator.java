package travelAgency.domain.flight;

import travelAgency.domain.exceptions.PastFlightScheduleException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public class FlightScheduleValidator {

    private final FlightSchedule flightSchedule;

    public FlightScheduleValidator(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    private static final LocalDate NOW = now();

    public void validate() {
        if (isPassed())
            throw new PastFlightScheduleException();
    }

    private boolean isPassed() {
        return flightSchedule.departure().isBefore(NOW) || flightSchedule.arrival().isBefore(NOW);
    }
}
