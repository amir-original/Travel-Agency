package travelAgency.domain.city;

public enum City{
    TEHRAN("Tehran"),
    AHVAZ("Ahvaz"),
    AZERBAIJAN("Azerbaijan"),
    ARDEBIL("Ardebil"),
    RASHT("Rasht"),
    ESFAHAN("Esfehan"),
    HAMADAN("Hamadan"),
    KASHAN("Kashan"),
    KERMAN("Kerman"),
    KISH("Kish"),
    MASHHAD("Mashhad"),
    SHIRAZ("Shiraz"),
    TABRIZ("Tabriz"),
    YAZD("Yazd"),
    LOS_ANGELES("Los Angeles"),
    NEW_YORK("New York"),
    PARIS("Paris"),
    FRANKFURT("Frankfurt"),
    HAMBURG("Hamburg"),
    MUNICH("Munich"),
    LONDON("London"),
    BAGHDAD("Baghdad"),
    NAJAF("Najaf");

    private final String name;

    City(String name) {
        this.name = name;
    }

}
