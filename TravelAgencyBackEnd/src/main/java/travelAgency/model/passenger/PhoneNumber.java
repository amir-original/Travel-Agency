package travelAgency.model.passenger;

import travelAgency.exceptions.InvalidPhoneNumberException;

import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public final class PhoneNumber {

    private final String number;

    private PhoneNumber(String initPhoneNumber) {
        if (isNull(initPhoneNumber) || initPhoneNumber.isBlank())
            throw new IllegalArgumentException("the phoneNumber must not be null or empty!");

        if (isNotValidPhoneNumberFormat(initPhoneNumber)) {
            throw new InvalidPhoneNumberException();
        }
        this.number = initPhoneNumber;
    }

    private boolean isNotValidPhoneNumberFormat(String phoneNumber) {
        return !Pattern.matches("^((\\+98)|(0)|(98))9\\d{9}$", phoneNumber);
    }

    public static PhoneNumber of(String plainTextPhoneNumber) {
        return new PhoneNumber(plainTextPhoneNumber);
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber phoneNumber = (PhoneNumber) o;
        return Objects.equals(number, phoneNumber.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
