package travelAgency.ui;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightLocation;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.flight.FlightSchedule;
import travelAgency.services.BookingReservation;
import travelAgency.services.city.CityService;
import travelAgency.services.flight.FlightListService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import static java.lang.String.format;

public class BookingFlightPage extends JFrame {

    private final FlightSearchResultPanel flightSearchResult;
    private JLabel resultLabel;
    private JComboBox<String> originComboBox, destinationComboBox;
    private JButton backButton, searchButton, nextButton;

    private UiComponents ui;
    private final CityService cityService;
    private final FlightListService flightListService;
    private final BookingReservation bookingReservation;
    private JDateChooser departureDatePicker;
    private JDateChooser arrivalDatePicker;
    private JPanel resultPanel;
    private JComboBox<String> exchangeRate;
    private JSpinner passengersSpinner;


    public BookingFlightPage(CityService cityService,
                             FlightListService flightListService,
                             BookingReservation bookingReservation,
                            ExchangeRateDAO exchangeRateDAO) {

        this.cityService = cityService;
        this.flightListService = flightListService;
        this.bookingReservation = bookingReservation;
        flightSearchResult = new FlightSearchResultPanel(exchangeRateDAO);

        createBookingFlightPage();
    }

    private void createBookingFlightPage() {
        setupPage();

        JPanel mainPanel = createMainPanel();

        createComponents(mainPanel);

        pack();
        setVisible(true);
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

    private JComboBox<String> AddCityDropdownToPanel(JPanel headerPanel, String labelText) {
        JLabel label = ui.label(labelText);
        JComboBox<String> cityDropdown = getAirportListsDropdown();
        headerPanel.add(label);
        headerPanel.add(cityDropdown);
        return cityDropdown;
    }

    public JComboBox<String> getAirportListsDropdown() {
        return ui.dropdown(cityService.citiesArray(), 100, 30);
    }

    private void createDepartureField(JPanel headerPanel) {
        departureDatePicker = createAndAddDatePickerToHeaderPanel(headerPanel, "Departure:");
    }

    private void createArrivalField(JPanel headerPanel) {
        arrivalDatePicker = createAndAddDatePickerToHeaderPanel(headerPanel, "Arrival:");
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
        final String[] values = {"IRR", "USD"};
        exchangeRate = ui.dropdown(values, 100, 30);
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
        addActionToSearchButton();
    }

    private void addActionToSearchButton() {
        searchButton.addActionListener(e -> performFlightSearchAndDisplayResults());
    }

    private void performFlightSearchAndDisplayResults() {
        displaySearchResultTitle();

        final Object exchangeRate = this.exchangeRate.getSelectedItem();

        final List<Flight> searchFlights = flightListService.searchFlights(getFlightPlan());

        flightSearchResult.showFlightsInfo(searchFlights, exchangeRate);

        updateFlightSearchResults(flightSearchResult);

        enableNextButtonIfSearchResultsExist(searchFlights);
    }

    private void displaySearchResultTitle() {
        final Object origin = originComboBox.getSelectedItem();
        final Object destination = destinationComboBox.getSelectedItem();
        final String title = format("Showing results for %s to %s ", origin, destination);
        resultLabel.setText(title);
    }

    private void updateFlightSearchResults(FlightSearchResultPanel flightSearchResult) {
        resultPanel.removeAll();
        resultPanel.add(flightSearchResult);
        resultPanel.repaint();
    }

    private void enableNextButtonIfSearchResultsExist(List<Flight> searchFlights) {
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
    private FlightPlan getFlightPlan() {
        final FlightLocation location = getFlightLocation();
        final FlightSchedule schedule = getFlightSchedule();
        return new FlightPlan(location, schedule);
    }

    @NotNull
    private FlightSchedule getFlightSchedule() {
        LocalDate departure = departureDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate arrival = arrivalDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new FlightSchedule(departure, arrival);
    }

    @NotNull
    private FlightLocation getFlightLocation() {
        final String from = (String) originComboBox.getSelectedItem();
        String to = (String) destinationComboBox.getSelectedItem();
        return new FlightLocation(cityService.getCity(from), cityService.getCity(to));
    }

    private void goToNextPageAction() {
        nextButton.addActionListener(e -> goToReservationInformationPage());
    }

    private void goToReservationInformationPage() {
        final Flight flight = getFlight();
        new ReservationInformationPage(flight, bookingReservation, cityService, getTravelers());
        dispose();
    }

    private int getTravelers() {
        return (int) passengersSpinner.getValue();
    }

    private Flight getFlight() {
        final String selectedFlight = flightSearchResult.getSelectedFlight();
        validateFlightSelection(selectedFlight);
        return flightListService.findFlight(selectedFlight);
    }

    private void validateFlightSelection(String selectedFlight) {
        if (selectedFlight == null)
            ui.showMessageDialog(this, "Please select a flight to proceed.");
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
}

