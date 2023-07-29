package travelAgency.infrastructure.libraries;

import travelAgency.model.flight.Money;

public class PriceFormatter {

    private PriceFormatter() {
    }

    public static String formatPrice(double price) {
        return String.format("%,.2f", price);
    }

    public static String formatPriceWithSymbol(Money money) {
        final String separator = " ";
        return formatPrice(money.amount()) + separator + money.currency().getSymbol();
    }
}
