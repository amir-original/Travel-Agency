package travelAgency.services.priceConverter;

public interface CurrencyConverterService {
    double convert(double amount);
    String convertAndFormat(double amount);
}
