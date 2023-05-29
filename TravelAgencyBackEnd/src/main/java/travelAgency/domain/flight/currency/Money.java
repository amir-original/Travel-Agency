package travelAgency.domain.flight.currency;

import travelAgency.helper.PriceFormatter;

public record Money(double amount, Currency currency) {

    public Money {
        check();
    }

    public void check() {
        if (isNegative(amount()))
            throw new IllegalArgumentException("the price value must not be a negative number");
    }

    public String formatMoneyWithSymbol(){
        return PriceFormatter.formatPriceWithSymbol(this);
    }

    public String formatAmount(){
        return PriceFormatter.formatPrice(amount);
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }
}
