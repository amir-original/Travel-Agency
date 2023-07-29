package travelAgency.model.rate;

import java.util.Arrays;
import java.util.List;

public enum Currency {
    USD("usd", "$"), IRR("irr", "ريال"),
    EUR("eur","€");

    private final String value;
    private final String symbol;

    Currency(String value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public static List<String> currencies() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toList();
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
