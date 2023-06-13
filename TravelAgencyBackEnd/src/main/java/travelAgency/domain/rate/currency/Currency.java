package travelAgency.domain.rate.currency;

import java.util.Arrays;

public enum Currency {
    USD("usd", "$"), IRR("irr", "ريال"),
    EUR("eur","€");

    private final String name;
    private final String symbol;

    Currency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public static String[] currencies() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toArray(String[]::new);
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
