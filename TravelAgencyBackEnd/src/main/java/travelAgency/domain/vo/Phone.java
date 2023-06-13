package travelAgency.domain.vo;

import travelAgency.exceptions.InvalidPhoneNumberException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Phone {
    private final String number;

    private Phone(String phoneNumber) {
        if (phoneNumber==null || phoneNumber.isBlank())
            throw new IllegalArgumentException("the phoneNumber must not be null or empty!");

        if (isNotValidPhoneNumberFormat(phoneNumber)) {
            throw new InvalidPhoneNumberException();
        }
        this.number = phoneNumber;
    }

    public static Phone of(String phoneNumber) {
        return new Phone(phoneNumber);
    }

    public String getNumber() {
        return number;
    }

    private boolean isNotValidPhoneNumberFormat(String phoneNumber) {
        return !Pattern.matches("^((\\+98)|(0)|(98))9\\d{9}$", phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                '}';
    }
}
