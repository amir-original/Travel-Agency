package travelAgency.fakeData;

import travelAgency.domain.Passenger;
import travelAgency.domain.country.Iran;

import static java.time.LocalDate.of;

public class FakePassengerData {

    public Passenger getPassenger() {
        return new Passenger("fly78478", "Sara", "Baiati",
                of(1999, 4, 5),
                Iran.TEHRAN,"Iran,TEHRAN",
                "1145789", "989907994339");
    }
}
