package net.scrumtools.service;

import net.scrumtools.entity.Room;
import net.scrumtools.entity.User;
import net.scrumtools.entity.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private RoomManager roomManager;
    private UserNameValidator userNameValidator;

    public RoomService(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public Room getRoomById(String roomId) {
        Room room = roomManager.getRoomById(roomId);
        return room;
    }

    public Room createRoom(String sessionId, String userName, long timerLimit, boolean randomOrder) {
        //userNameValidator.validate(userName);
        Room room = roomManager.createRoom();
        room.setRandomOrder(randomOrder);
        User user = new User(sessionId, UserId.createRandom(), userName, timerLimit);
        room.addUser(user);
        return room;
    }

    public Room joinToRoomById(String sessionId, String userName, String roomId) {
        userNameValidator.validate(userName);
        Room room = roomManager.getRoomById(roomId);
        User user = new User(sessionId, UserId.createRandom(), userName, room.getAllUsers().get(0).getUserTime().getTimerLimit());
        room.addUser(user);
        return room;
    }

    public Room leaveRoomById(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.removeUserBySessionId(sessionId);
        return room;
    }

    public Room startUserTimer(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.startTimerBySessionId(sessionId);
        return room;
    }

    public Room stopUserTimer(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.stopTimerBySessionId(sessionId);
        return room;
    }

    public void userSessionExpired(String sessionId) {
        List<Room> rooms = roomManager.findRoomsBySessionId(sessionId);
        for (Room room : rooms) {
            room.removeUserBySessionId(sessionId);
            if (room.isEmpty()) roomManager.removeRoom(room);
        }
    }
}
