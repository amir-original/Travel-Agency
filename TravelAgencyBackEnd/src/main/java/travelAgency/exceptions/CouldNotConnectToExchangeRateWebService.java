package travelAgency.exceptions;

public class CouldNotConnectToExchangeRateWebService extends RuntimeException {

    public CouldNotConnectToExchangeRateWebService(String message) {
        super(message);
    }

    public static CouldNotConnectToExchangeRateWebService withUrl(String url)
    throws CouldNotConnectToExchangeRateWebService{
        return new CouldNotConnectToExchangeRateWebService(
                "Could Not Connect To ExchangeRate Webservice with this {url}: "+url);
    }
}
