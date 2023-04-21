package travelAgency.ui;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.reservation.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReservationSearchResult extends JPanel {

    private DefaultTableModel tableModel;
    private String selectFlightNumber;

    public void showReservationInfo(Reservation reservation) {
        setLayout(new BorderLayout());

        // create table and set model
        JTable table = createTable();

        setupTableHeader();
        table.setModel(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // add flight data to table model
        initTableRows(reservation);

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

    private void initTableRows(Reservation reservation) {
            initTableRow(reservation);

    }

    private void initTableRow(Reservation reservation) {
        Object[] row = new Object[9];
        row[0] = reservation.ticketNumber();
        row[1] = reservation.flightNumber();
        row[2] = reservation.passenger().fullName();
        row[3] = reservation.passengerId();
        row[4] = reservation.flight().from();
        row[5] = reservation.flight().to();
        row[6] = reservation.flight().departure();
        row[7] = reservation.flight().arrival();
        row[8] = reservation.flight().price();

        tableModel.addRow(row);
    }

    private void setupTableHeader() {
        tableModel.addColumn("Ticket Number");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Passenger Name");
        tableModel.addColumn("Passport Number");
        tableModel.addColumn("Origin");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Departure");
        tableModel.addColumn("Arrival");
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
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.getColumnModel().getColumn(8).setPreferredWidth(150);
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

    // get selected flights from table
    public String getSelectedFlight() {
        return selectFlightNumber;
    }
}
