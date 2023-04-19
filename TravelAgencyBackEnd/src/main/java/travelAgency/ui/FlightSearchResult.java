package travelAgency.ui;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.flight.Flight;
import travelAgency.services.priceConverter.CurrencyConverterServiceImpl;
import travelAgency.services.priceConverter.currencyApi.USDToIRRConverter;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FlightSearchResult extends JPanel {

    private DefaultTableModel tableModel;
    private String selectFlightNumber;

    public void showFlightsInfo(List<Flight> searchFlights,
                                List<Reservation> allReservations,
                                Object exchangeRate) {
        setLayout(new BorderLayout());

        // create table and set model
        JTable table = createTable();

        setupTableHeader();
        table.setModel(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // add flight data to table model
        initTableRows(searchFlights, allReservations, exchangeRate);

        // add click listener to table rows
        getSelectFlight(table);

        // disable cell editing
        table.setDefaultEditor(Object.class, null);

        // set cell alignment
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        // set column widths
        setColumns(table);

        // set table dimensions to fit panel
        setup(table);
    }

    @NotNull
    private JTable createTable() {
        JTable table = new JTable();
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return table;
    }

    private void initTableRows(List<Flight> searchFlights, List<Reservation> allReservations, Object exchangeRate) {
        for (Flight flight : searchFlights) {
            initTableRow(allReservations, exchangeRate, flight);
        }
    }

    private void initTableRow(List<Reservation> allReservations, Object exchangeRate, Flight flight) {
        Object[] row = new Object[5];
        row[0] = flight.from();
        row[1] = flight.to();
        row[2] = flight.flightNumber();
        row[3] = 2; // TODO flight.getAvailableSeats(allReservations);
        initPrice(exchangeRate, row, flight);
        tableModel.addRow(row);
    }

    private void setupTableHeader() {
        tableModel.addColumn("Origin");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Available Seats");
        tableModel.addColumn("Price");
    }

    private void setup(JTable table) {
        Dimension tableSize = table.getPreferredSize();
        tableSize.height = table.getRowHeight() * tableModel.getRowCount();
        tableSize.width = table.getColumnModel().getTotalColumnWidth();
        table.setPreferredSize(tableSize);
    }

    private void setColumns(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
    }

    private void getSelectFlight(JTable table) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // get the flight number from the selected row and store it in the field
                    selectFlightNumber = (String) tableModel.getValueAt(selectedRow, 2);
                }
            }
        });
    }

    private void initPrice(Object exchangeRate, Object[] row, Flight flight) {
        if (exchangeRate.equals("USD")) {
            row[4] = flight.price() + " $ ";
        } else if (exchangeRate.equals("IRR")) {
            row[4] = " ريال " +
                    flight.price(new CurrencyConverterServiceImpl(new USDToIRRConverter()));
        }


    }

    // get selected flights from table
    public String getSelectedFlight() {
        return selectFlightNumber;
    }
}

/* for (int i = 0; i < origins.length; i++) {
            Object[] row = new Object[5];
            row[0] = origins[i];
            row[1] = destinations[i];
            row[2] = flightNumbers[i];
            row[3] = availableSeats[i];
            row[4] = prices[i] + " ريال ";
            tableModel.addRow(row);
        }*/
