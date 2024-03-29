package travelAgency.ui.pages;

import travelAgency.infrastructure.user_interface.web.controller.ExchangeRateOperations;
import travelAgency.infrastructure.user_interface.web.controller.FlightOperations;
import travelAgency.infrastructure.user_interface.web.controller.ReservationOperations;
import travelAgency.infrastructure.libraries.city.CityService;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage extends JFrame {
    private JButton bookingFlightButton, bookingListButton;

    private final UiComponents uiComponents = new UiComponents();


    public HomePage() {
        createHomePage();
    }


    private void createHomePage() {
        createComponents();

        addActionToButtons();

        setVisible(true);
    }

    private void createComponents() {
        createLayout();
        createWelcomeLabel();
        createDateLabel();
        createButtons();
        createPanel();
    }

    private void createLayout() {
        setTitle("Booking Flight Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("Welcome to Booking Flight System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }

    private void createDateLabel() {
        JLabel dateLabel = new JLabel(currentDateString());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        add(dateLabel, BorderLayout.PAGE_START);
    }

    private void createButtons() {
        bookingFlightButton = uiComponents.button("Booking Flight");
        bookingListButton = uiComponents.button("Search/Cancel Flight");
    }

    private void createPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(bookingFlightButton);
        buttonPanel.add(bookingListButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String currentDateString() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        return "Today's date: " + currentDate.format(formatter);
    }

    private void addActionToButtons() {
        goToFlightBookingPage();
        goToBookingListPage();
    }

    private void goToBookingListPage() {
        bookingListButton.addActionListener(e -> {
            new ReservationSearchPage();
            dispose();
        });
    }

    private void goToFlightBookingPage() {
        bookingFlightButton.addActionListener(e -> {
            new BookingFlightPage();
            dispose();
        });
    }
}
