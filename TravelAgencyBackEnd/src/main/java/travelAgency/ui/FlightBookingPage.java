package travelAgency.ui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlightBookingPage extends JFrame {

    private JLabel originLabel, destinationLabel, departureLabel, arrivalLabel, passengersLabel, resultLabel;
    private JComboBox<String> originComboBox, destinationComboBox;
    private JDateChooser departureDatePicker, arrivalDatePicker;
    private JSpinner passengersSpinner;
    private JButton backButton, searchButton, nextButton;

    public FlightBookingPage() {
        setTitle("Flight Booking Page");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Create header panel with origin and destination fields
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        originLabel = new JLabel("From:");
        headerPanel.add(originLabel);
        originComboBox = new JComboBox<>(new String[]{"New York", "Los Angeles", "Chicago", "Houston", "Miami"});
        originComboBox.setPreferredSize(new Dimension(100,30));
        headerPanel.add(originComboBox);
        destinationLabel = new JLabel("To:");
        headerPanel.add(destinationLabel);
        destinationComboBox = new JComboBox<>(new String[]{"London", "Paris", "Tokyo", "Sydney", "Beijing"});
        destinationComboBox.setPreferredSize(new Dimension(100,30));
        headerPanel.add(destinationComboBox);

        departureLabel = new JLabel("Departure:");
        headerPanel.add(departureLabel);
        departureDatePicker = new JDateChooser();
        departureDatePicker.setPreferredSize(new Dimension(150,30));

        headerPanel.add(departureDatePicker);
        arrivalLabel = new JLabel("Arrival:");
        headerPanel.add(arrivalLabel);
        arrivalDatePicker = new JDateChooser();
        arrivalDatePicker.setPreferredSize(new Dimension(150,30));
        headerPanel.add(arrivalDatePicker);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        passengersLabel = new JLabel("Passengers:");
        headerPanel.add(passengersLabel);
        passengersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        passengersSpinner.setPreferredSize(new Dimension(50,30));
        headerPanel.add(passengersSpinner);


        // Create footer panel with passengers field and buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        final JLabel converterLabel = new JLabel("Currency");
        final JComboBox<String> currencyConverter = new JComboBox<>(new String[]{"IRR ريال", "USD $"});
        currencyConverter.setPreferredSize(new Dimension(100,30));
        footerPanel.add(converterLabel);
        footerPanel.add(currencyConverter);
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100,30));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to previous page
                new HomePage();
                dispose();
            }
        });
        footerPanel.add(backButton);
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100,30));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform search and display results in resultLabel
                resultLabel.setText("Showing results for " + originComboBox.getSelectedItem() + " to " + destinationComboBox.getSelectedItem());
                nextButton.setEnabled(true);
            }
        });
        footerPanel.add(searchButton);
        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.setPreferredSize(new Dimension(100,30));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go to next page to book flight
            }
        });
        footerPanel.add(nextButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Create result panel with result label
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        resultPanel.setPreferredSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(resultPanel);


        resultLabel = new JLabel("No results to show");
        resultPanel.add(resultLabel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args){
        new FlightBookingPage();
    }

}

