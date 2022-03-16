package net.scrumtools.service;

import net.scrumtools.entity.Room;
import net.scrumtools.entity.RoomId;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private Map<RoomId, Room> rooms = new HashMap<>();

    public Room createRoom() {
        RoomId roomId = RoomId.createRandom();
        Room room = new Room(roomId);
        rooms.put(roomId, room);
        return room;
    }

    public Room getRoomById(RoomId roomId) {
        return rooms.get(roomId);
    }

    public Room getRoomById(String roomIdStr) {
        return getRoomById(RoomId.fromStr(roomIdStr));
    }
}
