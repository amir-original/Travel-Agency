package travelAgency.ui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingListPage extends JFrame {

    private JLabel birthdayLabel;
    private JDatePickerImpl datePicker;
    private JLabel firstNameLabel;
    private JTextField firstNameField;
    private JLabel flightNumberLabel;
    private JTextField flightNumberField;
    private JScrollPane scrollPane;
    private JButton searchButton;
    private JButton cancelButton;
    private JButton backButton;

    public void createBookingListPage() {
        setupPage();

        JPanel inputPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        createFlightNumberField();

        createFirstNameField();

        createBirthdayDatePicker();

        inputPanel.add(flightNumberLabel);
        inputPanel.add(flightNumberField);
        inputPanel.add(new JLabel());
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel());
        inputPanel.add(birthdayLabel);
        inputPanel.add(datePicker);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel("Search Results"));

        createResultPanel();

        JPanel buttonPanel = new JPanel(new FlowLayout());

        createButtons();

        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // TODO: add search logic here

        backButton.addActionListener(e -> {
            createHomePage();
            dispose(); // close current frame
        });
    }

    private void setupPage() {
        setTitle("Booking List");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout());
    }

    private void createButtons() {
        searchButton = new JButton("Search");
        cancelButton = new JButton("Cancel");
        backButton = new JButton("Back to Home");
    }

    private void createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(800, 400));
        scrollPane = new JScrollPane(resultPanel);
    }

    private void createFirstNameField() {
        firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
    }

    private void createFlightNumberField() {
        flightNumberLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField(20);
    }

    private void createBirthdayDatePicker() {
        birthdayLabel = new JLabel("Date of Birth:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);
    }

    private static void createHomePage() {
        new HomePage();
    }

    public static void main(String[] args) {
        new BookingListPage().createBookingListPage();
    }

}
