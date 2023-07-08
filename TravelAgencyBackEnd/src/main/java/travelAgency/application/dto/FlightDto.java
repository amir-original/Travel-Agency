package travelAgency.application.dto;

public class FlightDto {
    String flightNumber;
    String from;
    final String departureDate;
    final String arrivalDate;
    String to;
    final int totalCapacity;
    final double price;
    String Currency;

    public FlightDto(String flightNumber,
                     String from, String to,
                     String departureDate, String arrivalDate,
                     int totalCapacity, double price, String currency) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.totalCapacity = totalCapacity;
        this.price = price;
        Currency = currency;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

}
