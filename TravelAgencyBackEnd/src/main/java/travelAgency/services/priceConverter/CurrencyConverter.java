package travelAgency.services.priceConverter;

import java.util.Objects;

public final class CurrencyConverter {
    private final double oneDollarToRail;
    private final double oneRialToDollar;


    public CurrencyConverter(double oneDollarToRail, double oneRialToDollar) {
        this.oneDollarToRail = oneDollarToRail;
        this.oneRialToDollar = oneRialToDollar;
    }

    public double oneDollarToRail() {
        return oneDollarToRail;
    }

    public double oneRialToDollar() {
        return oneRialToDollar;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CurrencyConverter) obj;
        return Double.doubleToLongBits(this.oneDollarToRail) == Double.doubleToLongBits(that.oneDollarToRail) &&
                Double.doubleToLongBits(this.oneRialToDollar) == Double.doubleToLongBits(that.oneRialToDollar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oneDollarToRail, oneRialToDollar);
    }

    @Override
    public String toString() {
        return "CurrencyConverter[" +
                "oneDollarToRail=" + oneDollarToRail + ", " +
                "oneRialToDollar=" + oneRialToDollar;
    }

}
