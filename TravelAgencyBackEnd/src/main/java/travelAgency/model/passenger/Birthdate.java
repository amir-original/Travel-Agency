package travelAgency.model.passenger;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class Birthdate {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate ONE_HUNDRED_YEARS_AGO = NOW.minusYears(100);
   @NotNull
    private final LocalDate date;

    private Birthdate(@NotNull LocalDate date) {
        if (date.isAfter(NOW) || date.isEqual(NOW) || date.isBefore(ONE_HUNDRED_YEARS_AGO)) {
            throw new IllegalArgumentException(format());
        }

        this.date = date;
    }

    public static Birthdate of(LocalDate date){
        return new Birthdate(date);
    }

    private String format() {
        return String.format("birthdate is invalid your date should be between [%s-%s]",
                ONE_HUNDRED_YEARS_AGO,NOW);
    }

    public LocalDate toDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Birthdate birthdate = (Birthdate) o;
        return date.equals(birthdate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        return "Birthdate{" +
                "date=" + date +
                '}';
    }
}
