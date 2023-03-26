package travelAgency.ui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingListPage extends JFrame {

    public void createBookingListPage() {
        setTitle("Booking List");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        JLabel flightNumberLabel = new JLabel("Flight Number:");
        JTextField flightNumberField = new JTextField(20);
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);
        JLabel birthdayLabel = new JLabel("Date of Birth:");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        inputPanel.add(flightNumberLabel);
        inputPanel.add(flightNumberField);
        inputPanel.add(new JLabel());
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel());
        inputPanel.add(birthdayLabel);
        inputPanel.add(datePicker);
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel("Search Results"));

        JPanel resultPanel = new JPanel();
        resultPanel.setPreferredSize(new Dimension(800, 400));
        JScrollPane scrollPane = new JScrollPane(resultPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");
        JButton backButton = new JButton("Back to Home");

        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // TODO: add search logic here

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHomePage();
                dispose(); // close current frame
            }
        });
    }

    private static void createHomePage() {
        new HomePage();
    }

    public static void main(String[] args) {
        new BookingListPage().createBookingListPage();
    }

}
