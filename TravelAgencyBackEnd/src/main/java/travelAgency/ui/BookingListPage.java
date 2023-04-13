package travelAgency.ui;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import travelAgency.services.bookingList.BookingListService;

import javax.swing.*;
import java.awt.*;

public class BookingListPage extends JFrame {

    private final BookingListService bookingListService;
    private JScrollPane scrollPane;
    private JButton backButton;

    private UiComponents ui;

    public BookingListPage(BookingListService bookingListService) {
        this.bookingListService = bookingListService;
        createBookingListPage();
    }


    public void createBookingListPage() {
        setupPage();

        createComponentsAndAddToPage();

        pack();
        setVisible(true);

        // TODO: add search logic here
    }

    private void setupPage() {
        initPageConfig();
        this.ui = new UiComponents();
    }

    private void initPageConfig() {
        setTitle("Booking List");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void createComponentsAndAddToPage() {
        JPanel mainPanel =  new JPanel(new GridLayout(0, 3, 10, 10));

        createFlightNumberField(mainPanel);

        createFirstNameField(mainPanel);

        createBirthdayDatePicker(mainPanel);

        createResultPanel(mainPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        createButtons(buttonPanel);

        addComponentsToPage(mainPanel, buttonPanel);

    }

    private void addComponentsToPage(JPanel mainPanel, JPanel buttonPanel) {
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void goToHomePageAction() {
        backButton.addActionListener(e -> {
            new App();
            dispose(); // close current frame
        });
    }

    private void createButtons(JPanel buttonPanel) {
        createSearchButton(buttonPanel);
        createCancelButton(buttonPanel);
        createBackButton(buttonPanel);

    }

    private void createCancelButton(JPanel buttonPanel) {
        JButton cancelButton = ui.button("Cancel");
        buttonPanel.add(cancelButton);
    }

    private void createSearchButton(JPanel buttonPanel) {
        JButton searchButton = ui.button("Search");
        buttonPanel.add(searchButton);
    }

    private void createBackButton(JPanel buttonPanel) {
        backButton = ui.button("Back to Home");
        buttonPanel.add(backButton);
        goToHomePageAction();
    }

    private void createResultPanel(JPanel mainPanel) {
        scrollPane = ui.scrollPanel(800,400);
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Search Results"));
    }

    private void createFirstNameField(JPanel mainPanel) {
        JLabel firstNameLabel = ui.label("First Name:");
        JTextField firstNameField = ui.textInput(20);
        mainPanel.add(new JLabel());
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);
    }

    private void createFlightNumberField(JPanel mainPanel) {
        JLabel flightNumberLabel = ui.label("Flight Number:");
        JTextField flightNumberField = ui.textInput(20);
        mainPanel.add(flightNumberLabel);
        mainPanel.add(flightNumberField);
    }

    private void createBirthdayDatePicker(JPanel mainPanel) {
        JLabel birthdayLabel = ui.label("Date of Birth:");
        JDatePickerImpl datePicker = ui.datePicker();
        mainPanel.add(new JLabel());
        mainPanel.add(birthdayLabel);
        mainPanel.add(datePicker);
    }

}
