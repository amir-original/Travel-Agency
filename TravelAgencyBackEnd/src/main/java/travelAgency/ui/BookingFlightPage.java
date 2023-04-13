package travelAgency.ui;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.FlightLocation;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.flight.FlightSchedule;
import travelAgency.services.city.CityService;
import travelAgency.services.bookingList.BookingListService;
import travelAgency.services.flights.FlightService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class BookingFlightPage extends JFrame {

    private JLabel resultLabel;
    private JComboBox<String> originComboBox, destinationComboBox;
    private JButton backButton, searchButton, nextButton;

    private  UiComponents ui;
    private final CityService cityService;
    private final BookingListService bookingListService;
    private final FlightService flightService;
    private JDateChooser departureDatePicker;
    private JDateChooser arrivalDatePicker;
    private JPanel resultPanel;


    public BookingFlightPage(CityService cityService, BookingListService bookingListService, FlightService flightService) {
        this.cityService = cityService;
        this.bookingListService = bookingListService;
        this.flightService = flightService;

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
        headerPanel.add(departureLabel);
        headerPanel.add(departureDatePicker);
    }

    private void createArrivalField(JPanel mainPanel, JPanel headerPanel) {
        JLabel arrivalLabel = ui.label("Arrival:");
        arrivalDatePicker = ui.dateChooser(150,30);
        headerPanel.add(arrivalLabel);
        headerPanel.add(arrivalDatePicker);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createPassengersField(JPanel headerPanel) {
        JLabel passengersLabel = ui.label("Passengers:");
        JSpinner passengersSpinner = ui.inputNumber(1,1,5,50,30);
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
        backButton = ui.button("Back",100,30);
        goBackToHomePageAction();
    }

    private void createSearchButton() {
        searchButton = ui.button("Search",100,30);

        addActionToSearchButton();
    }

    private void addActionToSearchButton() {
        searchButton.addActionListener(e -> {
            // Perform search and display results in resultLabel
            final String text = "Showing results for " +
                    originComboBox.getSelectedItem()
                    + " to " +
                    destinationComboBox.getSelectedItem();


            // TODO search flight
            final FlightPanel flightPanel = new FlightPanel();

            final String from = (String) originComboBox.getSelectedItem();
            String to = (String) destinationComboBox.getSelectedItem();
            final FlightLocation location = new FlightLocation(cityService.getCity(from),cityService.getCity(to));
            LocalDate departure = departureDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate arrival = arrivalDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            final FlightSchedule schedule = new FlightSchedule(departure,arrival);
            final FlightPlan searchFlightPlan = new FlightPlan(location, schedule);

            flightPanel.showFlightsInfo(
                    flightService.search(searchFlightPlan),
                    flightService
            );

            resultPanel.add(flightPanel);



            resultLabel.setText(text);
            nextButton.setEnabled(true);
        });
    }

    private void goToNextPageAction() {
        nextButton.addActionListener(e -> {
            // Go to next page to book flight
        });
    }

    private void createNextButton() {
        nextButton = ui.button("Next",100,30);
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
        resultPanel.add(resultLabel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createCurrencyConverterField(JPanel footerPanel) {
        final JLabel converterLabel = ui.label("Currency");
        final String[] values = {"IRR ريال", "USD $"};
        final JComboBox<String> currencyConverter = ui.dropdown(values,100,30);
        footerPanel.add(converterLabel);
        footerPanel.add(currencyConverter);
    }

}

