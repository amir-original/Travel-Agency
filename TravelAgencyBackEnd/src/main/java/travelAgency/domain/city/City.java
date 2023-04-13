package travelAgency.domain.city;

public enum City{
    LOS_ANGELES("Los_Angeles"),NEW_YORK_CITY("New_York_City"),PARIS("Paris"),
    COLOGNE("Cologne"),FRANKFURT("Frankfurt"),HAMBURG("Hamburg"),MUNICH("Munich"),
    AHMEDABAD("Ahmedabad"),DELHI("Delhi"),MUMBAI("Mumbai"),LONDON("London"),
    MANCHESTER("Manchester"),
    TEHRAN("Tehran"), AHVAZ("Ahvaz") ,AZERBAIJAN("Azerbaijan") ,ARDEBIL("Ardebil"),
    RASHT("Rasht"), ESFAHAN("Esfehan"), HAMADAN("Hamadan"), KASHAN("Kashan"),
    KERMAN("Kerman"), KISH("Kish"),
    MASHHAD("Mashhad"),SHIRAZ("Shiraz"),TABRIZ("Tabriz"),YAZD("Yazd"), MILAN("Milan"),
    BAGHDAD("Baghdad"),NAJAF("Najaf"),FRANCE("France") ;
    private final String name;

    City(String name) {
        this.name = name;
    }

}
