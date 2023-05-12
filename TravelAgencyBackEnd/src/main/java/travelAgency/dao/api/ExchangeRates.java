package travelAgency.dao.api;

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
}
