package travelAgency.ui.pages;

import org.jetbrains.annotations.NotNull;
import travelAgency.dao.api.ExchangeRateDAO;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.currency.Money;
import travelAgency.services.currency_exchange.CurrencyConverter;
import travelAgency.services.currency_exchange.currency_api.ExchangeRateService;
import travelAgency.services.currency_exchange.currency_api.IRRToUSDConverter;
import travelAgency.services.currency_exchange.currency_api.USDToIRRConverter;
import travelAgency.ui.component.UiComponents;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static travelAgency.helper.PriceFormatter.formatPriceWithSymbol;


public class FlightSearchResultPanel extends JPanel {

    private final ExchangeRateDAO exchangeRateDAO;
    private DefaultTableModel tableModel;
    private String selectFlightNumber;
    private final UiComponents ui = new UiComponents();

    public FlightSearchResultPanel(ExchangeRateDAO exchangeRateDAO) {
        this.exchangeRateDAO = exchangeRateDAO;
    }

    public void showFlightsInfo(List<Flight> searchFlights, Object exchangeRate) {

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

    private void addFlightsToTable(List<Flight> searchFlights, Object exchangeRate) {
        for (Flight flight : searchFlights)
            addFlightToRow(exchangeRate, flight);
    }

    private void addFlightToRow(Object exchangeRate, Flight flight) {
        Object[] row = new Object[5];
        row[0] = flight.from();
        row[1] = flight.to();
        row[2] = flight.flightNumber();
        row[3] = flight.totalCapacity();
        initPrice(exchangeRate, flight, row);
        tableModel.addRow(row);
        tableModel.fireTableDataChanged();
    }

    private void initPrice(Object exchangeRate, Flight flight, Object[] row) {
        if (exchangeRate.equals("IRR")) {
            row[4] = getFormatPriceWithSymbol(flight, new USDToIRRConverter(exchangeRateDAO));
        } else if (exchangeRate.equals("USD")) {
            row[4] = getFormatPriceWithSymbol(flight, new IRRToUSDConverter(exchangeRateDAO));
        }
    }

    private String getFormatPriceWithSymbol(Flight flight, ExchangeRateService exchangeRateService) {
        return formatPriceWithSymbol(getMoney(flight, currencyConverter(exchangeRateService)));
    }


    private Money getMoney(Flight flight, CurrencyConverter currencyConverterService) {
        return currencyConverterService.convert(flight.price());
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
        table.setPreferredSize(tableSize);
    }

    private void setColumnWidths(JTable table) {
        for (int i = 0; i < 5; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(150);
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

    @NotNull
    private CurrencyConverter currencyConverter(ExchangeRateService exchangeRateService) {
        return new CurrencyConverter(exchangeRateService);
    }

    public String getSelectedFlight() {
        return selectFlightNumber;
    }
}

