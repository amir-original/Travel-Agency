package travelAgency.model.reservation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ReservationNumber {

    @NotNull
    private final String number;

    private ReservationNumber(@NotNull String initReservationNumber) {
        if (initReservationNumber.isBlank()) throw new InvalidTicketNumberException();

        this.number = initReservationNumber;
    }

    public static ReservationNumber ofString(String number){
        return new ReservationNumber(number);
    }

    public String toText() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationNumber that = (ReservationNumber) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "ReservationNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
