package travelAgency.ui;

import org.jetbrains.annotations.NotNull;
import travelAgency.dao.api.ExchangeRateDAOImpl;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.Money;
import travelAgency.services.price_converter.CurrencyConverterService;
import travelAgency.services.price_converter.CurrencyConverterServiceImpl;
import travelAgency.services.price_converter.currencyApi.ExchangeRateService;
import travelAgency.services.price_converter.currencyApi.IRRToUSDConverter;
import travelAgency.services.price_converter.currencyApi.USDToIRRConverter;
import travelAgency.services.reservation.ReservationListService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static travelAgency.helper.PriceFormat.formatPriceWithSymbol;

public class FlightSearchResult extends JPanel {

    private final ReservationListService reservationListService;
    private DefaultTableModel tableModel;
    private String selectFlightNumber;

    public FlightSearchResult(ReservationListService reservationService) {

        this.reservationListService = reservationService;
    }

    public void showFlightsInfo(List<Flight> searchFlights,Object exchangeRate) {

        setLayout(new BorderLayout());
        // create table and set model
        JTable table = createTable();
        table.removeAll();

        setupTableHeader();
        table.setModel(tableModel);

        removeAll();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // add flight data to table model
        initTableRows(searchFlights, exchangeRate);

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

        repaint();
        revalidate();
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

    private void initTableRows(List<Flight> searchFlights, Object exchangeRate) {
        for (Flight flight : searchFlights) {
            initTableRow(exchangeRate, flight);
        }
    }

    private void initTableRow(Object exchangeRate, Flight flight) {
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
        final ExchangeRateDAOImpl exchangeRateDAO = new ExchangeRateDAOImpl();
        if (exchangeRate.equals("IRR")){
            final USDToIRRConverter exchangeRateService = new USDToIRRConverter(exchangeRateDAO);
            row[4] = formatPriceWithSymbol(getMoney(flight, currencyConverter(exchangeRateService)));
        } else if (exchangeRate.equals("USD")) {
            final IRRToUSDConverter exchangeRateService = new IRRToUSDConverter(exchangeRateDAO);
            row[4] = formatPriceWithSymbol(getMoney(flight, currencyConverter(exchangeRateService)));
        }
    }

    private Money getMoney(Flight flight, CurrencyConverterService currencyConverterService) {
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

    @NotNull
    private CurrencyConverterServiceImpl currencyConverter(ExchangeRateService exchangeRateService) {
        return new CurrencyConverterServiceImpl(exchangeRateService);
    }

    // get selected flights from table
    public String getSelectedFlight() {
        return selectFlightNumber;
    }
}

