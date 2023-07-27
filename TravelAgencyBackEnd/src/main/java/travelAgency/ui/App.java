package travelAgency.ui;

import travelAgency.infrastructure.*;
import travelAgency.infrastructure.user_interface.web.controller.ExchangeRateOperations;
import travelAgency.infrastructure.user_interface.web.controller.FlightOperations;
import travelAgency.infrastructure.user_interface.web.controller.ReservationOperations;
import travelAgency.infrastructure.libraries.city.CityService;
import travelAgency.infrastructure.libraries.city.CityServiceImpl;
import travelAgency.ui.pages.HomePage;

public class App {

    private final ReservationOperations reservationController;
    private final FlightOperations flightController;
    private final ExchangeRateOperations rateConverterController;
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
