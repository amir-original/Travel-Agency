package travelAgency.service.priceConverter;

public class CurrencyConverter {
    private final double oneDollarToRail;
    private final double oneRialToDollar;
    private final double oneEuroToRial;
    private final double oneRialToEuro;

    public CurrencyConverter(double oneDollarToRail, double oneRialToDollar, double oneEuroToRial, double oneRialToEuro) {
        this.oneDollarToRail = oneDollarToRail;
        this.oneRialToDollar = oneRialToDollar;
        this.oneEuroToRial = oneEuroToRial;
        this.oneRialToEuro = oneRialToEuro;
    }

    public double oneDollarToRial() {
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
}
