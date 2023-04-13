package travelAgency.ui;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;

public class BookingInfoPage extends JFrame {

    private UiComponents ui;

    public void BookingInfoPage() {
        setupPage();

        createComponentsAndAddToPage();

        pack();
        setVisible(true);
    }

    private void setupPage() {
        setTitle("Booking Information");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        this.ui = new UiComponents();
    }

    private void createComponentsAndAddToPage() {
        createInfoFields();
        createButtons();
    }

    private void createInfoFields() {
        JPanel inputPanel =  new JPanel();
        inputPanel.setLayout(new GridLayout(0,3,10,20));
        inputPanel.setPreferredSize(new Dimension(650,400));

        createFirstNameField(inputPanel);

        createLastNameField(inputPanel);

        createBirthdayField(inputPanel);

        createCityField(inputPanel);

        createAddressField(inputPanel);

        createPostalCodeField(inputPanel);

        createPhoneNumberField(inputPanel);

        add(inputPanel,BorderLayout.LINE_START);
    }

    private void createFirstNameField(JPanel inputPanel) {
        createField(inputPanel,"First Name:");
    }

    private void createLastNameField(JPanel inputPanel) {
        createField(inputPanel, "Last Name:");
    }

    private void createBirthdayField(JPanel inputPanel) {
        JLabel birthdayLabel = ui.label("Date of Birth:");
        JDatePickerImpl datePicker = ui.datePicker();
        inputPanel.add(birthdayLabel);
        inputPanel.add(datePicker);
        inputPanel.add(new JLabel());
    }

    private void createPhoneNumberField(JPanel inputPanel) {
        createField(inputPanel, "Phone Number:");
    }

    private void createPostalCodeField(JPanel inputPanel) {
        createField(inputPanel, "Postal Code:");
    }

    private void createAddressField(JPanel inputPanel) {
        createField(inputPanel, "Address:");
    }

    private void createCityField(JPanel inputPanel) {
        createField(inputPanel, "City:");
    }

    private void createField(JPanel inputPanel, String label) {
        JLabel phoneLabel = ui.label(label);
        JTextField phoneField = ui.textInput(10);

        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel());
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createBookButton(buttonPanel);
        createBackButton(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void createBookButton(JPanel buttonPanel) {
        JButton bookButton = ui.button("Book");
        buttonPanel.add(bookButton);
        bookActionToBookButton(bookButton);
    }

    private void bookActionToBookButton(JButton bookButton) {
        bookButton.addActionListener(e -> {
            // TODO: add booking logic here
            JOptionPane.showMessageDialog(this, "Booking successful!");
            dispose(); // close frame after successful booking
        });
    }

    private void createBackButton(JPanel buttonPanel) {
        JButton backButton = ui.button("Back");
        buttonPanel.add(backButton);
        goBackToFlightBookingPageAction(backButton);
    }

    private void goBackToFlightBookingPageAction(JButton backButton) {
        backButton.addActionListener(e -> {
           // new BookingFlightPage();
            dispose(); // close current frame
        });
    }

    public static void main(String[] args) {
        final BookingInfoPage bookingInfoPage = new BookingInfoPage();
        bookingInfoPage.BookingInfoPage();
    }
}

