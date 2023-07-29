package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.application.dto.ReservationResponse;
import travelAgency.model.reservation.Reservation;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;
import static java.time.LocalDate.of;

public class BoardingPass extends JDialog {

    private final UiComponents ui = new UiComponents();

    public void printTicket(ReservationResponse reservation) {
        setupDialog();

        final String ticket = buildTicket(reservation);
        JPanel panel = createPanel();
        JButton closeButton = createCloseButton();
        JLabel header = createHeaderLabel("Boarding Pass");
        JTextArea ticketTextArea = ui.disableTextArea(ticket);

        addComponentsToPanel(panel, header, ticketTextArea, closeButton);
        addPanelToDialog(panel);
    }

    public String buildTicket(ReservationResponse reservationResponse) {
        return format(""" 
                        Passenger Name: %s               Flight Number: %s           Ticket Number: %s  
                                From : %s  📍 ---------------------------------------------- ✈  To: %s
                        Departure: %s                    Arrival: %s                 Price: %s     
                        """,
                reservationResponse.passengerFullName(),
                reservationResponse.flightNumber(),
                reservationResponse.flightNumber(),
                reservationResponse.from(),
                reservationResponse.to(),
                reservationResponse.departureDate(),
                reservationResponse.arrivalDate(),
                reservationResponse.price());
    }

    private JLabel createHeaderLabel(String txt) {
        return ui.h1Label(txt, SwingConstants.CENTER);
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
}
