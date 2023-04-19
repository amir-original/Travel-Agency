package travelAgency.ui;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.domain.reservation.BookingInformation;
import travelAgency.domain.reservation.Reservation;

import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;

public class ReservationResultDialog extends JDialog {

    private final Reservation reservation;

    public void printTicket() {
        // set dialog properties
        setTitle("Reservation Result");
        setSize(700, 250);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // create a panel to hold the components
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create labels to display the flight information
        String ticketInfo = reservation.getTicketInfo();

        JLabel header = new JLabel("Boarding Pass ", SwingConstants.CENTER);
        JTextArea confirmationTextArea = new JTextArea();
        confirmationTextArea.setEditable(false);
        confirmationTextArea.setOpaque(false);
        confirmationTextArea.setFocusable(false);
        confirmationTextArea.setFont(new Font("Arial Unicode MS", Font.PLAIN, 17));
        confirmationTextArea.append(ticketInfo);
        // create a button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        // add the components to the panel
        JPanel labelsPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        labelsPanel.add(header);
        labelsPanel.add(confirmationTextArea);
        panel.add(labelsPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        // add the panel to the dialog
        add(panel);
    }

    public ReservationResultDialog(Reservation reservation) {
        this.reservation = reservation;
    }

    public static void main(String[] args) {
        Reservation bookingInformation = new Reservation("A787",
                new BookingInformation(flight().build(),passenger().build(),5));
        final ReservationResultDialog dialog = new ReservationResultDialog(bookingInformation);
        dialog.printTicket();
        dialog.setVisible(true);
    }

    public static FlightBuilder flight() {
        return FlightBuilder.flight()
                .withFlightNumber("0321")
                .withTotalCapacity(40)
                .withPrice(145)
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(LocalDate.now())
                .arrivalAt(LocalDate.now().plusDays(1));
    }

    public static PassengerBuilder passenger() {
        return PassengerBuilder.passenger()
                .withId("55")
                .firstName("Sara")
                .lastName("Baiati")
                .withBirthday(of(1999, 4, 5))
                .ofCity(TEHRAN.name())
                .withAddress("Iran,TEHRAN")
                .withZipcode("1145789")
                .withPhoneNumber("989907994339");
    }
}
