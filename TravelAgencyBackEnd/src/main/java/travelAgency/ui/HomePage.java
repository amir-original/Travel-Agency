package travelAgency.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage extends JFrame {

    private JButton bookingFlightButton, bookingListButton;

    private final UiComponents uiComponents = new UiComponents();

    public static void main(String[] args) {
        final HomePage homePage = new HomePage();
    }


    public HomePage() {
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
        bookingListButton = uiComponents.button("Booking List");
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
            new BookingListPage();
            dispose();
        });
    }

    private void goToFlightBookingPage() {
        bookingFlightButton.addActionListener(e -> {
            new FlightBookingPage();
            dispose();
        });
    }
}
