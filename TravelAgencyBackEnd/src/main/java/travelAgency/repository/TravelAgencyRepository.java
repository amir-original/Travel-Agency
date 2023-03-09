package travelAgency.repository;

import travelAgency.domain.BookingInformation;

public interface TravelAgencyRepository {

    void book(BookingInformation bookingInformation);
}
