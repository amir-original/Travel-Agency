package travelAgency.model.passenger;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ResidentialAddress {
    private final String city;
    private final String address;
    private final String zipcode;

    private ResidentialAddress(@NotNull String city,
                               @NotNull String address,
                               @NotNull String zipcode) {
        if (city.isBlank() || address.isBlank() || zipcode.isBlank())
            throw new IllegalArgumentException("place withNationalCode resident should not be empty!");
        if (isAddressLengthGreaterThanTen(address))
            throw new IllegalArgumentException("address should be greater than 10 length!");
        if (isNotValidZipcodeFormat(zipcode))
            throw new IllegalArgumentException("zipcode length should be have equal 10");

        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
    }

    private boolean isAddressLengthGreaterThanTen(String address) {
        return address.length() <= 10;
    }

    private boolean isNotValidZipcodeFormat(String zipcode) {
        return !zipcode.matches("(\\d){10}");
    }

    public static ResidentialAddress of(String city, String address, String zipcode) {
        return new ResidentialAddress(city, address, zipcode);
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidentialAddress that = (ResidentialAddress) o;
        return Objects.equals(city, that.city) && Objects.equals(address, that.address) && Objects.equals(zipcode, that.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, address, zipcode);
    }

    @Override
    public String toString() {
        return "ResidentialAddress{" +
                "city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
