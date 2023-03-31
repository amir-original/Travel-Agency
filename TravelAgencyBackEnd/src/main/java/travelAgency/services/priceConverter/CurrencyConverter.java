package travelAgency.services.priceConverter;

import java.util.Objects;

public final class CurrencyConverter {
    private final double oneDollarToRail;
    private final double oneRialToDollar;
    private final double oneEuroToRial;
    private final double oneRialToEuro;

    public CurrencyConverter(double oneDollarToRail, double oneRialToDollar,
                             double oneEuroToRial, double oneRialToEuro) {
        this.oneDollarToRail = oneDollarToRail;
        this.oneRialToDollar = oneRialToDollar;
        this.oneEuroToRial = oneEuroToRial;
        this.oneRialToEuro = oneRialToEuro;
    }

    public double oneDollarToRail() {
        return oneDollarToRail;
    }

    public double oneRialToDollar() {
        return oneRialToDollar;
    }

    public double oneEuroToRial() {
        return oneEuroToRial;
    }

    public double oneRialToEuro() {
        return oneRialToEuro;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CurrencyConverter) obj;
        return Double.doubleToLongBits(this.oneDollarToRail) == Double.doubleToLongBits(that.oneDollarToRail) &&
                Double.doubleToLongBits(this.oneRialToDollar) == Double.doubleToLongBits(that.oneRialToDollar) &&
                Double.doubleToLongBits(this.oneEuroToRial) == Double.doubleToLongBits(that.oneEuroToRial) &&
                Double.doubleToLongBits(this.oneRialToEuro) == Double.doubleToLongBits(that.oneRialToEuro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oneDollarToRail, oneRialToDollar, oneEuroToRial, oneRialToEuro);
    }

    @Override
    public String toString() {
        return "CurrencyConverter[" +
                "oneDollarToRail=" + oneDollarToRail + ", " +
                "oneRialToDollar=" + oneRialToDollar + ", " +
                "oneEuroToRial=" + oneEuroToRial + ", " +
                "oneRialToEuro=" + oneRialToEuro + ']';
    }

}
