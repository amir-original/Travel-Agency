package travelAgency.domain.flight;

public record Money(double amount, Currency currency) {

    public Money {
        check();
    }

    public void check() {
        if (isNegative(amount()))
            throw new IllegalArgumentException("the price value must not be a negative number");
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }
}
