package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.ui.component.UiComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

    private void init() {
        JPanel mainPanel = createBoxLayoutPanel();

        JPanel searchPanel = createSearchDropdownPanel();

        createReservationNumberPanelField();

        createFlightNumberPanelFields();

        // hide fields initially
        hideFlightNumberPanel();

        JPanel buttonPanel = new JPanel(new FlowLayout());
        createAndAddButtons(buttonPanel);

        JScrollPane resultTableScrollPane = createResultTable();

        mainPanel.add(searchPanel);
        mainPanel.add(reservationNumberPanel);
        mainPanel.add(flightNumberPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(resultTableScrollPane);

        add(mainPanel);

        setVisible(true);
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

    private void hideFlightNumberPanel() {
        flightNumberPanel.setVisible(false);
    }

    private void createFlightNumberPanelFields() {
        flightNumberPanel = new JPanel(new FlowLayout());
        flightNumberField = createFlightPanelField("Flight Number:");
        firstNameField = createFlightPanelField("First Name:");
        createAndAddBirthdayField();
    }

    private void createAndAddBirthdayField() {
        JLabel birthdayLabel = ui.label("Birthday (yyyy-mm-dd):");
        birthdayField = ui.dateChooser(150,20);
        flightNumberPanel.add(birthdayLabel);
        flightNumberPanel.add(birthdayField);
    }

    private JTextField createFlightPanelField(String fieldName) {
       return createAndAddField(flightNumberPanel,fieldName);
    }

    private void createReservationNumberPanelField() {
        reservationNumberPanel = ui.flowLayoutPanel();
        reservationNumberField = createAndAddField(reservationNumberPanel,"Reservation Number:");
    }

    private JTextField createAndAddField(JPanel panel,String fieldName) {
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

    @NotNull
    private JPanel createBoxLayoutPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        return mainPanel;
    }

    private void createAndAddButtons(JPanel buttonPanel) {
        createSearchButton();
        createCancelButton();
        createBackToHomeButton();
        buttonPanel.add(backButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
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

    private void setup() {
        setTitle("Reservation Search");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // perform search based on selected search type
            String searchType = (String) searchTypeComboBox.getSelectedItem();
            if (searchType.equals("Reservation Number")) {
                String reservationNumber = reservationNumberField.getText();
                // perform search by reservation number
                // ...
            } else if (searchType.equals("Flight Number and Passenger Information")) {
                String flightNumber = flightNumberField.getText();
                String firstName = firstNameField.getText();
                String birthday = birthdayField.getDateFormatString();
                // perform search by flight number and passenger information
                // ...
            }
        } else if (e.getSource() == cancelButton) {
            // cancel selected flight
            // ...
        } else if (e.getSource() == backButton) {
            // go back to home page
            //HomePage homePage = new HomePage();
            dispose();
        } else if (e.getSource() == searchTypeComboBox) {
            // handle search type selection changes
            String selectedSearchType = (String) searchTypeComboBox.getSelectedItem();
            if (selectedSearchType.equals("Reservation Number")) {

                // hide flight number panel and passenger information fields
                reservationNumberPanel.setVisible(true);
                hideFlightNumberPanel();
                repaint();
            } else if (selectedSearchType.equals("Flight Number and Passenger Information")) {
                System.out.println("Im here");
                // show flight number panel and passenger information fields
                flightNumberPanel.setVisible(true);
                reservationNumberPanel.setVisible(false);
                repaint();
            }
        }
    }
}

