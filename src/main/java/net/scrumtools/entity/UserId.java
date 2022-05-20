package net.scrumtools.entity;


import net.scrumtools.service.IdGenerator;

import java.util.Objects;

public class UserId {
    private String value;

    public UserId(String value) {
        this.value = value;
    }

    public static UserId createRandom() {
        IdGenerator idGenerator = new IdGenerator();
        return new UserId(idGenerator.generateRoomNumber());
    }

    public static UserId fromStr(String str) {
        return new UserId(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
