package travelAgency.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomePage extends JFrame {

    private JLabel welcomeLabel, dateLabel, iconLabel;
    private JButton bookingFlightButton, bookingListButton;

    public HomePage() {
        setTitle("Booking Flight Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create welcome label
        welcomeLabel = new JLabel("Welcome to Booking Flight System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        // Create date label
        dateLabel = new JLabel(getCurrentDate());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setHorizontalAlignment(JLabel.LEFT);
        add(dateLabel, BorderLayout.NORTH);

        // Create icon label
       /* ImageIcon icon = new ImageIcon("C:\\Users\\amirrahmani\\Desktop\\3125713.png");
        iconLabel = new JLabel(icon);
        iconLabel.setSize(15,15);
        add(iconLabel, BorderLayout.WEST);*/

        // Create buttons
        bookingFlightButton = new JButton("Booking Flight");
        bookingListButton = new JButton("Booking List");

        // Create panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(bookingFlightButton);
        buttonPanel.add(bookingListButton);
        add(buttonPanel, BorderLayout.SOUTH);

        bookingFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FlightBookingPage();
                dispose();
            }
        });

        bookingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingListPage().createBookingListPage();
                dispose();
            }
        });

        setVisible(true);
    }

    // Helper method to get current date
    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        return "Today's date: " + currentDate.format(formatter);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
