package travelAgency.model.rate.currency;

import java.util.Arrays;

public enum Currency {
    USD("usd", "$"), IRR("irr", "ريال"),
    EUR("eur","€");

    private final String value;
    private final String symbol;

    Currency(String value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public static String[] currencies() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    public String value(){
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + value + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
