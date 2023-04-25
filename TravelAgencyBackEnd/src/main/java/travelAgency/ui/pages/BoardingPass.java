package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.FlightBuilder;
import travelAgency.domain.flight.currency.Money;
import travelAgency.domain.passenger.PassengerBuilder;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.*;

public class BoardingPass extends JDialog {

    private final Reservation reservation;

    private final UiComponents ui = new UiComponents();

    public void printTicket() {
        setupDialog();

        JPanel panel = createPanel();

        JLabel header =  ui.h1Label("Boarding Pass", SwingConstants.CENTER);

        JTextArea ticketInformation = ui.disableTextArea(reservation.buildTicket());

        JButton closeButton = createCloseButton();

        addComponentsToPanel(panel, header, ticketInformation, closeButton);

        addPanelToDialog(panel);
    }

    private void addPanelToDialog(JPanel panel) {
        add(panel);
        setVisible(true);
    }

    @NotNull
    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private void addComponentsToPanel(JPanel panel, JLabel header, JTextArea ticketInformation, JButton closeButton) {
        JPanel labelsPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        labelsPanel.add(header);
        labelsPanel.add(ticketInformation);
        panel.add(labelsPanel, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);
    }

    @NotNull
    private JButton createCloseButton() {
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }

    private void setupDialog() {
        setTitle("Boarding Pass");
        setSize(800, 250);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public BoardingPass(Reservation reservation) {
        this.reservation = reservation;
    }

    public static void main(String[] args) {
        Reservation bookingInformation = new Reservation("A87-745-854",
                new ReservationInformation(flight().build(),passenger().build(),5));
        final BoardingPass dialog = new BoardingPass(bookingInformation);
        dialog.printTicket();
        dialog.setVisible(true);
    }

    public static FlightBuilder flight() {
        return FlightBuilder.flight()
                .withFlightNumber("0321")
                .withTotalCapacity(40)
                .withPrice(new Money(145, Currency.USD))
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
