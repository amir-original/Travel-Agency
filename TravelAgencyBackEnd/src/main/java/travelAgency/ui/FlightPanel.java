package travelAgency.ui;

import travelAgency.domain.flight.Flight;
import travelAgency.services.flights.FlightService;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class FlightPanel extends JPanel {

    private String[] origins = {"New York", "London", "Paris", "Tokyo"};
    private String[] destinations = {"Los Angeles", "Sydney", "Rome", "Beijing"};
    private double[] prices = {599.99, 799.99, 899.99, 999.99};
    private String[] flightNumbers = {"AA123", "UA456", "DL789", "BA012"};
    private int[] availableSeats = {50, 30, 20, 10};

    private String getFlightInfo(Flight flight,int availableSeats) {
        return String.format("""
                        Flight:
                           Flight Number: %s
                           From: %s
                           To: %s
                           Price: %s Rial
                           Available Seats: %s
                           select flight
                        """,
                flight.flightNumber(),
                flight.from(),
                flight.to(),
                NumberFormat.getInstance().format(flight.price()),
                availableSeats);
    }

    public void showFlightsInfo(List<Flight> flights, FlightService availableSeats ) {
        setLayout(new GridLayout(flights.size(), 6));

       flights.forEach(flight -> {
           add(new JLabel(getFlightInfo(flight,availableSeats.availableSeats(flight.flightNumber()))));
       });
    }
}

