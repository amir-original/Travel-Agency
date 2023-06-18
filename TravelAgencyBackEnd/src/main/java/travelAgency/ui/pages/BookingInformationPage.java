package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.controller.ReservationController;
import travelAgency.domain.passenger.*;
import travelAgency.domain.flight.FlightDto;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.reservation.Reservation;
import travelAgency.ui.App;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BookingInformationPage extends JFrame {


    public static final String BOOKING_SUCCESSFUL = "Booking successful!";
    public static final String BOOKING_FAIL = "Booking Fail!";

    private final FlightDto selectedFlight;
    private final ReservationController reservationController;
    private final int numberOfTickets;
    private final UiComponents ui;
    private JTextField identityNumber;
    private JTextField firstName;
    private JTextField lastName;
    private JDateChooser birthdayPicker;
    private JTextField phoneNumber;
    private JTextField postalCode;
    private JTextField address;
    private JTextField city;

    public BookingInformationPage(FlightDto flight,
                                  ReservationController reservationController,
                                  int travelers) {
        selectedFlight = flight;
        this.reservationController = reservationController;
        this.numberOfTickets = travelers;
        this.ui = new UiComponents();
        setupPage();
        createComponentsAndAddToPage();
        pack();
        setVisible(true);
    }

    private void setupPage() {
        setTitle("Reservation Information");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 800);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void createComponentsAndAddToPage() {
        createInfoFields();
        createButtons();
    }

    private void createInfoFields() {
        JPanel inputPanel = createPanel();

        createFirstNameField(inputPanel);

        createLastNameField(inputPanel);

        createBirthdayField(inputPanel);

        createCityField(inputPanel);

        createAddressField(inputPanel);

        createPassportNumberField(inputPanel);

        createPostalCodeField(inputPanel);

        createPhoneNumberField(inputPanel);

        add(inputPanel, BorderLayout.LINE_START);
    }

    @NotNull
    private JPanel createPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 3, 10, 20));
        inputPanel.setPreferredSize(new Dimension(650, 400));
        return inputPanel;
    }

    private void createPassportNumberField(JPanel inputPanel) {
        identityNumber = createField(inputPanel, "Identity Number:");
    }

    private void createFirstNameField(JPanel inputPanel) {
        firstName = createField(inputPanel, "First Name:");
    }

    private void createLastNameField(JPanel inputPanel) {
        lastName = createField(inputPanel, "Last Name:");
    }

    private void createBirthdayField(JPanel inputPanel) {
        JLabel birthdayLabel = ui.label("Date withNationalCode Birth:");
        birthdayPicker = ui.dateChooser(150, 30);
        inputPanel.add(birthdayLabel);
        inputPanel.add(birthdayPicker);
        inputPanel.add(new JLabel());
    }

    private void createPhoneNumberField(JPanel inputPanel) {
        phoneNumber = createField(inputPanel, "PhoneNumber Number:");
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
        return textInput;
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createBackButton(buttonPanel);
        createBookButton(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void createBookButton(JPanel buttonPanel) {
        JButton bookButton = ui.button("Book");
        buttonPanel.add(bookButton);
        bookActionToBookButton(bookButton);
    }

    private void bookActionToBookButton(JButton bookButton) {
        bookButton.addActionListener(e -> {
            List<String> errors = canBookingReservation();
            if (errors.isEmpty()) {
                processReservationBooking();
            } else {
                errors.forEach(this::showMessageDialog);
            }
        });
    }

    private void processReservationBooking() {
        try {
            final Reservation reservation = bookReservation();
            processSuccessfulBooking(reservation);
        } catch (Exception exception) {
            showMessageDialog(BOOKING_FAIL);
        }
    }

    private Reservation bookReservation() {
        final PassengerDto passenger = createPassenger();
        final ReservationInformation reservationInformation =
                new ReservationInformation(selectedFlight,passenger,numberOfTickets);

        return reservationController.book(reservationInformation);
    }

    private List<String> canBookingReservation() {
        List<String> errors = new LinkedList<>();
        validateFields(errors);
        validatePassenger(errors);
        return errors;
    }

    private void validatePassenger(List<String> errors) {
        try {
            createPassenger();
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
    }

    private void validateFields(List<String> errors) {
        if (selectedFlight == null) {
            errors.add("no flight available!");
        } else if (numberOfTickets <= 0) {
            errors.add("number withNationalCode ticket should be greeter than 0");
        } else if (birthdayPicker.getDate() == null) {
            errors.add("birthday field must not be null!");
        }
    }

    private void processSuccessfulBooking(Reservation reservation) {
        final BoardingPass boardingPass = new BoardingPass();
        showMessageDialog(BOOKING_SUCCESSFUL);
        boardingPass.printTicket(reservation);
        dispose();
    }

    private PassengerDto createPassenger() {
        return PassengerDtoBuilder
                .passenger()
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .withNationalCode(identityNumber.getText())
                .withPhoneNumber(phoneNumber.getText())
                .ofCity(city.getText())
                .withAddress(address.getText())
                .withZipcode(postalCode.getText())
                .withBirthday(getLocalDate(birthdayPicker.getDate()))
                .build();
    }

    private LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void showMessageDialog(String message) {
        ui.showMessageDialog(this, message);
    }

    private void createBackButton(JPanel buttonPanel) {
        JButton backButton = ui.button("Back");
        buttonPanel.add(backButton);
        goToHomePage(backButton);
    }

    private void goToHomePage(JButton backButton) {
        backButton.addActionListener(e -> {
            new App().run();
            dispose();
        });
    }
}

