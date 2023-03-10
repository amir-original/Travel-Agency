package travelAgency.service.priceConverter;

import travelAgency.priceConverter.exception.AmountNotNegativeException;

public interface CurrencyConverterService {

    double convert(double amount);

    default void check(double amount){
        if (isNegative(amount)) throw new AmountNotNegativeException();
    }

    private boolean isNegative(double amount) {
        return amount < 0;
    }
}
