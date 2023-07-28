package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelAgency.infrastructure.user_interface.web.controller.ReservationOperations;
import travelAgency.model.reservation.Reservation;
import travelAgency.ui.App;
import travelAgency.ui.component.UiComponents;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import static java.util.Arrays.stream;

public class ReservationSearchPage extends JFrame implements ActionListener {
    private final ReservationOperations reservationController;
    private final JScrollPane resultTablePanel;
    private JPanel flightNumberPanel;
    private JPanel reservationNumberPanel;
    private JTextField reservationNumberField;
    private JTextField flightNumberField;
    private JTextField firstNameField;
    private JDateChooser birthdayField;
    private JButton searchButton, cancelButton, backButton;
    private JComboBox<String> searchTypeComboBox;
    private final UiComponents ui;
    private JTable jTable;
    private String reservationNumber;

    public ReservationSearchPage(ReservationOperations reservationController) {
        this.reservationController = reservationController;
        this.resultTablePanel = createResultTable();
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


        return new Component[]
                {searchPanel, reservationNumberPanel, flightNumberPanel, buttonPanel, resultTablePanel};
    }

    @NotNull
    private JScrollPane createResultTable() {
        JTable resultTable = createResultTable(new Object[][]{});
        return new JScrollPane(resultTable);
    }

    @NotNull
    private JTable createResultTable(Object[][] data) {
        return jTable = new JTable(data, getColumnNames());
    }

    @NotNull
    private String[] getColumnNames() {
        return new String[]
                {"Reservation Number",
                        "Passenger Name",
                        "Flight Number",
                        "Origin",
                        "Destination",
                        "Number Of Tickets"};
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
        if (isSearchByFlightAndPassengerInformation(searchType)) {
            performSearchByFlightNumberAndPassengerInformation();
        } else if (isSearchByReservationNumber(searchType)) {
            performSearchByReservationNumber();
        }
    }

    private void performSearchByFlightNumberAndPassengerInformation() {
        String flightNumber = flightNumberField.getText().strip();
        String firstName = firstNameField.getText().strip();
        final Date date = birthdayField.getDate();

        if (flightNumber.isEmpty()) {
            displayErrorMessage("flight Number must not be null or empty!");
        } else if (firstName.isEmpty()) {
            displayErrorMessage("passenger first name must not be null or empty!");
        } else if (date == null) {
            displayErrorMessage("please enter your correct birthdate!");
        } else {
            performReservationSearch(flightNumber, firstName, date);
        }
    }

    private void performReservationSearch(String flightNumber, String firstName, Date date) {
        final Reservation searchedReservation = searchReservation(flightNumber, firstName, date);

        handleReservationSearchResult(searchedReservation);
    }

    private Reservation searchReservation(String flightNumber, String firstName, Date birthday) {
        try {
            final LocalDate date = convertDateToLocalDate(birthday);
            return reservationController.search(flightNumber, firstName, date);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isReservationFound(Reservation reservation) {
        return reservation != null;
    }

    private LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void performSearchByReservationNumber() {
        String reservationNumber = reservationNumberField.getText().strip();

        if (reservationNumber.isEmpty()) {
            displayErrorMessage("please enter your reservation number");
        } else {
            performReservationSearch(reservationNumber);
        }

    }

    private void performReservationSearch(String reservationNumber) {
        Reservation searchedReservation = searchReservation(reservationNumber);

        handleReservationSearchResult(searchedReservation);
    }

    private void handleReservationSearchResult(Reservation searchedReservation) {
        if (isReservationFound(searchedReservation)) {
            processFoundReservation(searchedReservation);
        } else {
            displayErrorMessage("Not found Any Reservation with Entered information!");
        }
    }

    private void processFoundReservation(Reservation searchedReservation) {
        Object[][] result = searchedReservation.createReservationInfo();
        final JTable resultTable = createResultTable(result);
        updateResultTable(resultTable);
        EnableCancelButton(resultTable);
    }

    private Reservation searchReservation(String reservationNumber) {
        try {
            return reservationController.search(reservationNumber);
        } catch (Exception e) {
            return null;
        }
    }

    private void displayErrorMessage(String s) {
        ui.showMessageDialog(this, "Error: " + s);
    }

    private void displaySuccessMessage(String s) {
        ui.showMessageDialog(this, "Success: " + s);
    }

    private void updateResultTable(JTable table) {
        resultTablePanel.setViewportView(table);
        resultTablePanel.repaint();
        repaint();
    }

    private void EnableCancelButton(JTable table) {
        cancelButton.setEnabled(true);
        addClickListenerToTableRows(table);
    }

    private void goBackToHomePage() {
        App app = new App();
        app.run();
        dispose();
    }

    private void cancelSelectedFlight() {
        if (reservationNumber == null || reservationNumber.isEmpty()) {
            displayErrorMessage("Please select a reservation before canceling a flight.");
        } else {
            handleCancellationProcess();
        }
    }

    private void handleCancellationProcess() {
        try {
            reservationController.cancel(reservationNumber);
            removeResultTable();
            displaySuccessMessage("Cancellation successes!");
        } catch (Exception e) {
            displayErrorMessage("Cancellation failed!");
        }
    }

    private void removeResultTable() {
        updateResultTable(createResultTable(new Object[][]{}));
    }

    private void addClickListenerToTableRows(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            selectReservationNumberIfRowSelected(event, table.getSelectedRow());
        });
    }

    private void selectReservationNumberIfRowSelected(ListSelectionEvent event, int selectedRow) {
        if (!event.getValueIsAdjusting() && !isRowSelected(selectedRow))
            reservationNumber = getSelectReservationNumberValue(selectedRow);
    }

    private boolean isRowSelected(int selectedRow) {
        return selectedRow == -1;
    }

    private String getSelectReservationNumberValue(int selectedRow) {
        final int reservationColumn = 0;
        return (String) jTable.getValueAt(selectedRow, reservationColumn);
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

