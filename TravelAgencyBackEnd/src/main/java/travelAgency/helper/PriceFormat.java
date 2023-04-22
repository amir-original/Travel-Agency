package travelAgency.helper;

import travelAgency.domain.flight.currency.Money;

public class PriceFormat {

    public static String formatPrice(double price) {
        return String.format("%,.2f", price);
    }

    public static String formatPriceWithSymbol(Money money) {
        final String separator = " ";
        return formatPrice(money.amount()) + separator + money.currency().getSymbol();
    }
}
