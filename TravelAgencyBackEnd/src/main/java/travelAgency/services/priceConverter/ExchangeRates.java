package travelAgency.services.priceConverter;

import java.util.Objects;

public final class ExchangeRates {
    private final double usdToIrr;
    private final double irrToUsd;


    public ExchangeRates(double usdToIrr, double irrToUsd) {
        this.usdToIrr = usdToIrr;
        this.irrToUsd = irrToUsd;
    }

    public double usdToIrr() {
        return usdToIrr;
    }

    public double irrToUsd() {
        return irrToUsd;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExchangeRates) obj;
        return Double.doubleToLongBits(this.usdToIrr) == Double.doubleToLongBits(that.usdToIrr) &&
                Double.doubleToLongBits(this.irrToUsd) == Double.doubleToLongBits(that.irrToUsd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usdToIrr, irrToUsd);
    }

    @Override
    public String toString() {
        return "ExchangeRates[" +
                "usdToIrr=" + usdToIrr + ", " +
                "irrToUsd=" + irrToUsd;
    }

}
