package travelAgency.ui;

import com.toedter.calendar.JDateChooser;
import travelAgency.domain.reservation.Reservation;
import travelAgency.services.reservation.ReservationListService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BookingListPage extends JFrame {

    private final ReservationListService reservationListService;
    private JPanel resultPanel;
    private JButton backButton;

    private UiComponents ui;
    private JTextField firstNameField;
    private JTextField flightNumberField;
    private JDateChooser birthdayField;
    private ReservationSearchResult reservationSearchResult;

    public BookingListPage(ReservationListService reservationListService) {
        this.reservationListService = reservationListService;
        createBookingListPage();
    }


    public void createBookingListPage() {
        setupPage();
        createComponentsAndAddToPage();
        pack();
        setVisible(true);

        reservationSearchResult = new ReservationSearchResult();
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
        add(resultPanel, BorderLayout.CENTER);
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
        resultPanel.add(new JLabel("Hello"));
        JButton searchButton = ui.button("Search");
        buttonPanel.add(searchButton);
        searchButton.addActionListener(e ->
                {
                    final LocalDate localDateOf = getLocalDateOf(birthdayField.getDate());
                    System.out.println(localDateOf);
                    final Reservation searchReservation =
                            reservationListService.search(flightNumberField.getText(),
                                    firstNameField.getText(),
                                    localDateOf);

                    System.out.println(searchReservation);
                    reservationSearchResult.showReservationInfo(searchReservation);
                    resultPanel.add(reservationSearchResult);
                }
        );
    }

    private LocalDate getLocalDateOf(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private void createBackButton(JPanel buttonPanel) {
        backButton = ui.button("Back to Home");
        buttonPanel.add(backButton);
        goToHomePageAction();
    }

    private void createResultPanel(JPanel mainPanel) {
        resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(800,400));
        final JScrollPane jScrollPane = ui.scrollPanel(resultPanel);

        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel("Search Results"));
        mainPanel.add(jScrollPane);
    }

    private void createFirstNameField(JPanel mainPanel) {
        JLabel firstNameLabel = ui.label("First Name:");
        firstNameField = ui.textInput(20);
        mainPanel.add(new JLabel());
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);
    }

    private void createFlightNumberField(JPanel mainPanel) {
        JLabel flightNumberLabel = ui.label("Flight Number:");
        flightNumberField = ui.textInput(20);
        mainPanel.add(flightNumberLabel);
        mainPanel.add(flightNumberField);
    }

    private void createBirthdayDatePicker(JPanel mainPanel) {
        JLabel birthdayLabel = ui.label("Date of Birth:");
        birthdayField = ui.dateChooser(150,30);
        mainPanel.add(new JLabel());
        mainPanel.add(birthdayLabel);
        mainPanel.add(birthdayField);
    }

}
