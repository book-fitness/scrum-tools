package net.scrumtools.entity;

import net.scrumtools.service.IdGenerator;

import java.util.Objects;

public class RoomId {
    private String value;

    public RoomId(String value) {
        this.value = value;
    }

    public static RoomId createRandom() {
        IdGenerator idGenerator = new IdGenerator();
        return new RoomId(idGenerator.generateId());
    }

    public static RoomId fromStr(String str) {
        return new RoomId(str);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId roomId = (RoomId) o;
        return Objects.equals(value, roomId.value);
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
