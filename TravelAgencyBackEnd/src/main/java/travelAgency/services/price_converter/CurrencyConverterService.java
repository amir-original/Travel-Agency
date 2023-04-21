package travelAgency.services.price_converter;

import travelAgency.domain.flight.Money;

public interface CurrencyConverterService {
    Money convert(Money amount);
}
