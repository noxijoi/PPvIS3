package serverapp.entity;

import java.util.Objects;

public class Name {
    public String firstName;
    public String lastName;
    public String patronymic;

    public Name(String firstName, String secondName, String patronymic) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.patronymic = patronymic;
    }

    public Name(String[] fioStrings) {
        this.firstName = fioStrings[0];
        this.lastName = fioStrings[1];
        this.patronymic = fioStrings[2];
    }

    public Name() {

    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ' ' + patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(lastName, name.lastName) &&
                Objects.equals(patronymic, name.patronymic);
    }


    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic);
    }
}
