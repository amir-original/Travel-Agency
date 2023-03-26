package travelAgency.ui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingInfoPage extends JFrame {

    public static void createBookingInfoPage() {
        JFrame frame = new JFrame("Booking Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(20);
        JLabel birthdayLabel = new JLabel("Date of Birth:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField(20);
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField(20);
        JLabel postalCodeLabel = new JLabel("Postal Code:");
        JTextField postalCodeField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);

        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel());
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel());
        inputPanel.add(birthdayLabel);
        inputPanel.add(datePicker);
        inputPanel.add(new JLabel());
        inputPanel.add(cityLabel);
        inputPanel.add(cityField);
        inputPanel.add(new JLabel());
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);
        inputPanel.add(new JLabel());
        inputPanel.add(postalCodeLabel);
        inputPanel.add(postalCodeField);
        inputPanel.add(new JLabel());
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton bookButton = new JButton("Book");
        JButton backButton = new JButton("Back");

        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add booking logic here
                JOptionPane.showMessageDialog(frame, "Booking successful!");
                frame.dispose(); // close frame after successful booking
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // createFlightBookingPage();
                frame.dispose(); // close current frame
            }
        });
    }


    public static void main(String[] args) {
        createBookingInfoPage();
    }
}

