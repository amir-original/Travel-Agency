package travelAgency.ui;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.FlightLocation;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.flight.FlightSchedule;
import travelAgency.services.BookingReservation;
import travelAgency.services.city.CityService;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.flight.FlightService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import static java.lang.String.format;

public class BookingFlightPage extends JFrame {

    private final FlightSearchResult flightSearchResult;
    private JLabel resultLabel;
    private JComboBox<String> originComboBox, destinationComboBox;
    private JButton backButton, searchButton, nextButton;

    private UiComponents ui;
    private final CityService cityService;
    private final BookingListService bookingListService;
    private final FlightService flightService;
    private final BookingReservation bookingReservation;
    private JDateChooser departureDatePicker;
    private JDateChooser arrivalDatePicker;
    private JPanel resultPanel;
    private JComboBox<String> currencyConverter;
    private JSpinner passengersSpinner;


    public BookingFlightPage(CityService cityService,
                             BookingListService bookingListService,
                             FlightService flightService,
                             BookingReservation bookingReservation) {

        this.cityService = cityService;
        this.bookingListService = bookingListService;
        this.flightService = flightService;
        this.bookingReservation = bookingReservation;

        createBookingFlightPage();
        flightSearchResult = new FlightSearchResult();
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
        setTitle("Flight Booking Page");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @NotNull
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        return mainPanel;
    }

    private void createComponents(JPanel mainPanel) {
        createHeaderPanel(mainPanel);

        createFooterPanel(mainPanel);

        createResultPanel(mainPanel);
    }

    private void createHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        createFromField(headerPanel);

        createToField(headerPanel);

        createDepartureField(headerPanel);

        createArrivalField(mainPanel, headerPanel);

        createPassengersField(headerPanel);
    }

    private void createFromField(JPanel headerPanel) {
        JLabel originLabel = ui.label("From:");
        originComboBox = ui.dropdown(cityService.citiesArray(), 100, 30);
        originComboBox.setSelectedItem(cityService.getCity("tehran"));
        headerPanel.add(originLabel);
        headerPanel.add(originComboBox);
    }

    private void createToField(JPanel headerPanel) {
        JLabel destinationLabel = ui.label("To:");
        destinationComboBox = ui.dropdown(cityService.citiesArray(), 100, 30);
        headerPanel.add(destinationLabel);
        headerPanel.add(destinationComboBox);
    }

    private void createDepartureField(JPanel headerPanel) {
        JLabel departureLabel = ui.label("Departure:");
        departureDatePicker = ui.dateChooser(150, 30);
        disableDatesBeforTodaysDate(departureDatePicker);
        headerPanel.add(departureLabel);
        headerPanel.add(departureDatePicker);
    }

    private void createArrivalField(JPanel mainPanel, JPanel headerPanel) {
        JLabel arrivalLabel = ui.label("Arrival:");
        arrivalDatePicker = ui.dateChooser(150, 30);
        disableDatesBeforTodaysDate(arrivalDatePicker);
        headerPanel.add(arrivalLabel);
        headerPanel.add(arrivalDatePicker);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void disableDatesBeforTodaysDate(JDateChooser dateChooser) {
        dateChooser.setMinSelectableDate(Calendar.getInstance().getTime());
    }

    private void createPassengersField(JPanel headerPanel) {
        JLabel passengersLabel = ui.label("Passengers:");
        passengersSpinner = ui.inputNumber(1, 1, 5, 50, 30);
        headerPanel.add(passengersLabel);
        headerPanel.add(passengersSpinner);
    }


    private void createFooterPanel(JPanel mainPanel) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        createFooterButtons(footerPanel);

        footerPanel.add(backButton);
        footerPanel.add(searchButton);
        footerPanel.add(nextButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void createFooterButtons(JPanel footerPanel) {
        createCurrencyConverterField(footerPanel);

        createBackButton();

        createSearchButton();

        createNextButton();
    }

    private void createBackButton() {
        backButton = ui.button("Back", 100, 30);
        goBackToHomePageAction();
    }

    private void createSearchButton() {
        searchButton = ui.button("Search", 100, 30);

        addActionToSearchButton();
    }

    private void addActionToSearchButton() {
        searchButton.addActionListener(e -> {
            // Perform searchFlights and display results in resultLabel
            displayResultOfSearchFlightsInResultLabel();

            // TODO searchFlights flight
            final FlightPlan searchFlightPlan = getFlightPlan();

            final Object exchangeRate = currencyConverter.getSelectedItem();

            flightSearchResult.showFlightsInfo(
                    flightService.searchFlights(searchFlightPlan),
                    bookingListService.getAllReservations(),
                    exchangeRate
            );

            resultPanel.add(flightSearchResult);

            nextButton.setEnabled(true);
        });
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
        System.out.println(departure);
        System.out.println(arrival);
        return new FlightSchedule(departure, arrival);
    }

    @NotNull
    private FlightLocation getFlightLocation() {
        final String from = (String) originComboBox.getSelectedItem();
        String to = (String) destinationComboBox.getSelectedItem();
        return new FlightLocation(cityService.getCity(from), cityService.getCity(to));
    }

    private void displayResultOfSearchFlightsInResultLabel() {
        final String text = format("Showing results for %s to %s ",
                originComboBox.getSelectedItem(),
                destinationComboBox.getSelectedItem());

        resultLabel.setText(text);
    }

    private void goToNextPageAction() {
        nextButton.addActionListener(e -> {
            // Go to next page to book flight
            final String selectedFlight = flightSearchResult.getSelectedFlight();
            new BookingInfoPage(flightService.findFlight(selectedFlight),
                    bookingReservation,
                    cityService, (int) passengersSpinner.getValue()
            );
            dispose();
        });
    }

    private void createNextButton() {
        nextButton = ui.button("Next", 100, 30);
        nextButton.setEnabled(false);
        goToNextPageAction();
    }

    private void goBackToHomePageAction() {
        backButton.addActionListener(e -> {
            // Go back to previous page
            new App();
            dispose();
        });
    }

    private void createResultPanel(JPanel mainPanel) {
        resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        resultPanel.setPreferredSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        resultLabel = ui.label("No results to show");

        // TODO show information
        resultPanel.repaint();
        resultPanel.add(resultLabel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createCurrencyConverterField(JPanel footerPanel) {
        final JLabel converterLabel = ui.label("Currency");
        final String[] values = {"IRR", "USD"};
        currencyConverter = ui.dropdown(values, 100, 30);
        footerPanel.add(converterLabel);
        footerPanel.add(currencyConverter);
    }

}

