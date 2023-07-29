package travelAgency.application.dto;

public record ReservationResponse(String reservationNumber,
                                  String passengerId,
                                  String passengerFullName,
                                  String flightNumber,
                                  String from,
                                  String to,
                                  String departureDate,
                                  String arrivalDate,
                                  String price,
                                  int travelers) {
}
