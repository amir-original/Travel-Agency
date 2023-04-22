package travelAgency.domain.flight.currency;

public enum Currency {
    USD("usd", "$"), IRR("irr", "ريال");

    private final String name;
    private final String symbol;

    Currency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
