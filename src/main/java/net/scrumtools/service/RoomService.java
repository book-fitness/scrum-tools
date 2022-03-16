package net.scrumtools.service;

import net.scrumtools.entity.Room;
import net.scrumtools.entity.User;

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

    public Room createRoom(String sessionId, String userName) {
        userNameValidator.validate(userName);
        Room room = roomManager.createRoom();
        User user = new User(sessionId, userName);
        room.addUser(user);
        return room;
    }

    public Room joinToRoomById(String sessionId, String userName, String roomId) {
        userNameValidator.validate(userName);
        Room room = roomManager.getRoomById(roomId);
        User user = new User(sessionId, userName);
        room.addUser(user);
        return room;
    }

    public Room leaveRoomById(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.removeUserById(sessionId);
        return room;
    }

    public Room startUserTimer(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.startTimerOfUser(sessionId);
        return room;
    }

    public Room stopUserTimer(String roomId, String sessionId) {
        Room room = roomManager.getRoomById(roomId);
        room.stopTimerOfUser(sessionId);
        return room;
    }
}
