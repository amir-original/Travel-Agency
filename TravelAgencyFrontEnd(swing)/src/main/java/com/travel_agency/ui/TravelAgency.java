package com.travel_agency.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class TravelAgency {
    private static JTextField FromField;
    private static JTextField ToField;
    private static JTextField Departure;
    private static JTextField Arrival;
    private static String adadAval;

    public static void main(String[] args) {
        JFrame travelAgency = new JFrame("Travel Agency");

        FromField = new JTextField("From");
        FromField.setBounds(50, 10, 150, 50);
        //FromField.setText("From");

        ToField = new JTextField();
        ToField.setBounds(220, 10, 150, 50);
        ToField.replaceSelection("To");

        Departure = new JTextField();
        Departure.setBounds(390, 10, 200, 50);
        Departure.replaceSelection("Departure");

       /* Arrival = new JTextField();
        Arrival.setBounds(590, 10, 200, 50);
        Arrival.replaceSelection("Arrival");
        // ToField.setText(" To");*/

        /*UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);*/

        final DatePicker datePicker = new DatePicker();
        datePicker.setText("Departure");
        datePicker.setBounds(390,10,200,50);

        final DatePicker Arrival = new DatePicker();
        Arrival.setText("Arrival");
        Arrival.setBounds(610,10,200,50);
        Arrival.setForeground(Color.DARK_GRAY);

        JComboBox currency = currencyConverter();
        JButton searchButton = searchButton();
        JButton bookButton = bookButton();
        travelAgency.add(currency);
        travelAgency.add(bookButton);
        travelAgency.add(searchButton);
        travelAgency.add(FromField);
        travelAgency.add(ToField);
        travelAgency.add(datePicker);
        travelAgency.add(Arrival);

        travelAgency.setSize(800, 600);
        travelAgency.setLayout(null);
        travelAgency.setVisible(true);
    }

    private static JButton bookButton() {
        JButton bookButton = new JButton("✈︎ Book");
        bookButton.setBounds(550, 70, 150, 50);
        bookButton.addActionListener(e -> FromField.setText(""));
        bookButton.setForeground(Color.DARK_GRAY);
        bookButton.setBackground(Color.pink);
        return bookButton;
    }

    private static JButton searchButton() {
        JButton searchButton = new JButton("\uD83D\uDD0D︎Search");
        searchButton.setBounds(300, 70, 150, 50);
        searchButton.addActionListener(e -> FromField.setText(""));
        searchButton.setForeground(Color.DARK_GRAY);
        searchButton.setBackground(Color.pink);
        return searchButton;
    }

    private static JComboBox currencyConverter() {
        String[] petStrings = {"\uD83D\uDCB1 Currency","USD $","IRR ريال"};
        JComboBox currency = new JComboBox(petStrings);
        ;
        currency.setSelectedIndex(0);
        currency.setBounds(60,70,150,50);
        return currency;
    }

    private static JButton getButton(String label, int xPosition, int yPosition) {
        JButton button = new JButton(label);
        button.setBounds(xPosition, yPosition, 50, 50);
        button.addActionListener(event -> FromField.setText(FromField.getText() + button.getText()));
        return button;
    }
}
