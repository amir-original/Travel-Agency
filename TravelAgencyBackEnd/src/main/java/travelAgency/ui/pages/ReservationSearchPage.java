package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.ui.App;
import travelAgency.ui.component.UiComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.util.Arrays.stream;

public class ReservationSearchPage extends JFrame implements ActionListener {
    private JPanel flightNumberPanel;
    private JPanel reservationNumberPanel;
    private final ReservationListService reservationListService;
    private JTextField reservationNumberField;
    private JTextField flightNumberField;
    private JTextField firstNameField;
    private JDateChooser birthdayField;
    private JButton searchButton, cancelButton, backButton;
    private JComboBox<String> searchTypeComboBox;
    private final UiComponents ui;

    public ReservationSearchPage(ReservationListService reservationListService) {
        this.reservationListService = reservationListService;
        this.ui = new UiComponents();
        setup();
        init();
    }

    private void setup() {
        setTitle("Reservation Search");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void init() {
        JPanel mainPanel = ui.createBoxLayoutPanel(BoxLayout.Y_AXIS);
        var components = createMainPanelComponents();
        stream(components).forEach(mainPanel::add);

        add(mainPanel);
        setVisible(true);
    }

    private Component[] createMainPanelComponents() {
        JPanel searchPanel = createSearchDropdownPanel();

        createReservationNumberPanelField();

        createFlightNumberPanelFields();

        hideFlightNumberPanel();

        final JPanel buttonPanel = createButtonPanel();

        JScrollPane resultTablePanel = createResultTable();

        return new Component[]{searchPanel, reservationNumberPanel, flightNumberPanel, buttonPanel, resultTablePanel};
    }

    @NotNull
    private JScrollPane createResultTable() {
        String[] columnNames = {"Reservation Number", "Passenger Name", "Flight Number", "Origin", "Destination", "Number of Tickets"};
        Object[][] data = {
                {"123456", "John Smith", "AA1234", "New York", "Los Angeles", 2},
                {"654321", "Jane Doe", "UA5678", "Chicago", "San Francisco", 1}
        };

        JTable resultTable = new JTable(data, columnNames);
        return new JScrollPane(resultTable);
    }

    private void createFlightNumberPanelFields() {
        flightNumberPanel = ui.flowLayoutPanel();
        flightNumberField = createFlightPanelField("Flight Number:");
        firstNameField = createFlightPanelField("First Name:");
        createAndAddBirthdayField();
    }

    private void createAndAddBirthdayField() {
        JLabel birthdayLabel = ui.label("Birthday (yyyy-mm-dd):");
        birthdayField = ui.dateChooser(150, 20);
        flightNumberPanel.add(birthdayLabel);
        flightNumberPanel.add(birthdayField);
    }

    private JTextField createFlightPanelField(String fieldName) {
        return createAndAddField(flightNumberPanel, fieldName);
    }

    private void createReservationNumberPanelField() {
        reservationNumberPanel = ui.flowLayoutPanel();
        reservationNumberField = createAndAddField(reservationNumberPanel, "Reservation Number:");
    }

    private JTextField createAndAddField(JPanel panel, String fieldName) {
        final JLabel label = ui.label(fieldName);
        final JTextField field = ui.textInput(10);
        panel.add(label);
        panel.add(field);
        return field;
    }

    @NotNull
    private JPanel createSearchDropdownPanel() {
        JPanel searchPanel = ui.flowLayoutPanel();
        JLabel searchLabel = ui.label("Search by:");
        createSearchTypeDropdown();
        searchPanel.add(searchLabel);
        searchPanel.add(searchTypeComboBox);
        return searchPanel;
    }

    private void createSearchTypeDropdown() {
        String[] searchTypes = {"Reservation Number", "Flight Number and Passenger Information"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchTypeComboBox.addActionListener(this);
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createSearchButton();
        createCancelButton();
        createBackToHomeButton();
        buttonPanel.add(backButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private void createBackToHomeButton() {
        backButton = ui.button("Back to Home");
        backButton.addActionListener(this);
    }

    private void createCancelButton() {
        cancelButton = ui.button("Cancel Flight");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(this);
    }

    private void createSearchButton() {
        searchButton = ui.button("Search");
        searchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        String searchType = getSelectedSearchType();
        if (source == searchButton) {
            performSearch(searchType);
        } else if (source == cancelButton) {
            cancelSelectedFlight();
        } else if (source == backButton) {
            goBackToHomePage();
        } else if (source == searchTypeComboBox) {
            handleSearchTypeSelectionChanges(searchType);
        }
    }

    private void performSearch(String searchType) {
        if (isSearchByReservationNumber(searchType)) {
            performSearchByReservationNumber();
        } else if (isSearchByFlightAndPassengerInformation(searchType)) {
            performSearchByFlightNumberAndPassengerInformation();
        }
    }

    private void performSearchByFlightNumberAndPassengerInformation() {
        String flightNumber = flightNumberField.getText();
        String firstName = firstNameField.getText();
        String birthday = birthdayField.getDateFormatString();
        // perform search by flight number and passenger information
        // ...
    }

    private void performSearchByReservationNumber() {
        String reservationNumber = reservationNumberField.getText();
        // perform search by reservation number
        // ...
    }

    private void goBackToHomePage() {
        App app = new App();
        dispose();
    }

    private void cancelSelectedFlight() {
        // cancel selected flight
        // ...
    }

    private void handleSearchTypeSelectionChanges(String searchType) {
        if (isSearchByReservationNumber(searchType)) {
            showReservationNumberPanel();
            hideFlightNumberPanel();
        } else if (isSearchByFlightAndPassengerInformation(searchType)) {
            showFlightNumberPanel();
            hideReservationNumberPanel();
        }
    }

    private boolean isSearchByReservationNumber(String selectedSearchType) {
        return selectedSearchType.equals("Reservation Number");
    }

    private boolean isSearchByFlightAndPassengerInformation(String searchType) {
        return searchType.equals("Flight Number and Passenger Information");
    }

    @Nullable
    private String getSelectedSearchType() {
        return (String) searchTypeComboBox.getSelectedItem();
    }

    private void showReservationNumberPanel() {
        reservationNumberPanel.setVisible(true);
    }

    private void hideFlightNumberPanel() {
        flightNumberPanel.setVisible(false);
    }

    private void showFlightNumberPanel() {
        flightNumberPanel.setVisible(true);
    }

    private void hideReservationNumberPanel() {
        reservationNumberPanel.setVisible(false);
    }
}

