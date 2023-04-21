package travelAgency.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ReservationSearchPage extends JFrame implements ActionListener {
    private final JPanel flightNumberPanel;
    private final JPanel reservationNumberPanel;
    private JLabel searchLabel, reservationNumberLabel, flightNumberLabel, firstNameLabel, birthdayLabel;
    private JTextField reservationNumberField, flightNumberField, firstNameField, birthdayField;
    private JButton searchButton, cancelButton, backButton;
    private JTable resultTable;
    private JComboBox<String> searchTypeComboBox;

    public ReservationSearchPage() {
        setTitle("Reservation Search");
        setSize(900
                , 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchLabel = new JLabel("Search by:");
        searchPanel.add(searchLabel);
        String[] searchTypes = {"Reservation Number", "Flight Number and Passenger Information"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchTypeComboBox.addActionListener(this); // add ActionListener
        searchPanel.add(searchTypeComboBox);

        reservationNumberPanel = new JPanel(new FlowLayout());
        reservationNumberLabel = new JLabel("Reservation Number:");
        reservationNumberField = new JTextField(10);
        reservationNumberPanel.add(reservationNumberLabel);
        reservationNumberPanel.add(reservationNumberField);

        flightNumberPanel = new JPanel(new FlowLayout());
        flightNumberLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField(10);
        firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(10);
        birthdayLabel = new JLabel("Birthday (yyyy-mm-dd):");
        birthdayField = new JTextField(10);
        flightNumberPanel.add(flightNumberLabel);
        flightNumberPanel.add(flightNumberField);
        flightNumberPanel.add(firstNameLabel);
        flightNumberPanel.add(firstNameField);
        flightNumberPanel.add(birthdayLabel);
        flightNumberPanel.add(birthdayField);
        // hide fields initially
        flightNumberPanel.setVisible(false);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        cancelButton = new JButton("Cancel Flight");
        cancelButton.setEnabled(false);
        cancelButton.addActionListener(this);
        backButton = new JButton("Back to Home");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);

        String[] columnNames = {"Reservation Number", "Passenger Name", "Flight Number", "Origin", "Destination", "Number of Tickets"};
        Object[][] data = {
                {"123456", "John Smith", "AA1234", "New York", "Los Angeles", 2},
                {"654321", "Jane Doe", "UA5678", "Chicago", "San Francisco", 1}
        };
        resultTable = new JTable(data, columnNames);
        JScrollPane resultTableScrollPane = new JScrollPane(resultTable);

        mainPanel.add(searchPanel);
        mainPanel.add(reservationNumberPanel);
        mainPanel.add(flightNumberPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(resultTableScrollPane);

        add(mainPanel);

        setVisible(true);
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
                String birthday = birthdayField.getText();
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
                flightNumberPanel.setVisible(false);
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

    public static void main(String[] args) {
        new ReservationSearchPage();
    }
}

