package travelAgency.ui.pages;

import com.toedter.calendar.JDateChooser;
import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.domain.reservation.Reservation;
import travelAgency.services.BookingReservation;
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

    private final Flight selectFlight;
    private final BookingReservation reservationListService;
    private final int travelers;
    private final UiComponents ui;
    private JTextField passportNumber;
    private JTextField firstName;
    private JTextField lastName;
    private JDateChooser birthdayPicker;
    private JTextField phoneNumber;
    private JTextField postalCode;
    private JTextField address;
    private JTextField city;

    public BookingInformationPage(Flight flight, BookingReservation reservationListService, int travelers) {
        selectFlight = flight;
        this.reservationListService = reservationListService;
        this.travelers = travelers;
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
        passportNumber = createField(inputPanel, "Passport Number:");
    }

    private void createFirstNameField(JPanel inputPanel) {
        firstName = createField(inputPanel, "First Name:");
    }

    private void createLastNameField(JPanel inputPanel) {
        lastName = createField(inputPanel, "Last Name:");
    }

    private void createBirthdayField(JPanel inputPanel) {
        JLabel birthdayLabel = ui.label("Date of Birth:");
        birthdayPicker = ui.dateChooser(150, 30);
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
            if (!errors.isEmpty()) {
                errors.forEach(this::showMessageDialog);
                return;
            }
            attemptReservationBooking();
        });
    }

    private void attemptReservationBooking() {
        try {
            final ReservationInformation reservationInformation = getReservationInformation();
            final Reservation reservation = bookReservation(reservationInformation);
            processSuccessfulBooking(reservation);
        } catch (Exception exception) {
            showMessageDialog(BOOKING_FAIL);
        }
    }

    private List<String> canBookingReservation() {
        List<String> errors = new LinkedList<>();
        if (selectFlight == null) {
            errors.add("no flight available!");
        }
        if (travelers <= 0) {
            errors.add("number of ticket should be greeter than 0");
        }
        if (birthdayPicker.getDate() == null) {
            errors.add("birthday field must not be null!");
        }

        try {
            final Passenger passenger = getPassenger();
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return errors;
    }

    private void processSuccessfulBooking(Reservation reservation) {
        final BoardingPass dialog = new BoardingPass(reservation);
        showMessageDialog(BOOKING_SUCCESSFUL);
        dialog.printTicket();
        dispose();
    }

    private Reservation bookReservation(ReservationInformation reservationInformation) {
        return reservationListService.book(reservationInformation);
    }

    @NotNull
    private ReservationInformation getReservationInformation() {
        return new ReservationInformation(selectFlight, getPassenger(), travelers);
    }

    private Passenger getPassenger() {
        return PassengerBuilder
                .passenger()
                .withId(passportNumber.getText())
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .withPhoneNumber(phoneNumber.getText())
                .withAddress(address.getText())
                .withZipcode(postalCode.getText())
                .ofCity(city.getText())
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
