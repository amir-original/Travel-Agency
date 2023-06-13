package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.Reservation;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import java.awt.*;

import static java.time.LocalDate.of;

public class BoardingPass extends JDialog {

    private final Reservation reservation;

    private final UiComponents ui = new UiComponents();

    public BoardingPass(Reservation reservation) {
        this.reservation = reservation;
    }

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
}
