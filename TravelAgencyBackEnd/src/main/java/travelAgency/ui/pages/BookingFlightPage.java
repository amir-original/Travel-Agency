package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.infrastructure.ServiceContainer;
import travelAgency.infrastructure.libraries.city.CityServiceImpl;
import travelAgency.model.flight.FlightLocation;
import travelAgency.model.flight.FlightSchedule;
import travelAgency.application.dto.FlightDto;
import travelAgency.infrastructure.user_interface.web.controller.ExchangeRateOperations;
import travelAgency.infrastructure.user_interface.web.controller.FlightOperations;
import travelAgency.infrastructure.user_interface.web.controller.ReservationOperations;
import travelAgency.infrastructure.libraries.city.CityService;
import travelAgency.ui.App;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class BookingFlightPage extends JFrame {

    private final FlightSearchResultPanel flightSearchResult;
    private final ExchangeRateOperations rateConverterController;
    private JLabel resultLabel;
    private JComboBox<Object> originComboBox, destinationComboBox;
    private JButton backButton, searchButton, nextButton;

    private UiComponents ui;
    private final CityService cityService;
    private final FlightOperations flightController;
    private JDateChooser departureDateChooser;
    private JDateChooser arrivalDateChooser;
    private JPanel resultPanel;
    private JComboBox<Object> exchangeRate;
    private JSpinner passengersSpinner;


    public BookingFlightPage() {
        ServiceContainer serviceContainer = new ServiceContainer();
        this.cityService = new CityServiceImpl();
        this.flightController = serviceContainer.flightController();
        this.rateConverterController = serviceContainer.exchangeRateController();
        this.flightSearchResult = new FlightSearchResultPanel(this.rateConverterController,flightController);

        createBookingFlightPage();
    }

    private void createBookingFlightPage() {
        setupPage();
        JPanel mainPanel = createMainPanel();
        createComponents(mainPanel);

        ui.update(this);
    }

    private void setupPage() {
        initConfigPage();
        this.ui = new UiComponents();
    }

    private void initConfigPage() {
        setTitle("Booking Flight Page");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @NotNull
    private JPanel createMainPanel() {
        JPanel mainPanel = ui.boarderLayoutPanel();
        add(mainPanel);
        return mainPanel;
    }

    private void createComponents(JPanel mainPanel) {
        createHeaderPanel(mainPanel);

        initializeResultPanel(mainPanel);

        createFooterPanel(mainPanel);
    }

    private void createHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = ui.flowLayoutPanel(FlowLayout.CENTER, 10, 10);

        createHeaderPanelFields(headerPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createHeaderPanelFields(JPanel headerPanel) {
        createOriginField(headerPanel);

        createDestinationField(headerPanel);

        createDepartureField(headerPanel);

        createArrivalField(headerPanel);

        createPassengersField(headerPanel);
    }

    private void createOriginField(JPanel headerPanel) {
        originComboBox = AddCityDropdownToPanel(headerPanel, "From:");
    }

    private void createDestinationField(JPanel headerPanel) {
        destinationComboBox = AddCityDropdownToPanel(headerPanel, "To:");
    }

    private JComboBox<Object> AddCityDropdownToPanel(JPanel headerPanel, String labelText) {
        JLabel label = ui.label(labelText);
        JComboBox<Object> cityDropdown = getAirportListsDropdown();
        headerPanel.add(label);
        headerPanel.add(cityDropdown);
        return cityDropdown;
    }

    public JComboBox<Object> getAirportListsDropdown() {
        return ui.dropdown(cityService.citiesList(), 100, 30);
    }

    private void createDepartureField(JPanel headerPanel) {
        departureDateChooser = createAndAddDatePickerToHeaderPanel(headerPanel, "Departure:");
    }

    private void createArrivalField(JPanel headerPanel) {
        arrivalDateChooser = createAndAddDatePickerToHeaderPanel(headerPanel, "Arrival:");
    }

    private JDateChooser createAndAddDatePickerToHeaderPanel(JPanel headerPanel, String labelText) {
        JLabel departureLabel = ui.label(labelText);
        final JDateChooser jDateChooser = ui.dateChooser(150, 30);
        disableDatesBefogTodayDate(jDateChooser);
        headerPanel.add(departureLabel);
        headerPanel.add(jDateChooser);
        return jDateChooser;
    }

    private void disableDatesBefogTodayDate(JDateChooser dateChooser) {
        dateChooser.setMinSelectableDate(Calendar.getInstance().getTime());
    }

    private void createPassengersField(JPanel headerPanel) {
        JLabel passengersLabel = ui.label("Passengers:");
        passengersSpinner = ui.inputNumber(1, 1, 5, 50, 30);
        headerPanel.add(passengersLabel);
        headerPanel.add(passengersSpinner);
    }


    private void createFooterPanel(JPanel mainPanel) {
        JPanel footerPanel = ui.flowLayoutPanel(FlowLayout.CENTER, 20, 10);

        createFooterButtons(footerPanel);

        addButtonsToFooterPanel(footerPanel);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void createFooterButtons(JPanel footerPanel) {
        createCurrencyConverterField(footerPanel);

        createBackButton();

        createSearchButton();

        createNextButton();
    }

    private void createCurrencyConverterField(JPanel footerPanel) {
        final JLabel converterLabel = ui.label("Currency");
        final List<String> currencies = rateConverterController.getCurrencies();
        exchangeRate = ui.dropdown(currencies, 100, 30);
        footerPanel.add(converterLabel);
        footerPanel.add(exchangeRate);
    }

    private void createBackButton() {
        backButton = createButton("Back");
        goBackToHomePageAction();
    }

    private void goBackToHomePageAction() {
        backButton.addActionListener(e -> {
            final App app = new App();
            app.run();
            dispose();
        });
    }

    private void createSearchButton() {
        searchButton = createButton("Search");
        searchButtonClicked();
    }

    private void searchButtonClicked() {
        searchButton.addActionListener(e -> performFlightSearchAndDisplayResults());
    }

    private void performFlightSearchAndDisplayResults() {
        final List<String> errors = canSearchFlight();
        if (!errors.isEmpty()) {
            displayErrors(errors);
        } else {
            executeFlightSearch();
        }
    }

    private void displayErrors(List<String> errors) {
        for (String error : errors) {
            displayErrorMessage(error);
        }
    }

    private void executeFlightSearch() {
        displaySearchResultTitle();
        final String currency = (String) this.exchangeRate.getSelectedItem();
        performFlightSearch(currency);
    }

    private void performFlightSearch(String exchangeRate) {
        final List<FlightDto> searchFlights;
        try {
            searchFlights = flightController.searchFlights(getFlightPlan());
            flightSearchResult.showFlightsInfo(getFlightPlan(), exchangeRate);
            updateFlightSearchResults(flightSearchResult);
            enableNextButtonIfSearchResultsExist(searchFlights);
        } catch (Exception e) {
            displayErrorMessage(e.getMessage());
        }
    }

    private List<String> canSearchFlight() {
        List<String> errorMessages = new LinkedList<>();
        if (departureDateChooser.getDate() == null || arrivalDateChooser.getDate() == null) {
            errorMessages.add("Departure and arrival date must not be null!");
        } else {
            validateFlightLocation(errorMessages);
        }

        return errorMessages;
    }

    private void validateFlightLocation(List<String> errorMessages) {
        try {
            final String from = (String) originComboBox.getSelectedItem();
            String to = (String) destinationComboBox.getSelectedItem();
            new FlightLocation(cityService.getCity(from), cityService.getCity(to));
        } catch (Exception e) {
            errorMessages.add(e.getMessage());
        }
    }

    private void displaySearchResultTitle() {
        final Object origin = originComboBox.getSelectedItem();
        final Object destination = destinationComboBox.getSelectedItem();
        final String title = format("Showing results for %s to %s ", origin, destination);
        resultLabel.setText(title);
        resultPanel.add(resultLabel);
    }

    private void updateFlightSearchResults(FlightSearchResultPanel flightSearchResult) {
        resultPanel.removeAll();
        resultPanel.add(flightSearchResult);
        resultPanel.repaint();
    }

    private void enableNextButtonIfSearchResultsExist(List<FlightDto> searchFlights) {
        if (!searchFlights.isEmpty())
            nextButton.setEnabled(true);
    }

    private void createNextButton() {
        nextButton = createButton("Next");
        nextButton.setEnabled(false);
        goToNextPageAction();
    }

    private void addButtonsToFooterPanel(JPanel footerPanel) {
        footerPanel.add(backButton);
        footerPanel.add(searchButton);
        footerPanel.add(nextButton);
    }

    @NotNull
    private FlightPlanRequest getFlightPlan() {
        final String from = (String) originComboBox.getSelectedItem();
        String to = (String) destinationComboBox.getSelectedItem();
        final ZoneId zone = ZoneId.systemDefault();
        LocalDate departure = departureDateChooser.getDate().toInstant().atZone(zone).toLocalDate();
        LocalDate arrival = arrivalDateChooser.getDate().toInstant().atZone(zone).toLocalDate();

        return new FlightPlanRequest(from, to,departure,arrival);
    }

    private void goToNextPageAction() {
        nextButton.addActionListener(e -> goToBookingInformationPage());
    }

    private void goToBookingInformationPage() {
        final String selectedFlight = flightSearchResult.getSelectedFlight();
        if (isFlightSelected(selectedFlight)) {
            final FlightDto flight = getFlightBy(selectedFlight);
            new BookingInformationPage(flight, getTravelers());
            dispose();
        } else
            displayErrorMessage("Please select a flight to proceed.");
    }

    private int getTravelers() {
        return (int) passengersSpinner.getValue();
    }

    private FlightDto getFlightBy(String selectedFlightNumber) {
        return flightController.findFlight(selectedFlightNumber);
    }

    private boolean isFlightSelected(String selectedFlight) {
        return selectedFlight != null;
    }

    private JButton createButton(String buttonName) {
        return ui.button(buttonName, 100, 30);
    }

    private void initializeResultPanel(JPanel mainPanel) {
        JScrollPane scrollPane = initializeResultPanel();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JScrollPane initializeResultPanel() {
        createResultPanel();
        JScrollPane scrollPane = ui.scrollPanel(resultPanel);
        resultLabel = ui.label("No results to show");
        resultPanel.add(resultLabel);
        resultPanel.repaint();
        return scrollPane;
    }

    private void createResultPanel() {
        resultPanel = ui.flowLayoutPanel(FlowLayout.CENTER, 20, 10);
        resultPanel.setPreferredSize(new Dimension(800, 400));
    }

    private void displayErrorMessage(String error) {
        ui.showMessageDialog(this, "Error: " + error);
    }
}

