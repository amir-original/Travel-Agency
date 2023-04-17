package travelAgency.ui;

import travelAgency.domain.booking.Reservation;
import travelAgency.domain.flight.Flight;
import travelAgency.services.flights.FlightService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlightSearchResult extends JPanel {

    public void showFlightsInfo(List<Flight> flights, List<Reservation> reservations) {
        setLayout(new GridLayout(flights.size(), 6));

       flights.forEach(flight -> {
           final String flightInfo = flight.getFlightInfo(reservations);
           add(new JLabel(flightInfo));
       });
    }
}

