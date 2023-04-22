package travelAgency.services.currency_exchange;

import travelAgency.domain.flight.currency.Money;

public interface CurrencyConverterService {
    Money convert(Money amount);
}
