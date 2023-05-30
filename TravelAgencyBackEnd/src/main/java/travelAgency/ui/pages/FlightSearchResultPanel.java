package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.controller.ExchangeRateConverterController;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.currency.Currency;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateConverter;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateServiceImpl;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class FlightSearchResultPanel extends JPanel {

    private final ExchangeRateConverterController rateConverterController;
    private DefaultTableModel tableModel;
    private String selectFlightNumber;
    private final UiComponents ui = new UiComponents();

    public FlightSearchResultPanel(ExchangeRateConverterController rateConverterController) {
        this.rateConverterController = rateConverterController;
    }

    public void showFlightsInfo(List<Flight> searchFlights, Currency exchangeRate) {

        setLayout(new BorderLayout());

        JTable table = createTableAndSetModel();

        removeAll();
        add(new JScrollPane(table), BorderLayout.CENTER);

        addFlightsToTable(searchFlights, exchangeRate);

        addClickListenerToTableRows(table);

        disableCellEditing(table);

        setCellAlignment(table);

        setColumnWidths(table);

        setup(table);

        repaint();
        revalidate();
    }

    private void disableCellEditing(JTable table) {
        table.setDefaultEditor(Object.class, null);
    }

    private void setCellAlignment(JTable table) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
    }

    @NotNull
    private JTable createTableAndSetModel() {
        JTable table = new JTable();
        tableModel = ui.createReadOnlyTableModel();
        setupTableHeader();
        table.removeAll();
        table.setModel(tableModel);
        return table;
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
        row[3] = flight.totalCapacity();
        row[4] = flight.departure().toString();
        row[5] = getFormatPriceWithSymbol(flight, exchangeRate);
        tableModel.addRow(row);
        tableModel.fireTableDataChanged();
    }

    private String getFormatPriceWithSymbol(Flight flight, Currency target) {
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
        table.setPreferredSize(tableSize);
    }

    private void setColumnWidths(JTable table) {
        for (int i = 0; i < 6; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(180);
    }

    private void addClickListenerToTableRows(JTable table) {
        table.getSelectionModel().addListSelectionListener(event -> {
            selectFlightNumberIfRowSelected(event, table.getSelectedRow());
        });
    }

    private void selectFlightNumberIfRowSelected(ListSelectionEvent event, int selectedRow) {
        if (!event.getValueIsAdjusting() && !isRowSelected(selectedRow))
            selectFlightNumber = getSelectFlightNumberValue(selectedRow);
    }

    private boolean isRowSelected(int selectedRow) {
        return selectedRow == -1;
    }

    private String getSelectFlightNumberValue(int selectedRow) {
        return (String) tableModel.getValueAt(selectedRow, 2);
    }

    public String getSelectedFlight() {
        return selectFlightNumber;
    }
}

