package travelAgency.ui;

import travelAgency.controller.ReservationController;
import travelAgency.controller.ExchangeRateController;
import travelAgency.controller.FlightController;
import travelAgency.ServiceContainer;
import travelAgency.services.city.CityService;
import travelAgency.services.city.CityServiceImpl;
import travelAgency.ui.pages.HomePage;

public class App {

    private final ReservationController reservationController;
    private final FlightController flightController;
    private final ExchangeRateController rateConverterController;
    private final CityService cityService;

    public App() {
        final ServiceContainer serviceContainer = new ServiceContainer();
        reservationController = serviceContainer.reservationController();
        this.rateConverterController = serviceContainer.exchangeRateController();
        this.flightController = serviceContainer.flightController();
        this.cityService = new CityServiceImpl();
    }


    public static void main(String[] args) {
        final App app = new App();
        app.run();
    }

    public void run() {
        buildHomePage();
    }

    private void buildHomePage() {
        new HomePage(reservationController, flightController, rateConverterController, cityService);
    }

}
