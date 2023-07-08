package travelAgency.model.rate.currency;

import java.util.Objects;

public final class Money {
    private final double amount;
    private final Currency currency;


    private Money(double amount, Currency currency) {
        if (isNegative(amount))
            throw new IllegalArgumentException("the price amount must not be a negative number");

        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(double price, Currency currency) {
        return new Money(price, currency);
    }

    public String formatMoneyWithSymbol() {
        return PriceFormatter.formatPriceWithSymbol(this);
    }

    public String formatAmount() {
        return PriceFormatter.formatPrice(amount);
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }

    public double amount() {
        return amount;
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Money) obj;
        return Double.doubleToLongBits(this.amount) == Double.doubleToLongBits(that.amount) &&
                Objects.equals(this.currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money[" +
                "amount=" + amount + ", " +
                "currency=" + currency + ']';
    }

    public Money convert(Currency to, double rate) {
        return new Money(amount * rate, to);
    }
}
