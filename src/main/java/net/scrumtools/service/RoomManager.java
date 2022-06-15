package net.scrumtools.service;

import net.scrumtools.entity.Room;
import net.scrumtools.entity.RoomId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    public List<Room> findRoomsBySessionId(String sessionId) {
        return this.rooms.values()
                .stream()
                .filter(r -> r.hasUserWithSessionId(sessionId))
                .collect(Collectors.toList());
    }

    public void removeRoom(Room room) {
        rooms.remove(room.getRoomId());
    }
}
