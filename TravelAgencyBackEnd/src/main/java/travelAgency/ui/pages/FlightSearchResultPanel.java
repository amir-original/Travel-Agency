package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.controller.ExchangeRateConverterController;
import travelAgency.controller.FlightController;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class FlightSearchResultPanel extends JPanel {

    private final ExchangeRateConverterController rateConverterController;
    private final FlightController flightController;
    private DefaultTableModel tableModel;
    private String selectedFlightNumber;
    private final UiComponents ui = new UiComponents();

    public FlightSearchResultPanel(ExchangeRateConverterController rateConverterController,
                                   FlightController flightController) {
        this.rateConverterController = rateConverterController;
        this.flightController = flightController;
    }

    public void showFlightsInfo(FlightPlan searchedFlightPlan, Currency exchangeRate) {

        setLayout(new BorderLayout());

        JTable table = createTableAndSetModel();

        removeAll();
        add(new JScrollPane(table), BorderLayout.CENTER);

        populateTableWithFlightInfo(searchedFlightPlan, exchangeRate);

        configureTable(table);

        setup(table);

        updateUi();
    }

    @NotNull
    private JTable createTableAndSetModel() {
        JTable table = new JTable();
        tableModel = ui.createReadOnlyTableModel();
        setupTableHeader();
        table.setModel(tableModel);
        disableCellEditing(table);
        setCellAlignment(table);
        return table;
    }

    private void disableCellEditing(JTable table) {
        table.setDefaultEditor(Object.class, null);
    }

    private void setCellAlignment(JTable table) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
    }

    private void populateTableWithFlightInfo(FlightPlan searchedFlightPlan, Currency exchangeRate) {
        final List<Flight> flights = flightController.searchFlights(searchedFlightPlan);
        addFlightsToTable(flights, exchangeRate);
        tableModel.fireTableDataChanged();
    }

    private void configureTable(JTable table) {
        configureTableInteraction(table);
        setColumnWidths(table);
    }

    private void updateUi() {
        repaint();
        revalidate();
    }

    private void addFlightsToTable(List<Flight> searchFlights, Currency exchangeRate) {
        for (Flight flight : searchFlights)
            addFlightToRow(exchangeRate, flight);
    }

    private void addFlightToRow(Currency exchangeRate, Flight flight) {
        Object[] row = new Object[6];
        row[0] = flight.from();
        row[1] = flight.to();
        row[2] = flight.flightNumber();
        row[3] = flightController.getAvailableSeats(flight.flightNumber());
        row[4] = flight.departure().toString();
        row[5] = formatPriceWithSymbol(flight, exchangeRate);
        tableModel.addRow(row);
    }

    private String formatPriceWithSymbol(Flight flight, Currency target) {
        final double amount = flight.price().amount();
        final Currency from = flight.price().currency();
        final Money money = rateConverterController.convert(amount, from, target);
        return money.formatMoneyWithSymbol();
    }

    private void setupTableHeader() {
        tableModel.addColumn("Origin");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Available Seats");
        tableModel.addColumn("Date");
        tableModel.addColumn("Price");
    }

    private void setup(JTable table) {
        Dimension tableSize = table.getPreferredSize();
        tableSize.height = table.getRowHeight() * tableModel.getRowCount();
        tableSize.width = table.getColumnModel().getTotalColumnWidth();
        table.setPreferredSize(tableSize);
    }

    private void setColumnWidths(JTable table) {
        for (int i = 0; i < 6; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(180);
    }

    private void configureTableInteraction(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            selectFlightNumberIfRowSelected(event, table.getSelectedRow());
        });
    }

    private void selectFlightNumberIfRowSelected(ListSelectionEvent event, int selectedRow) {
        if (!event.getValueIsAdjusting() && !isRowSelected(selectedRow))
            selectedFlightNumber = getSelectFlightNumberValue(selectedRow);
    }

    private boolean isRowSelected(int selectedRow) {
        return selectedRow == -1;
    }

    private String getSelectFlightNumberValue(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 2);
    }

    public String getSelectedFlight() {
        return selectedFlightNumber;
    }
}

