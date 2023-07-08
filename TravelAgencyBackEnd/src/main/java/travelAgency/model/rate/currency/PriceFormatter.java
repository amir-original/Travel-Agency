package travelAgency.model.rate.currency;

import travelAgency.model.rate.currency.Money;

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
