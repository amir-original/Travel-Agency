package travelAgency.ui;

import com.toedter.calendar.JDateChooser;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.services.BookingReservation;
import travelAgency.services.city.CityService;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;

public class BookingInfoPage extends JFrame {


    private final Flight selectFlight;
    private final BookingReservation bookingReservation;
    private final CityService cityService;
    private final int travelers;
    private UiComponents ui;
    private JTextField firstName;
    private JTextField lastName;
    private JDateChooser birthdayPicker;
    private JTextField phoneNumber;
    private JTextField postalCode;
    private JTextField address;
    private JTextField city;

    public BookingInfoPage(Flight flight,
                           BookingReservation bookingReservation,
                           CityService cityService,
                           int travelers) {
        selectFlight = flight;
        this.bookingReservation = bookingReservation;
        this.cityService = cityService;
        this.travelers = travelers;
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
        firstName = createField(inputPanel, "First Name:");
    }

    private void createLastNameField(JPanel inputPanel) {
        lastName = createField(inputPanel, "Last Name:");
    }

    private void createBirthdayField(JPanel inputPanel) {
        JLabel birthdayLabel = ui.label("Date of Birth:");
        birthdayPicker = ui.dateChooser(150,30);
        inputPanel.add(birthdayLabel);
        inputPanel.add(birthdayPicker);
        inputPanel.add(new JLabel());
    }

    private void createPhoneNumberField(JPanel inputPanel) {
        phoneNumber = createField(inputPanel, "Phone Number:");
    }

    private void createPostalCodeField(JPanel inputPanel) {
        postalCode = createField(inputPanel, "Postal Code:");
    }

    private void createAddressField(JPanel inputPanel) {
        address = createField(inputPanel, "Address:");
    }

    private void createCityField(JPanel inputPanel) {
        city = createField(inputPanel, "City:");
    }

    private JTextField createField(JPanel inputPanel, String label) {
        JLabel phoneLabel = ui.label(label);
        JTextField textInput = ui.textInput(10);

        inputPanel.add(phoneLabel);
        inputPanel.add(textInput);
        inputPanel.add(new JLabel());
        return  textInput;
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
            final Passenger passenger = PassengerBuilder.passenger()
                    .withId("Bg-8748")
                    .firstName(firstName.getText())
                    .lastName(lastName.getText())
                    .withPhoneNumber(phoneNumber.getText())
                    .withAddress(address.getText())
                    .withZipcode(postalCode.getText())
                    .ofCity(city.getText())
                    .withBirthday(birthdayPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .build();
            final BookingInformation bookingInformation = new BookingInformation(selectFlight, passenger,travelers);

            bookingReservation.book(bookingInformation);
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
}

