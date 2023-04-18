package travelAgency.services.priceConverter.currencyApi;

public class USDToIRRConverter implements ExchangeRateService {

    @Override
    public double diffAmount() {
        return service().usdToIrr();
    }
}
